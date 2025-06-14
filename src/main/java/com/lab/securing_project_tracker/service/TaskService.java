package com.lab.securing_project_tracker.service;


import com.lab.securing_project_tracker.dto.task.TaskDto;
import com.lab.securing_project_tracker.dto.task.TaskResponseDto;
import com.lab.securing_project_tracker.model.TaskEntity;
import com.lab.securing_project_tracker.util.DeveloperTaskCount;
import com.lab.securing_project_tracker.util.TaskStatusCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    TaskEntity create(TaskDto taskDto);
    Optional<TaskEntity> findTaskById(Long id);
    Optional<TaskEntity> findTaskByTitle(String title);
    Page<TaskResponseDto> findAll(Pageable pageable);
    TaskEntity partialUpdate(TaskDto taskDto, Long id);
    void deleteById(Long id);
    void assignTaskToDeveloper(Long taskId, Long developerId);
    List<TaskResponseDto> findAllByOrderByDueDateAsc();
    List<TaskResponseDto> findOverdueTasks();
    List<DeveloperTaskCount> findTopDevelopers(Pageable pageable);
    List<TaskStatusCount> countTasksByStatus();
}
