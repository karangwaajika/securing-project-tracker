package com.lab.securing_project_tracker.dto.project;

import com.lab.securing_project_tracker.util.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/* this is dto is used project is nested */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDtoSummary {
    private Long id;
    private String name;
    private String description;
    private LocalDate deadline;
    private ProjectStatus status;
    private LocalDateTime createdAt = LocalDateTime.now();
}
