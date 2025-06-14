package com.lab.securing_project_tracker.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "audit_logs")
public class AuditLogEntity {
    @Id
    private String id;

    private String actionType;
    private String entityType;
    private String entityId;
    private Instant timestamp;
    private String actorName;

    private Map<String, Object> payload;
}
