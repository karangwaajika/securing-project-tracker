package com.lab.securing_project_tracker.service;

import com.lab.securing_project_tracker.dto.project.ProjectDto;
import com.lab.securing_project_tracker.dto.project.ProjectResponseDto;
import com.lab.securing_project_tracker.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    Project create(ProjectDto project);
    Optional<Project> findProjectById(Long id);
    Optional<Project> findProjectByName(String name);
    Page<ProjectResponseDto> findAll(Pageable pageable);
    Project partialUpdate(ProjectDto projectdto, Long id);
    void deleteById(Long id);
    List<ProjectResponseDto> findAllProject();
    List<ProjectResponseDto> findProjectsWithoutTasks();


}
