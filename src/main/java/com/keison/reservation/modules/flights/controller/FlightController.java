package com.keison.reservation.modules.flights.controller;

import com.keison.reservation.modules.flights.dto.CreateFlightRequest;
import com.keison.reservation.modules.flights.dto.GenerateSeatsRequest;
import com.keison.reservation.modules.flights.entity.Flight;
import com.keison.reservation.modules.flights.entity.FlightSeat;
import com.keison.reservation.modules.flights.service.FlightService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping
    public Flight createFlight(@Valid @RequestBody CreateFlightRequest req) {
        return flightService.createFlight(req);
    }

    @GetMapping
    public List<Flight> listFlights() {
        return flightService.listFlights();
    }

    @GetMapping("/{flightId}")
    public Flight getFlight(@PathVariable UUID flightId) {
        return flightService.getFlight(flightId);
    }

    @PostMapping("/{flightId}/seats/generate")
    public List<FlightSeat> generateSeats(
            @PathVariable UUID flightId,
            @Valid @RequestBody GenerateSeatsRequest req
    ) {
        return flightService.generateSeats(flightId, req);
    }

    @GetMapping("/{flightId}/seats")
    public List<FlightSeat> listSeats(
            @PathVariable UUID flightId,
            @RequestParam(defaultValue = "false") boolean onlyAvailable
    ) {
        return flightService.listSeats(flightId, onlyAvailable);
    }
}
