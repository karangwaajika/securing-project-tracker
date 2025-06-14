package com.lab.securing_project_tracker.mapper;

import com.lab.securing_project_tracker.dto.project.ProjectDto;
import com.lab.securing_project_tracker.dto.project.ProjectResponseDto;
import com.lab.securing_project_tracker.dto.task.TaskDtoSummary;
import com.lab.securing_project_tracker.model.Project;
import com.lab.securing_project_tracker.model.TaskEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProjectMapper {
    public static ProjectResponseDto toResponseDto(Project project) {
        ProjectResponseDto dto = new ProjectResponseDto();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setDescription(project.getDescription());
        dto.setDeadline(project.getDeadline());
        dto.setStatus(project.getStatus());
        dto.setCreatedAt(project.getCreatedAt());


        List<TaskDtoSummary> taskDtoList = new ArrayList<>();

        for (TaskEntity task : project.getTaskEntities()) {
            TaskDtoSummary taskDto = toTaskDto(task);
            taskDtoList.add(taskDto);
        }

        dto.setTasks(taskDtoList);
        return dto;
    }

    /* this prevents the invite loop because there is no project again
    * as a task is expected to have project inside it.*/

    private static TaskDtoSummary toTaskDto(TaskEntity task) {
        // without including project
        TaskDtoSummary dto = new TaskDtoSummary();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setDueDate(task.getDueDate());
        return dto;
    }

    public static Project toEntity(ProjectDto dto) {
        Project project = new Project();

        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        project.setDeadline(dto.getDeadline());
        project.setStatus(dto.getStatus());
        project.setCreatedAt(LocalDateTime.now());
        project.setTaskEntities(new ArrayList<>()); // not set

        return project;
    }
}
