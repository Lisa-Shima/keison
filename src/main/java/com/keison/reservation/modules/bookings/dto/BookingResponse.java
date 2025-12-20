package com.keison.reservation.modules.bookings.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class BookingResponse {
    private UUID bookingId;
    private String bookingRef;
    private UUID flightId;
    private String status;
    private int numberOfPassengers;
    private BigDecimal totalAmount;
    private Instant createdAt;
    private List<Item> items;

    public BookingResponse(UUID bookingId, String bookingRef, UUID flightId, String status,
                           int numberOfPassengers, BigDecimal totalAmount, Instant createdAt, List<Item> items) {
        this.bookingId = bookingId;
        this.bookingRef = bookingRef;
        this.flightId = flightId;
        this.status = status;
        this.numberOfPassengers = numberOfPassengers;
        this.totalAmount = totalAmount;
        this.createdAt = createdAt;
        this.items = items;
    }

    public UUID getBookingId() { return bookingId; }
    public String getBookingRef() { return bookingRef; }
    public UUID getFlightId() { return flightId; }
    public String getStatus() { return status; }
    public int getNumberOfPassengers() { return numberOfPassengers; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public Instant getCreatedAt() { return createdAt; }
    public List<Item> getItems() { return items; }

    public static class Item {
        private UUID passengerId;
        private String passengerName;
        private UUID seatId;
        private String seatNumber;

        public Item(UUID passengerId, String passengerName, UUID seatId, String seatNumber) {
            this.passengerId = passengerId;
            this.passengerName = passengerName;
            this.seatId = seatId;
            this.seatNumber = seatNumber;
        }

        public UUID getPassengerId() { return passengerId; }
        public String getPassengerName() { return passengerName; }
        public UUID getSeatId() { return seatId; }
        public String getSeatNumber() { return seatNumber; }
    }
}
