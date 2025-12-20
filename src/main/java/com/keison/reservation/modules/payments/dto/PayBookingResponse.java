package com.keison.reservation.modules.payments.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class PayBookingResponse {

    private UUID paymentId;
    private UUID bookingId;
    private String bookingStatus;
    private BigDecimal paidAmount;
    private Instant paidAt;
    private List<String> ticketNumbers;

    public PayBookingResponse(UUID paymentId, UUID bookingId, String bookingStatus, BigDecimal paidAmount, Instant paidAt, List<String> ticketNumbers) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.bookingStatus = bookingStatus;
        this.paidAmount = paidAmount;
        this.paidAt = paidAt;
        this.ticketNumbers = ticketNumbers;
    }

    public UUID getPaymentId() { return paymentId; }
    public UUID getBookingId() { return bookingId; }
    public String getBookingStatus() { return bookingStatus; }
    public BigDecimal getPaidAmount() { return paidAmount; }
    public Instant getPaidAt() { return paidAt; }
    public List<String> getTicketNumbers() { return ticketNumbers; }
}
