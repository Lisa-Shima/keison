package com.keison.reservation.modules.flights.repo;

import com.keison.reservation.modules.flights.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FlightRepository extends JpaRepository<Flight, UUID> {
    boolean existsByFlightNo(String flightNo);
    List<Flight> findByOriginAirportIdAndDestAirportId(UUID originAirportId, UUID destAirportId);
    List<Flight> findByOriginAirportCodeAndDestAirportCode(String originAirportCode, String destAirportCode);

}
