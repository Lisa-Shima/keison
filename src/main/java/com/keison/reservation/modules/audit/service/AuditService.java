package com.keison.reservation.modules.audit.service;

import com.keison.reservation.modules.audit.entity.AuditLog;
import com.keison.reservation.modules.audit.repo.AuditLogRepository;
import com.keison.reservation.modules.users.entity.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class AuditService {

    private final AuditLogRepository auditRepo;

    public AuditService(AuditLogRepository auditRepo) {
        this.auditRepo = auditRepo;
    }

    public void log(User actor, String action, String entityType, UUID entityId) {
        AuditLog log = new AuditLog();
        log.setActorUserId(actor.getId());
        log.setActorRole(actor.getRole().name());
        log.setAction(action);
        log.setEntityType(entityType);
        log.setEntityId(entityId);
        log.setCreatedAt(Instant.now());
        auditRepo.save(log);
    }
}
