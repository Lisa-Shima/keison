package com.keison.reservation.modules.tickets.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class TicketItemResponse {
    private UUID id;
    private String ticketNumber;
    private String status;
    private Instant issuedAt;

    private String flightNo;
    private Instant departureTime;
    private Instant arrivalTime;

    private String origin;
    private String destination;

    private String seatNumber;
    private String travelClass;
    private BigDecimal price;

    public TicketItemResponse(UUID id, String ticketNumber, String status, Instant issuedAt,
                              String flightNo, Instant departureTime, Instant arrivalTime,
                              String origin, String destination,
                              String seatNumber, String travelClass, BigDecimal price) {
        this.id = id;
        this.ticketNumber = ticketNumber;
        this.status = status;
        this.issuedAt = issuedAt;
        this.flightNo = flightNo;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.origin = origin;
        this.destination = destination;
        this.seatNumber = seatNumber;
        this.travelClass = travelClass;
        this.price = price;
    }

    public UUID getId() { return id; }
    public String getTicketNumber() { return ticketNumber; }
    public String getStatus() { return status; }
    public Instant getIssuedAt() { return issuedAt; }
    public String getFlightNo() { return flightNo; }
    public Instant getDepartureTime() { return departureTime; }
    public Instant getArrivalTime() { return arrivalTime; }
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }
    public String getSeatNumber() { return seatNumber; }
    public String getTravelClass() { return travelClass; }
    public BigDecimal getPrice() { return price; }
}
