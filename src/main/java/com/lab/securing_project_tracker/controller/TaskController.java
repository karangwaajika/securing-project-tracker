package com.lab.securing_project_tracker.controller;

import com.lab.securing_project_tracker.dto.task.TaskDto;
import com.lab.securing_project_tracker.dto.task.TaskResponseDto;
import com.lab.securing_project_tracker.exception.TaskNotFoundException;
import com.lab.securing_project_tracker.mapper.TaskMapper;
import com.lab.securing_project_tracker.model.TaskEntity;
import com.lab.securing_project_tracker.service.TaskService;
import com.lab.securing_project_tracker.util.DeveloperTaskCount;
import com.lab.securing_project_tracker.util.TaskStatusCount;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/tasks")
@Tag(name = "Task Controller", description = "Manage all the Task's urls")
public class TaskController {
    TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping(name = "add_task", path = "/add")
    @Operation(summary = "Create task",
            description = "This request inserts a task to the database and returns " +
                          "the inserted task ")
    public ResponseEntity<TaskResponseDto> addTask(@RequestBody TaskDto taskDto){
        TaskEntity savedTaskEntity = this.taskService.create(taskDto);
        TaskResponseDto taskResponseDto= TaskMapper.toResponseDto(savedTaskEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskResponseDto);
    }

    @GetMapping(name = "view_task_by_id", path = "/view/{id}")
    @Operation(summary = "View Project",
            description = "Search and view only one task using task ID")
    public ResponseEntity<TaskResponseDto> viewTask(@PathVariable Long id){
        Optional<TaskEntity> task = this.taskService.findTaskById(id);

        if(task.isEmpty()){
            throw new TaskNotFoundException(
                    String.format("A task with the Id '%d' doesn't exist", id));
        }
        TaskResponseDto taskResponseDto = TaskMapper.toResponseDto(task.get());
        return ResponseEntity.status(HttpStatus.OK).body(taskResponseDto);
    }

    @GetMapping(name = "view_tasks", path = "/view")
    @Operation(summary = "View Tasks",
            description = "This method applies pagination for efficient retrieval " +
                          "of tasks list")
    public Page<TaskResponseDto> viewTasks(Pageable pageable){
        return this.taskService.findAll(pageable);
    }

    @PreAuthorize("hasRole('DEVELOPER')")
    @PatchMapping(name = "update_task", path = "/update")
    @Operation(summary = "Update Task",
            description = "The task can be updated partially, " +
                          "it's doesn't necessary required " +
                          "all the fields to be updated")
    public ResponseEntity<TaskResponseDto> updateTask(@RequestBody TaskDto taskDto,
                                                      @RequestParam Long taskId,
                                                      Authentication auth) throws AccessDeniedException {

        TaskEntity updatedTask = this.taskService.partialUpdate(taskDto, taskId, auth);
        TaskResponseDto updatedTaskDto = TaskMapper.toResponseDto(updatedTask);
        return ResponseEntity.status(HttpStatus.OK).body(updatedTaskDto);
    }

    @DeleteMapping(name = "delete_task", path = "/delete")
    @Operation(summary = "Delete Task",
            description = "The task is delete using its id that is retrieved " +
                          "as a query parameter from the url")
    public ResponseEntity<?> deleteTask(@RequestParam Long id){
        this.taskService.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("message", "Task deleted successfully"));
    }

    @PutMapping(name = "assign_task", path = "/assign")
    @Operation(summary = "Delete Task",
            description = "The task is assign using its id that is retrieved " +
                          "as a query parameter from the url")
    public ResponseEntity<?> assignTask(@RequestParam Long taskId, @RequestParam Long developerId){
        this.taskService.assignTaskToDeveloper(taskId, developerId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("message", "Task assigned successfully"));
    }

    @GetMapping(name = "sort_tasks_by_dueDate", path = "/sortByDueDateASC")
    @Operation(summary = "View Tasks",
            description = "This method applies pagination for efficient retrieval " +
                          "of tasks list")
    public List<TaskResponseDto> sortByDueDate(){
        return this.taskService.findAllByOrderByDueDateAsc();
    }

    @GetMapping(name = "filter_overdue", path = "/overdue")
    @Operation(summary = "View Tasks",
            description = "This method filter overdue tasks for efficient retrieval " +
                          "of tasks list")
    public List<TaskResponseDto> filterOverdue(){
        return this.taskService.findOverdueTasks();
    }

    @GetMapping(name = "view_to_developer_tasks", path = "/topDeveloper")
    @Operation(summary = "View Tasks",
            description = "This method applies pagination for efficient retrieval " +
                          "of tasks list")
    public List<DeveloperTaskCount> viewTopDevTasks(){
        return this.taskService.findTopDevelopers(PageRequest.of(0, 5));
    }

    @GetMapping(name = "view_tasks_counts", path = "/taskStatusCount")
    @Operation(summary = "View Tasks",
            description = "This method applies pagination for efficient retrieval " +
                          "of tasks list")
    public List<TaskStatusCount> countTasksByStatus(){
        return this.taskService.countTasksByStatus();
    }


}
