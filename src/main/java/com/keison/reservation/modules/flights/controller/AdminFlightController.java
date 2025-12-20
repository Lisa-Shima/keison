package com.keison.reservation.modules.flights.controller;

import com.keison.reservation.modules.flights.dto.*;
import com.keison.reservation.modules.flights.entity.Flight;
import com.keison.reservation.modules.flights.entity.FlightSeat;
import com.keison.reservation.modules.flights.service.FlightAdminService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@PreAuthorize("hasAnyRole('ADMIN','STAFF')")
@RestController
@RequestMapping("/api/admin/flights")
public class AdminFlightController {

    private final FlightAdminService service;

    public AdminFlightController(FlightAdminService service) {
        this.service = service;
    }

    @PostMapping
    public Flight createFlight(@Valid @RequestBody CreateFlightRequest req) {
        return service.createFlight(req);
    }

    @PostMapping("/{flightId}/seats/generate")
    public List<FlightSeat> generateSeats(@PathVariable UUID flightId, @Valid @RequestBody GenerateSeatsRequest req) {
        return service.generateSeats(flightId, req);
    }

    @PutMapping("/seats/{seatId}/price")
    public FlightSeat updateSeatPrice(@PathVariable UUID seatId, @Valid @RequestBody UpdateSeatPriceRequest req) {
        return service.updateSeatPrice(seatId, req);
    }

    @PutMapping("/seats/{seatId}/status")
    public FlightSeat updateSeatStatus(@PathVariable UUID seatId, @Valid @RequestBody UpdateSeatStatusRequest req) {
        return service.updateSeatStatus(seatId, req);
    }
}
