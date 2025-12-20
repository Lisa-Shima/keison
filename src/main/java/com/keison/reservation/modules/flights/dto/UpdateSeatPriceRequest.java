package com.keison.reservation.modules.flights.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class UpdateSeatPriceRequest {
    @NotNull
    private BigDecimal price;

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}
