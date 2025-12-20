package com.keison.reservation.modules.bookings.repo;

import com.keison.reservation.common.enums.BookingStatus;
import com.keison.reservation.modules.bookings.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {
    Optional<Booking> findByBookingRef(String bookingRef);
    List<Booking> findByPassengerId(UUID passengerId);
    List<Booking> findByStatus(BookingStatus status);
}
