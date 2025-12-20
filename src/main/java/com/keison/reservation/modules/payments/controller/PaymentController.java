package com.keison.reservation.modules.payments.controller;

import com.keison.reservation.modules.payments.dto.PayBookingRequest;
import com.keison.reservation.modules.payments.dto.PayBookingResponse;
import com.keison.reservation.modules.payments.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasRole('PASSENGER')")
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/pay-booking")
    public PayBookingResponse payBooking(@Valid @RequestBody PayBookingRequest req) {
        return paymentService.payBooking(req);
    }
}
