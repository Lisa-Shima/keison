package com.keison.reservation.modules.flights.service;

import com.keison.reservation.common.enums.SeatStatus;
import com.keison.reservation.common.enums.TravelClass;
import com.keison.reservation.common.exception.BadRequestException;
import com.keison.reservation.modules.flights.dto.CreateFlightRequest;
import com.keison.reservation.modules.flights.dto.GenerateSeatsRequest;
import com.keison.reservation.modules.flights.dto.UpdateSeatPriceRequest;
import com.keison.reservation.modules.flights.dto.UpdateSeatStatusRequest;
import com.keison.reservation.modules.flights.entity.Flight;
import com.keison.reservation.modules.flights.entity.FlightSeat;
import com.keison.reservation.modules.flights.repo.FlightRepository;
import com.keison.reservation.modules.flights.repo.FlightSeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FlightAdminService {

    private final FlightRepository flightRepo;
    private final FlightSeatRepository seatRepo;

    public FlightAdminService(FlightRepository flightRepo, FlightSeatRepository seatRepo) {
        this.flightRepo = flightRepo;
        this.seatRepo = seatRepo;
    }

    public Flight createFlight(CreateFlightRequest req) {
        String origin = req.getOriginAirportCode().trim().toUpperCase();
        String dest = req.getDestAirportCode().trim().toUpperCase();

        if (origin.equals(dest)) throw new BadRequestException("Origin and destination cannot be the same");

        if (!req.getArrivalTime().isAfter(req.getDepartureTime())) {
            throw new BadRequestException("Arrival time must be after departure time");
        }

        Flight f = new Flight();
        f.setAirline(req.getAirline());
        f.setFlightNo(req.getFlightNo());
        f.setOriginAirportCode(origin);
        f.setDestAirportCode(dest);
        f.setDepartureTime(req.getDepartureTime());
        f.setArrivalTime(req.getArrivalTime());

        return flightRepo.save(f);
    }

    @Transactional
    public List<FlightSeat> generateSeats(UUID flightId, GenerateSeatsRequest req) {
        Flight flight = flightRepo.findById(flightId)
                .orElseThrow(() -> new BadRequestException("Flight not found"));

        TravelClass travelClass = req.getTravelClass();
        String prefix = travelClass == TravelClass.ECONOMY ? "E" :
                travelClass == TravelClass.BUSINESS ? "B" : "F";

        int startIndex = seatRepo.countByFlightIdAndTravelClass(flightId, travelClass) + 1;

        List<FlightSeat> seats = new ArrayList<>();
        for (int i = 0; i < req.getCount(); i++) {
            FlightSeat s = new FlightSeat();
            s.setFlight(flight);
            s.setSeatNumber(prefix + (startIndex + i));
            s.setTravelClass(travelClass);
            s.setPrice(req.getPrice());
            s.setStatus(SeatStatus.AVAILABLE);
            seats.add(s);
        }

        return seatRepo.saveAll(seats);
    }

    public FlightSeat updateSeatPrice(UUID seatId, UpdateSeatPriceRequest req) {
        FlightSeat seat = seatRepo.findById(seatId)
                .orElseThrow(() -> new BadRequestException("Seat not found"));
        seat.setPrice(req.getPrice());
        return seatRepo.save(seat);
    }

    public FlightSeat updateSeatStatus(UUID seatId, UpdateSeatStatusRequest req) {
        FlightSeat seat = seatRepo.findById(seatId)
                .orElseThrow(() -> new BadRequestException("Seat not found"));
        seat.setStatus(req.getStatus());
        return seatRepo.save(seat);
    }
}
