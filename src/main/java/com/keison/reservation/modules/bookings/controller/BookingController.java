package com.keison.reservation.modules.bookings.controller;

import com.keison.reservation.modules.bookings.dto.BookingResponse;
import com.keison.reservation.modules.bookings.dto.CreateBookingRequest;
import com.keison.reservation.modules.bookings.entity.Booking;
import com.keison.reservation.modules.bookings.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@PreAuthorize("hasRole('PASSENGER')")
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public BookingResponse createBooking(@Valid @RequestBody CreateBookingRequest req) {
        return bookingService.createBooking(req);
    }

    // simple listing by owner (until JWT is enforced)
    @GetMapping
    public List<Booking> myBookings() {
        return bookingService.myBookings();
    }

    @PostMapping("/{bookingId}/cancel")
    public Booking cancel(@PathVariable UUID bookingId) {
        return bookingService.cancelMyBooking(bookingId);
    }
}
