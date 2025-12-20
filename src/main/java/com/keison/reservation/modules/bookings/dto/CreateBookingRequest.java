package com.keison.reservation.modules.bookings.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public class CreateBookingRequest {

//    @NotNull
//    private UUID bookingOwnerPassengerId;

    @NotNull
    private UUID flightId;

    @NotNull
    @Size(min = 1)
    private List<TravelerSeat> travelers;

//    public UUID getBookingOwnerPassengerId() { return bookingOwnerPassengerId; }
//    public void setBookingOwnerPassengerId(UUID bookingOwnerPassengerId) { this.bookingOwnerPassengerId = bookingOwnerPassengerId; }

    public UUID getFlightId() { return flightId; }
    public void setFlightId(UUID flightId) { this.flightId = flightId; }

    public List<TravelerSeat> getTravelers() { return travelers; }
    public void setTravelers(List<TravelerSeat> travelers) { this.travelers = travelers; }

    public static class TravelerSeat {
        @NotNull
        private UUID passengerId; // passenger record already exists

        @NotNull
        private UUID seatId;

        private String note;

        public UUID getPassengerId() { return passengerId; }
        public void setPassengerId(UUID passengerId) { this.passengerId = passengerId; }

        public UUID getSeatId() { return seatId; }
        public void setSeatId(UUID seatId) { this.seatId = seatId; }

        public String getNote() { return note; }
        public void setNote(String note) { this.note = note; }
    }
}
