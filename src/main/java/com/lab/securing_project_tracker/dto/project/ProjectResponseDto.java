package com.lab.securing_project_tracker.dto.project;

import com.lab.securing_project_tracker.dto.task.TaskDtoSummary;
import com.lab.securing_project_tracker.util.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectResponseDto {
    private Long id;
    private String name;
    private String description;
    private LocalDate deadline;
    private ProjectStatus status;
    private LocalDateTime createdAt = LocalDateTime.now();
    private List<TaskDtoSummary> tasks;
}
