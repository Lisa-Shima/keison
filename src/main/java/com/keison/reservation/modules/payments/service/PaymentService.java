package com.keison.reservation.modules.payments.service;

import com.keison.reservation.common.enums.BookingStatus;
import com.keison.reservation.common.enums.PaymentStatus;
import com.keison.reservation.common.enums.SeatStatus;
import com.keison.reservation.common.enums.TicketStatus;
import com.keison.reservation.common.exception.BadRequestException;
import com.keison.reservation.modules.bookings.entity.Booking;
import com.keison.reservation.modules.bookings.entity.BookingPassenger;
import com.keison.reservation.modules.bookings.repo.BookingDetailsRepository;
import com.keison.reservation.modules.bookings.repo.BookingPassengerRepository;
import com.keison.reservation.modules.bookings.repo.BookingRepository;
import com.keison.reservation.modules.flights.entity.FlightSeat;
import com.keison.reservation.modules.flights.repo.FlightSeatRepository;
import com.keison.reservation.modules.passengers.entity.Passenger;
import com.keison.reservation.modules.passengers.repo.PassengerRepository;
import com.keison.reservation.modules.payments.dto.PayBookingRequest;
import com.keison.reservation.modules.payments.dto.PayBookingResponse;
import com.keison.reservation.modules.payments.entity.Payment;
import com.keison.reservation.modules.payments.repo.PaymentRepository;
import com.keison.reservation.modules.tickets.entity.Ticket;
import com.keison.reservation.modules.tickets.repo.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

@Service
public class PaymentService {

    private final BookingRepository bookingRepo;
    private final PassengerRepository passengerRepo;
    private final PaymentRepository paymentRepo;
    private final TicketRepository ticketRepo;

    private final BookingDetailsRepository detailsRepo;
    private final BookingPassengerRepository bookingPassengerRepo;
    private final FlightSeatRepository seatRepo;

    public PaymentService(
            BookingRepository bookingRepo,
            PassengerRepository passengerRepo,
            PaymentRepository paymentRepo,
            TicketRepository ticketRepo,
            BookingDetailsRepository detailsRepo,
            BookingPassengerRepository bookingPassengerRepo,
            FlightSeatRepository seatRepo
    ) {
        this.bookingRepo = bookingRepo;
        this.passengerRepo = passengerRepo;
        this.paymentRepo = paymentRepo;
        this.ticketRepo = ticketRepo;
        this.detailsRepo = detailsRepo;
        this.bookingPassengerRepo = bookingPassengerRepo;
        this.seatRepo = seatRepo;
    }

    @Transactional
    public PayBookingResponse payBooking(PayBookingRequest req) {
        Booking booking = bookingRepo.findById(req.getBookingId())
                .orElseThrow(() -> new BadRequestException("Booking not found"));

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new BadRequestException("Only PENDING bookings can be paid");
        }

        var user = com.keison.reservation.security.SecurityUtils.currentUser();
        Passenger payer = passengerRepo.findByUserId(user.getId())
                .orElseThrow(() -> new BadRequestException("Passenger profile not found"));


        // simple rule: payer must be booking owner
        if (!booking.getPassenger().getId().equals(payer.getId())) {
            throw new BadRequestException("Payer must be the booking owner");
        }

        // amount must match booking total
        if (req.getAmount().compareTo(booking.getTotalAmount()) != 0) {
            throw new BadRequestException("Payment amount must equal booking total amount");
        }

        // Create payment
        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setPassenger(payer);
        payment.setAmount(req.getAmount());
        payment.setStatus(PaymentStatus.PAID);
        payment.setCreatedAt(Instant.now());
        payment = paymentRepo.save(payment);

        // Find booking passengers and seats
        List<UUID> detailIds = detailsRepo.findByBookingId(booking.getId()).stream()
                .map(d -> d.getId())
                .toList();

        List<BookingPassenger> all = new ArrayList<>();
        for (UUID detailId : detailIds) {
            all.addAll(bookingPassengerRepo.findByBookingDetailId(detailId));
        }

        if (all.isEmpty()) {
            throw new BadRequestException("Booking has no passengers/seats");
        }

        // Seats: RESERVED -> BOOKED
        List<FlightSeat> seatsToUpdate = new ArrayList<>();
        for (BookingPassenger bp : all) {
            if (bp.getSeat() == null) {
                throw new BadRequestException("Booking passenger has no seat linked");
            }
            FlightSeat seat = bp.getSeat();
            if (seat.getStatus() != SeatStatus.RESERVED) {
                throw new BadRequestException("Seat " + seat.getSeatNumber() + " is not reserved");
            }
            seat.setStatus(SeatStatus.BOOKED);
            seatsToUpdate.add(seat);
        }
        seatRepo.saveAll(seatsToUpdate);

        // Booking -> CONFIRMED
        Instant paidAt = Instant.now();
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setPaidAt(paidAt);
        bookingRepo.save(booking);

        // Create tickets (one per passenger-seat)
        List<String> ticketNumbers = new ArrayList<>();
        for (BookingPassenger bp : all) {
            Ticket t = new Ticket();
            t.setTicketNumber(generateTicketNumber());
            t.setPayment(payment);
            t.setPassenger(bp.getPassenger());
            t.setFlight(bp.getFlight());
            t.setSeat(bp.getSeat());
            t.setPassengerName(bp.getPassenger().getFullName());
            t.setSeatNumber(bp.getSeatNumber());
            t.setTravelClass(bp.getSeat().getTravelClass());
            t.setTicketPrice(bp.getSeat().getPrice());
            t.setStatus(TicketStatus.ISSUED);
            t.setIssuedAt(Instant.now());

            ticketRepo.save(t);
            ticketNumbers.add(t.getTicketNumber());
        }

        return new PayBookingResponse(
                payment.getId(),
                booking.getId(),
                booking.getStatus().name(),
                payment.getAmount(),
                paidAt,
                ticketNumbers
        );
    }

    private String generateTicketNumber() {
        return "TK-" + UUID.randomUUID().toString().substring(0, 10).toUpperCase();
    }
}
