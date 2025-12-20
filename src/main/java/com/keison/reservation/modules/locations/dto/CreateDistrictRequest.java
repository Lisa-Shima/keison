package com.keison.reservation.modules.locations.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class CreateDistrictRequest {
    @NotBlank private String name;
    @NotNull private UUID provinceId;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public UUID getProvinceId() { return provinceId; }
    public void setProvinceId(UUID provinceId) { this.provinceId = provinceId; }
}
