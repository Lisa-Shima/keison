package com.keison.reservation.modules.flights.repo;

import com.keison.reservation.common.enums.SeatStatus;
import com.keison.reservation.modules.flights.entity.FlightSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FlightSeatRepository extends JpaRepository<FlightSeat, UUID> {
    List<FlightSeat> findByFlightId(UUID flightId);
    List<FlightSeat> findByFlightIdAndStatus(UUID flightId, SeatStatus status);
    long countByFlightId(UUID flightId);
    int countByFlightIdAndTravelClass(UUID flightId, com.keison.reservation.common.enums.TravelClass travelClass);
}
