package com.lab.securing_project_tracker.service.impl;

import com.lab.securing_project_tracker.model.AuditLogEntity;
import com.lab.securing_project_tracker.repository.AuditLogRepository;
import com.lab.securing_project_tracker.service.AuditLogService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    private AuditLogRepository auditLogRepository;
    public AuditLogServiceImpl(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Override
    public void logAction(String actionType, String entityType, String entityId, String actor, Map<String, Object> payload) {
        AuditLogEntity log = AuditLogEntity.builder()
                .actionType(actionType)
                .entityType(entityType)
                .entityId(entityId)
                .actorName(actor)
                .timestamp(Instant.now())
                .payload(payload)
                .build();
        this.auditLogRepository.save(log);
    }

    @Override
    public List<AuditLogEntity> getLogs(Optional<String> entityType, Optional<String> actorName) {
        if (entityType.isPresent() && actorName.isPresent()) {
            return auditLogRepository.findByEntityTypeAndActorName(entityType.get(), actorName.get());
        } else if (entityType.isPresent()) {
            return auditLogRepository.findByEntityType(entityType.get());
        } else if (actorName.isPresent()) {
            return auditLogRepository.findByActorName(actorName.get());
        } else {
            return auditLogRepository.findAll();
        }
    }

}
