package com.keison.reservation.modules.auth.controller;

import com.keison.reservation.modules.auth.dto.AuthResponse;
import com.keison.reservation.modules.auth.dto.LoginRequest;
import com.keison.reservation.modules.auth.dto.RegisterPassengerRequest;
import com.keison.reservation.modules.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register/passenger")
    public AuthResponse registerPassenger(@Valid @RequestBody RegisterPassengerRequest req) {
        return authService.registerPassenger(req);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest req) {
        return authService.login(req);
    }
}
