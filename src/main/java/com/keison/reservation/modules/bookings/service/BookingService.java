package com.keison.reservation.modules.bookings.service;

import com.keison.reservation.common.enums.BookingStatus;
import com.keison.reservation.common.enums.SeatStatus;
import com.keison.reservation.common.exception.BadRequestException;
import com.keison.reservation.modules.bookings.dto.BookingResponse;
import com.keison.reservation.modules.bookings.dto.CreateBookingRequest;
import com.keison.reservation.modules.bookings.entity.Booking;
import com.keison.reservation.modules.bookings.entity.BookingDetails;
import com.keison.reservation.modules.bookings.entity.BookingPassenger;
import com.keison.reservation.modules.bookings.repo.BookingDetailsRepository;
import com.keison.reservation.modules.bookings.repo.BookingPassengerRepository;
import com.keison.reservation.modules.bookings.repo.BookingRepository;
import com.keison.reservation.modules.flights.entity.Flight;
import com.keison.reservation.modules.flights.entity.FlightSeat;
import com.keison.reservation.modules.flights.repo.FlightRepository;
import com.keison.reservation.modules.flights.repo.FlightSeatRepository;
import com.keison.reservation.modules.passengers.entity.Passenger;
import com.keison.reservation.modules.passengers.repo.PassengerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.keison.reservation.security.SecurityUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

import static com.keison.reservation.security.SecurityUtils.currentUser;

@Service
public class BookingService {

    private final BookingRepository bookingRepo;
    private final BookingDetailsRepository detailsRepo;
    private final BookingPassengerRepository bookingPassengerRepo;

    private final PassengerRepository passengerRepo;
    private final FlightRepository flightRepo;
    private final FlightSeatRepository seatRepo;

    public BookingService(
            BookingRepository bookingRepo,
            BookingDetailsRepository detailsRepo,
            BookingPassengerRepository bookingPassengerRepo,
            PassengerRepository passengerRepo,
            FlightRepository flightRepo,
            FlightSeatRepository seatRepo
    ) {
        this.bookingRepo = bookingRepo;
        this.detailsRepo = detailsRepo;
        this.bookingPassengerRepo = bookingPassengerRepo;
        this.passengerRepo = passengerRepo;
        this.flightRepo = flightRepo;
        this.seatRepo = seatRepo;
    }

    @Transactional
    public BookingResponse createBooking(CreateBookingRequest req) {
        var user = currentUser();
        Passenger owner = passengerRepo.findByUserId(user.getId())
                .orElseThrow(() -> new BadRequestException("Passenger profile not found"));

        Flight flight = flightRepo.findById(req.getFlightId())
                .orElseThrow(() -> new BadRequestException("Flight not found"));

        // Validate duplicates seats in request
        Set<UUID> seatIds = new HashSet<>();
        for (CreateBookingRequest.TravelerSeat t : req.getTravelers()) {
            if (!seatIds.add(t.getSeatId())) {
                throw new BadRequestException("Duplicate seat selected in booking request");
            }
        }

        // Fetch all seats at once
        List<FlightSeat> seats = seatRepo.findAllById(seatIds);
        if (seats.size() != seatIds.size()) {
            throw new BadRequestException("One or more seats not found");
        }

        // Validate each traveler passenger exists + seat belongs to flight + seat AVAILABLE
        Map<UUID, Passenger> passengersById = new HashMap<>();
        for (CreateBookingRequest.TravelerSeat t : req.getTravelers()) {
            Passenger p = passengerRepo.findById(t.getPassengerId())
                    .orElseThrow(() -> new BadRequestException("Passenger not found: " + t.getPassengerId()));
            passengersById.put(p.getId(), p);
        }

        BigDecimal total = BigDecimal.ZERO;
        for (FlightSeat s : seats) {
            if (!s.getFlight().getId().equals(flight.getId())) {
                throw new BadRequestException("Seat " + s.getSeatNumber() + " does not belong to this flight");
            }
            if (s.getStatus() != SeatStatus.AVAILABLE) {
                throw new BadRequestException("Seat " + s.getSeatNumber() + " is not available");
            }
            total = total.add(s.getPrice());
        }

        // Reserve seats now
        for (FlightSeat s : seats) {
            s.setStatus(SeatStatus.RESERVED);
        }
        seatRepo.saveAll(seats);

        // Create booking
        Booking booking = new Booking();
        booking.setBookingRef(generateBookingRef());
        booking.setPassenger(owner);
        booking.setNumberOfPassengers(req.getTravelers().size());
        booking.setSeatPrice(total); // kept for compatibility; total seats price
        booking.setTotalAmount(total);
        booking.setStatus(BookingStatus.PENDING);
        booking.setCreatedAt(Instant.now());

        booking = bookingRepo.save(booking);

        // Create booking details rows (one per traveler)
        List<BookingResponse.Item> items = new ArrayList<>();

        for (CreateBookingRequest.TravelerSeat t : req.getTravelers()) {
            Passenger traveler = passengersById.get(t.getPassengerId());
            FlightSeat seat = seats.stream().filter(x -> x.getId().equals(t.getSeatId())).findFirst().orElseThrow();

            BookingDetails details = new BookingDetails();
            details.setBooking(booking);
            details.setFlight(flight);
            details.setPassengerName(traveler.getFullName());
            details.setSeatNumber(seat.getSeatNumber());
            details.setAirline(flight.getAirline());
            details = detailsRepo.save(details);

            BookingPassenger bp = new BookingPassenger();
            bp.setBookingDetail(details);
            bp.setPassenger(traveler);
            bp.setFlight(flight);
            bp.setSeat(seat);
            bp.setSeatNumber(seat.getSeatNumber());
            bp.setNote(t.getNote());
            bookingPassengerRepo.save(bp);

            items.add(new BookingResponse.Item(traveler.getId(), traveler.getFullName(), seat.getId(), seat.getSeatNumber()));
        }

        return new BookingResponse(
                booking.getId(),
                booking.getBookingRef(),
                flight.getId(),
                booking.getStatus().name(),
                booking.getNumberOfPassengers(),
                booking.getTotalAmount(),
                booking.getCreatedAt(),
                items
        );
    }

    public List<Booking> listBookingsByOwner(UUID ownerPassengerId) {
        return bookingRepo.findByPassengerId(ownerPassengerId);
    }

    private String generateBookingRef() {
        return "BK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public List<Booking> myBookings() {
        var user = com.keison.reservation.security.SecurityUtils.currentUser();
        Passenger p = passengerRepo.findByUserId(user.getId())
                .orElseThrow(() -> new BadRequestException("Passenger profile not found"));
        return bookingRepo.findByPassengerId(p.getId());
    }

    @Transactional
    public Booking cancelMyBooking(UUID bookingId) {
        var user = SecurityUtils.currentUser();

        var passenger = passengerRepo.findByUserId(user.getId())
                .orElseThrow(() -> new BadRequestException("Passenger profile not found"));

        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new BadRequestException("Booking not found"));

        if (!booking.getPassenger().getId().equals(passenger.getId())) {
            throw new BadRequestException("You can only cancel your own booking");
        }

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new BadRequestException("Only PENDING bookings can be cancelled");
        }

        // release seats: RESERVED -> AVAILABLE
        List<UUID> detailIds = detailsRepo.findByBookingId(booking.getId()).stream()
                .map(d -> d.getId())
                .toList();

        List<BookingPassenger> all = new ArrayList<>();
        for (UUID detailId : detailIds) {
            all.addAll(bookingPassengerRepo.findByBookingDetailId(detailId));
        }

        List<FlightSeat> seatsToUpdate = new ArrayList<>();
        for (BookingPassenger bp : all) {
            if (bp.getSeat() != null && bp.getSeat().getStatus() == SeatStatus.RESERVED) {
                FlightSeat seat = bp.getSeat();
                seat.setStatus(SeatStatus.AVAILABLE);
                seatsToUpdate.add(seat);
            }
        }
        if (!seatsToUpdate.isEmpty()) {
            seatRepo.saveAll(seatsToUpdate);
        }

        booking.setStatus(BookingStatus.CANCELLED);
        return bookingRepo.save(booking);
    }

    @Transactional
    public Booking cancelBookingAsStaff(UUID bookingId) {

        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new BadRequestException("Booking not found"));

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            return booking; // already cancelled
        }

        if (booking.getStatus() == BookingStatus.CONFIRMED) {
            throw new BadRequestException("Cannot cancel a CONFIRMED booking (refund not implemented)");
        }

        // booking is PENDING -> release reserved seats
        List<UUID> detailIds = detailsRepo.findByBookingId(booking.getId()).stream()
                .map(d -> d.getId())
                .toList();

        List<BookingPassenger> all = new ArrayList<>();
        for (UUID detailId : detailIds) {
            all.addAll(bookingPassengerRepo.findByBookingDetailId(detailId));
        }

        List<FlightSeat> seatsToUpdate = new ArrayList<>();
        for (BookingPassenger bp : all) {
            if (bp.getSeat() != null && bp.getSeat().getStatus() == SeatStatus.RESERVED) {
                FlightSeat seat = bp.getSeat();
                seat.setStatus(SeatStatus.AVAILABLE);
                seatsToUpdate.add(seat);
            }
        }

        if (!seatsToUpdate.isEmpty()) {
            seatRepo.saveAll(seatsToUpdate);
        }

        booking.setStatus(BookingStatus.CANCELLED);
        return bookingRepo.save(booking);
    }

}
