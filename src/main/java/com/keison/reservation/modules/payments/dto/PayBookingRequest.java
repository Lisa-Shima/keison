package com.keison.reservation.modules.payments.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public class PayBookingRequest {

    @NotNull
    private UUID bookingId;

//    @NotNull
//    private UUID payerPassengerId;

    @NotNull
    private BigDecimal amount;

    public UUID getBookingId() { return bookingId; }
    public void setBookingId(UUID bookingId) { this.bookingId = bookingId; }

//    public UUID getPayerPassengerId() { return payerPassengerId; }
//    public void setPayerPassengerId(UUID payerPassengerId) { this.payerPassengerId = payerPassengerId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}
