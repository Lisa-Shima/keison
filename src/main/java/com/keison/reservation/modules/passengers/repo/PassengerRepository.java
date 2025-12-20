package com.keison.reservation.modules.passengers.repo;

import com.keison.reservation.modules.passengers.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PassengerRepository extends JpaRepository<Passenger, UUID> {
    Optional<Passenger> findByUserId(UUID userId);
    boolean existsByPassportNo(String passportNo);
}
