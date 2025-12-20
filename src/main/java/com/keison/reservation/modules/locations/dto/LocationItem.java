package com.keison.reservation.modules.locations.dto;

import java.util.UUID;

public class LocationItem {
    private UUID id;
    private String name;

    public LocationItem(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
}
