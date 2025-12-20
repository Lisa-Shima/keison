package com.keison.reservation.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProperties {

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expiresMinutes}")
    private long expiresMinutes;

    public String getSecret() {
        return secret;
    }

    public long getExpiresMinutes() {
        return expiresMinutes;
    }
}
