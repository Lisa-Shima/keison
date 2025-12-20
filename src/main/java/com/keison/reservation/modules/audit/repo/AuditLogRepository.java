package com.keison.reservation.modules.audit.repo;

import com.keison.reservation.modules.audit.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AuditLogRepository extends JpaRepository<AuditLog, UUID> {
    List<AuditLog> findByActorUserId(UUID actorUserId);
}
