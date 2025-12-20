package com.keison.reservation.modules.flights.dto;

import com.keison.reservation.common.enums.SeatStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateSeatStatusRequest {
    @NotNull
    private SeatStatus status;

    public SeatStatus getStatus() { return status; }
    public void setStatus(SeatStatus status) { this.status = status; }
}
