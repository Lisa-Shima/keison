package com.keison.reservation.modules.audit.controller;

import com.keison.reservation.modules.audit.entity.AuditLog;
import com.keison.reservation.modules.audit.repo.AuditLogRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/api/admin/audit-logs")
public class AdminAuditController {

    private final AuditLogRepository auditRepo;

    public AdminAuditController(AuditLogRepository auditRepo) {
        this.auditRepo = auditRepo;
    }

    @GetMapping
    public List<AuditLog> all() {
        return auditRepo.findAll();
    }
}
