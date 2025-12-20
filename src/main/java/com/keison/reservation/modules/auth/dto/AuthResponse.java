package com.keison.reservation.modules.auth.dto;

import java.util.UUID;

public class AuthResponse {
    private UUID userId;
    private UUID passengerId;
    private String email;
    private String role;
    private String token;

    public AuthResponse(UUID userId, UUID passengerId, String email, String role, String token) {
        this.userId = userId;
        this.passengerId = passengerId;
        this.email = email;
        this.role = role;
        this.token = token;
    }

    public UUID getUserId() { return userId; }
    public UUID getPassengerId() { return passengerId; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public String getToken() { return token; }
}
