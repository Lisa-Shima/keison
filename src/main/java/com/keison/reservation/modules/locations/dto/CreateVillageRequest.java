package com.keison.reservation.modules.locations.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class CreateVillageRequest {
    @NotBlank private String name;
    @NotNull private UUID cellId;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public UUID getCellId() { return cellId; }
    public void setCellId(UUID cellId) { this.cellId = cellId; }
}
