package com.lab.securing_project_tracker.controller;

import com.lab.securing_project_tracker.dto.project.ProjectDto;
import com.lab.securing_project_tracker.dto.project.ProjectResponseDto;
import com.lab.securing_project_tracker.exception.ProjectNotFoundException;
import com.lab.securing_project_tracker.mapper.ProjectMapper;
import com.lab.securing_project_tracker.model.Project;
import com.lab.securing_project_tracker.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/projects")
@Tag(name = "Project Controller", description = "Manage all the Project's urls")
public class ProjectController {
    ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping(name = "add_project", path = "/add")
    @Operation(summary = "Create project",
            description = "This request inserts a project to the database and returns " +
                          "the inserted project ")
    public ResponseEntity<ProjectResponseDto> addProject(@RequestBody ProjectDto projectDto){
        Project savedProject = this.projectService.create(projectDto);
        ProjectResponseDto savedProjectDto = ProjectMapper.toResponseDto(savedProject);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProjectDto);
    }

    @GetMapping(name = "view_project_by_id", path = "/view/{id}")
    @Operation(summary = "View Project",
            description = "Search and view only one project using project ID")
    public ResponseEntity<ProjectResponseDto> viewProject(@PathVariable Long id){
        Optional<Project> project = this.projectService.findProjectById(id);

        if(project.isEmpty()){
            throw new ProjectNotFoundException(
                    String.format("A project with the Id '%d' doesn't exist", id));
        }
        ProjectResponseDto projectResponseDto = ProjectMapper.toResponseDto(project.get());
        return ResponseEntity.status(HttpStatus.OK).body(projectResponseDto);
    }

    @GetMapping(name = "view_projects", path = "/view")
    @Operation(summary = "View Projects",
            description = "This method applies pagination for efficient retrieval " +
                          "of projects list")
    public Page<ProjectResponseDto> viewProjects(Pageable pageable){
        return this.projectService.findAll(pageable);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PatchMapping(name = "update_project", path = "/update/{id}")
    @Operation(summary = "Update Project",
            description = "The project can be updated partially, " +
                          "it's doesn't necessary required " +
                          "all the fields to be updated")
    public ResponseEntity<ProjectResponseDto> updateProject(@RequestBody ProjectDto projectDto,
                                                 @PathVariable Long id){

        Project updatedProject = this.projectService.partialUpdate(projectDto, id);
        ProjectResponseDto updatedProjectDto = ProjectMapper.toResponseDto(updatedProject);
        return ResponseEntity.status(HttpStatus.OK).body(updatedProjectDto);
    }

    @DeleteMapping(name = "delete_project", path = "/delete")
    @Operation(summary = "Delete Project",
            description = "The project is delete using its id that is retrieved " +
                          "as a query parameter from the url")
    public ResponseEntity<?> deleteProject(@RequestParam Long id){
        this.projectService.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("message", "Project deleted successfully"));
    }

    @GetMapping(name = "view_projects", path = "/viewAll")
    @Operation(summary = "View Projects",
            description = "This method applies pagination for efficient retrieval " +
                          "of projects list")
    public ResponseEntity<List<ProjectResponseDto>> viewAllProjects(){
        List<ProjectResponseDto> projects = this.projectService.findAllProject();
        return ResponseEntity.status(HttpStatus.OK).body(projects);
    }

    @GetMapping(name = "view_projects_no_task", path = "/viewNoTask")
    @Operation(summary = "View Projects",
            description = "This method applies pagination for efficient retrieval " +
                          "of projects list")
    public List<ProjectResponseDto> viewProjectsWithNoTask(Pageable pageable){
        return this.projectService.findProjectsWithoutTasks();
    }



}
