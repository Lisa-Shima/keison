package com.keison.reservation.modules.flights.dto;

import com.keison.reservation.common.enums.TravelClass;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class GenerateSeatsRequest {

    @NotNull
    private TravelClass travelClass;

    @Min(1)
    private int count;

    @NotNull
    private BigDecimal price;

    public TravelClass getTravelClass() { return travelClass; }
    public void setTravelClass(TravelClass travelClass) { this.travelClass = travelClass; }

    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}
