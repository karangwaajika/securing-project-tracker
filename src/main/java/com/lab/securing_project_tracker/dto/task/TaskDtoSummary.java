package com.lab.securing_project_tracker.dto.task;

import com.lab.securing_project_tracker.util.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/* this is dto is used task is nested */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDtoSummary {
    private Long id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private TaskStatus status;
}
