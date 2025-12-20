package com.keison.reservation.modules.flights.service;

import com.keison.reservation.common.enums.SeatStatus;
import com.keison.reservation.common.enums.TravelClass;
import com.keison.reservation.common.exception.BadRequestException;
import com.keison.reservation.modules.flights.dto.CreateFlightRequest;
import com.keison.reservation.modules.flights.dto.GenerateSeatsRequest;
import com.keison.reservation.modules.flights.entity.Flight;
import com.keison.reservation.modules.flights.entity.FlightSeat;
import com.keison.reservation.modules.flights.repo.FlightRepository;
import com.keison.reservation.modules.flights.repo.FlightSeatRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FlightService {

    private final FlightRepository flightRepo;
    private final FlightSeatRepository seatRepo;

    public FlightService(FlightRepository flightRepo, FlightSeatRepository seatRepo) {
        this.flightRepo = flightRepo;
        this.seatRepo = seatRepo;
    }

    public Flight createFlight(CreateFlightRequest req) {
        String flightNo = req.getFlightNo().trim().toUpperCase();
        if (flightRepo.existsByFlightNo(flightNo)) {
            throw new BadRequestException("Flight number already exists");
        }
        if (req.getOriginAirportCode().equals(req.getDestAirportCode())) {
            throw new BadRequestException("Origin and destination cannot be the same");
        }

        if (req.getArrivalTime().isBefore(req.getDepartureTime())) {
            throw new BadRequestException("Arrival time cannot be before departure time");
        }

        Flight f = new Flight();
        f.setFlightNo(flightNo);
        f.setAirline(req.getAirline() == null || req.getAirline().isBlank() ? "Keison Airline" : req.getAirline());
        f.setOriginAirportCode(req.getOriginAirportCode());
        f.setDestAirportCode(req.getDestAirportCode());
        f.setDepartureTime(req.getDepartureTime());
        f.setArrivalTime(req.getArrivalTime());

        return flightRepo.save(f);
    }

    public List<Flight> listFlights() {
        return flightRepo.findAll();
    }

    public Flight getFlight(UUID flightId) {
        return flightRepo.findById(flightId)
                .orElseThrow(() -> new BadRequestException("Flight not found"));
    }

    public List<FlightSeat> listSeats(UUID flightId, boolean onlyAvailable) {
        getFlight(flightId); // validate flight exists
        if (onlyAvailable) {
            return seatRepo.findByFlightIdAndStatus(flightId, SeatStatus.AVAILABLE);
        }
        return seatRepo.findByFlightId(flightId);
    }

    public List<FlightSeat> generateSeats(UUID flightId, GenerateSeatsRequest req) {

        Flight flight = flightRepo.findById(flightId)
                .orElseThrow(() -> new BadRequestException("Flight not found"));

        TravelClass travelClass = req.getTravelClass();

        String prefix = switch (travelClass) {
            case ECONOMY -> "E";
            case BUSINESS -> "B";
            case FIRST_CLASS -> "F";
        };

        int existing = seatRepo.countByFlightIdAndTravelClass(flightId, travelClass);
        int start = existing + 1;

        List<FlightSeat> seats = new ArrayList<>();

        for (int i = 0; i < req.getCount(); i++) {
            FlightSeat seat = new FlightSeat();
            seat.setFlight(flight);
            seat.setSeatNumber(prefix + (start + i));
            seat.setTravelClass(travelClass);
            seat.setPrice(req.getPrice());
            seat.setStatus(SeatStatus.AVAILABLE);
            seats.add(seat);
        }

        return seatRepo.saveAll(seats);
    }


    private void addSeats(
            List<FlightSeat> seats,
            Flight flight,
            String prefix,
            int count,
            TravelClass travelClass,
            BigDecimal price
    ) {
        for (int i = 1; i <= count; i++) {
            FlightSeat s = new FlightSeat();
            s.setFlight(flight);
            s.setSeatNumber(prefix + i);
            s.setTravelClass(travelClass);
            s.setPrice(price);
            s.setStatus(SeatStatus.AVAILABLE);
            seats.add(s);
        }
    }
}
