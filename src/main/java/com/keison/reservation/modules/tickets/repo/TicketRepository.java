package com.keison.reservation.modules.tickets.repo;

import com.keison.reservation.modules.tickets.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    List<Ticket> findByPaymentId(UUID paymentId);
    List<Ticket> findByPassengerId(UUID passengerId);
}
