package com.keison.reservation.modules.bookings.repo;

import com.keison.reservation.modules.bookings.entity.BookingPassenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookingPassengerRepository extends JpaRepository<BookingPassenger, UUID> {
    List<BookingPassenger> findByBookingDetailId(UUID bookingDetailId);
}
