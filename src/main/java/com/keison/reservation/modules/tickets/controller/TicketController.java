package com.keison.reservation.modules.tickets.controller;

import com.keison.reservation.modules.tickets.entity.Ticket;
import com.keison.reservation.modules.tickets.repo.TicketRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketRepository ticketRepo;

    public TicketController(TicketRepository ticketRepo) {
        this.ticketRepo = ticketRepo;
    }

    @GetMapping("/by-passenger")
    public List<Ticket> byPassenger(@RequestParam UUID passengerId) {
        return ticketRepo.findByPassengerId(passengerId);
    }

    @GetMapping("/by-payment")
    public List<Ticket> byPayment(@RequestParam UUID paymentId) {
        return ticketRepo.findByPaymentId(paymentId);
    }
}
