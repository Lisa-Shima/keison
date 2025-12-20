package com.keison.reservation.modules.bookings.repo;

import com.keison.reservation.modules.bookings.entity.BookingDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookingDetailsRepository extends JpaRepository<BookingDetails, UUID> {
    List<BookingDetails> findByBookingId(UUID bookingId);
}
