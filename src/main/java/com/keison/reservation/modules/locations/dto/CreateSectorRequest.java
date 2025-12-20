package com.keison.reservation.modules.locations.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class CreateSectorRequest {
    @NotBlank private String name;
    @NotNull private UUID districtId;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public UUID getDistrictId() { return districtId; }
    public void setDistrictId(UUID districtId) { this.districtId = districtId; }
}
