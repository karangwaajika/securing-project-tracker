package com.lab.securing_project_tracker.service.impl;

import com.lab.securing_project_tracker.dto.project.ProjectDto;
import com.lab.securing_project_tracker.dto.project.ProjectResponseDto;
import com.lab.securing_project_tracker.exception.ProjectExistsException;
import com.lab.securing_project_tracker.exception.ProjectNotFoundException;
import com.lab.securing_project_tracker.mapper.ProjectMapper;
import com.lab.securing_project_tracker.model.Project;
import com.lab.securing_project_tracker.repository.ProjectRepository;
import com.lab.securing_project_tracker.service.AuditLogService;
import com.lab.securing_project_tracker.service.ProjectService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {
    ProjectRepository projectRepository;
    AuditLogService auditLogService;

    public ProjectServiceImpl(ProjectRepository projectRepository,
                              AuditLogService auditLogService){

        this.projectRepository = projectRepository;
        this.auditLogService = auditLogService;
    }

    @Override
    public Project create(ProjectDto projectDto) {
        if(findProjectByName(projectDto.getName()).isPresent()){
            throw new ProjectExistsException(
                    String.format("A project with the name '%s' already exist",
                            projectDto.getName()));
        }

        Project project = ProjectMapper.toEntity(projectDto);
        Project savedProject = this.projectRepository.save(project);

        // insert log action for create project
        this.auditLogService.logAction(
                "CREATE", "Project", savedProject.getId().toString(), "user",
                Map.of("name", savedProject.getName(), "description", savedProject.getDescription())
        );
        return savedProject;
    }

    @Override
    public Optional<Project> findProjectByName(String name) {
        return this.projectRepository.findProjectByName(name);
    }

    @Override
    public Page<ProjectResponseDto> findAll(Pageable pageable) {
        Page<Project> projectPage = this.projectRepository.findAll(pageable);
        //project -> ProjectMapper.toResponseDto(project)
        return projectPage.map(ProjectMapper::toResponseDto);
    }

    @Override
    @CacheEvict(value = "projects", allEntries = true)
    public Project partialUpdate(ProjectDto projectDto, Long id) {
        Project project = findProjectById(id)
                .orElseThrow( () -> new ProjectNotFoundException(
                        String.format("A project with the Id '%d' doesn't exist", id))
                );
        // update only fields that are provided
        if(projectDto.getName() != null){
            project.setName(projectDto.getName());
        }
        if(projectDto.getDeadline() != null){
            project.setDeadline(projectDto.getDeadline());
        }
        if(projectDto.getDescription() != null){
            project.setDescription(projectDto.getDescription());
        }
        if(projectDto.getStatus() != null){
            project.setStatus(projectDto.getStatus());
        }

        // insert log action for update project
        this.auditLogService.logAction(
                "UPDATE", "Project", project.getId().toString(), "user",
                Map.of("name", project.getName(), "description", project.getDescription())
        );

        return this.projectRepository.save(project);
    }

    @Override
    @CacheEvict(value = "projects", allEntries = true)
    @Transactional
    public void deleteById(Long id) {
        Project project = findProjectById(id)
                .orElseThrow( () -> new ProjectNotFoundException(
                        String.format("A project with the Id '%d' doesn't exist", id))
                );

        // insert log action for delete project
        this.auditLogService.logAction(
                "DELETE", "Project", project.getId().toString(), "user",
                Map.of("name", project.getName(), "description", project.getDescription())
        );

        this.projectRepository.deleteById(id);
    }

    @Override
    @Cacheable("projects")
    public List<ProjectResponseDto> findAllProject() {
        System.out.println("Fetching projects from DB..."); // to verify caching
        List<Project> tasks = this.projectRepository.findAll();
        return tasks.stream().map(ProjectMapper::toResponseDto).toList();
    }

    @Override
    public List<ProjectResponseDto> findProjectsWithoutTasks() {
        List<Project> projects = this.projectRepository.findProjectsWithoutTasks();
        return projects.stream().map(ProjectMapper::toResponseDto).toList();
    }

    @Override
    public Optional<Project> findProjectById(Long id) {
        return this.projectRepository.findById(id);
    }

}
