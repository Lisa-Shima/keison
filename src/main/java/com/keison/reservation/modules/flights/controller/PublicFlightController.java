package com.keison.reservation.modules.flights.controller;

import com.keison.reservation.modules.flights.entity.Flight;
import com.keison.reservation.modules.flights.entity.FlightSeat;
import com.keison.reservation.modules.flights.repo.FlightRepository;
import com.keison.reservation.modules.flights.repo.FlightSeatRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@PreAuthorize("hasRole('PASSENGER')")
@RestController
@RequestMapping("/api/flights")
public class PublicFlightController {

    private final FlightRepository flightRepo;
    private final FlightSeatRepository seatRepo;

    public PublicFlightController(FlightRepository flightRepo, FlightSeatRepository seatRepo) {
        this.flightRepo = flightRepo;
        this.seatRepo = seatRepo;
    }

    @GetMapping("/search")
    public List<Flight> search(@RequestParam String origin, @RequestParam String dest) {
        return flightRepo.findByOriginAirportCodeAndDestAirportCode(
                origin.trim().toUpperCase(),
                dest.trim().toUpperCase()
        );
    }

    @GetMapping("/{flightId}/seats")
    public List<FlightSeat> seats(@PathVariable UUID flightId) {
        return seatRepo.findByFlightId(flightId);
    }
}
