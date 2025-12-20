package com.keison.reservation.modules.bookings.entity;

import com.keison.reservation.modules.flights.entity.Flight;
import com.keison.reservation.modules.flights.entity.FlightSeat;
import com.keison.reservation.modules.passengers.entity.Passenger;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "booking_passenger")
public class BookingPassenger {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "booking_detail_id")
    private BookingDetails bookingDetail;

    @ManyToOne(optional = false)
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;

    @ManyToOne(optional = false)
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private FlightSeat seat;

    @Column(nullable = false, length = 10)
    private String seatNumber;

    @Column(columnDefinition = "TEXT")
    private String note;

    // getters/setters
    public UUID getId() { return id; }

    public BookingDetails getBookingDetail() { return bookingDetail; }
    public void setBookingDetail(BookingDetails bookingDetail) { this.bookingDetail = bookingDetail; }

    public Passenger getPassenger() { return passenger; }
    public void setPassenger(Passenger passenger) { this.passenger = passenger; }

    public Flight getFlight() { return flight; }
    public void setFlight(Flight flight) { this.flight = flight; }

    public FlightSeat getSeat() { return seat; }
    public void setSeat(FlightSeat seat) { this.seat = seat; }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
