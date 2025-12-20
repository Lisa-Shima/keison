package com.keison.reservation.modules.locations.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class CreateCellRequest {
    @NotBlank private String name;
    @NotNull private UUID sectorId;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public UUID getSectorId() { return sectorId; }
    public void setSectorId(UUID sectorId) { this.sectorId = sectorId; }
}
