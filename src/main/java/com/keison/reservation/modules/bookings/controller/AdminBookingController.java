package com.keison.reservation.modules.bookings.controller;

import com.keison.reservation.common.enums.BookingStatus;
import com.keison.reservation.modules.bookings.entity.Booking;
import com.keison.reservation.modules.bookings.repo.BookingRepository;
import com.keison.reservation.modules.bookings.service.BookingService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@PreAuthorize("hasAnyRole('ADMIN','STAFF')")
@RestController
@RequestMapping("/api/admin/bookings")
public class AdminBookingController {

    private final BookingRepository bookingRepo;
    private final BookingService bookingService; // ✅ DECLARED

    public AdminBookingController(
            BookingRepository bookingRepo,
            BookingService bookingService // ✅ INJECTED
    ) {
        this.bookingRepo = bookingRepo;
        this.bookingService = bookingService;
    }

    @GetMapping
    public List<Booking> all(@RequestParam(required = false) BookingStatus status) {
        if (status == null) {
            return bookingRepo.findAll();
        }
        return bookingRepo.findByStatus(status);
    }

    @GetMapping("/{bookingId}")
    public Booking one(@PathVariable UUID bookingId) {
        return bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    @PostMapping("/{bookingId}/cancel")
    public Booking cancel(@PathVariable UUID bookingId) {
        return bookingService.cancelBookingAsStaff(bookingId);
    }
}
