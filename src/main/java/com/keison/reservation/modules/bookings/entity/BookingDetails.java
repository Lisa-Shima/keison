package com.keison.reservation.modules.bookings.entity;

import com.keison.reservation.modules.flights.entity.Flight;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "booking_details")
public class BookingDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @ManyToOne(optional = false)
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @Column(nullable = false, length = 180)
    private String passengerName;

    @Column(nullable = false, length = 10)
    private String seatNumber;

    @Column(nullable = false, length = 80)
    private String airline;

    // getters/setters
    public UUID getId() { return id; }

    public Booking getBooking() { return booking; }
    public void setBooking(Booking booking) { this.booking = booking; }

    public Flight getFlight() { return flight; }
    public void setFlight(Flight flight) { this.flight = flight; }

    public String getPassengerName() { return passengerName; }
    public void setPassengerName(String passengerName) { this.passengerName = passengerName; }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public String getAirline() { return airline; }
    public void setAirline(String airline) { this.airline = airline; }
}
