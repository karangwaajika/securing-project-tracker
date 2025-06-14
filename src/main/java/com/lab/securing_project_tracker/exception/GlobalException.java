package com.lab.securing_project_tracker.exception;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
@Hidden
public class GlobalException {
    @ExceptionHandler(ProjectExistsException.class)
    public ResponseEntity<?> handleProjectExists(ProjectExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("Error", e.getMessage()));
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<?> handleProjectNotFound(ProjectNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("Error", e.getMessage()));
    }

    @ExceptionHandler(TaskExistsException.class)
    public ResponseEntity<?> handleTaskExists(TaskExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("Error", e.getMessage()));
    }

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<?> handleTaskExists(UserExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("Error", e.getMessage()));
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<?> handleTaskNotFound(TaskNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("Error", e.getMessage()));
    }

    @ExceptionHandler(SkillExistsException.class)
    public ResponseEntity<?> handleSkillExists(SkillExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("Error", e.getMessage()));
    }

    @ExceptionHandler(InvalidSkillException.class)
    public ResponseEntity<?> handleSkillExists(InvalidSkillException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("Error", e.getMessage()));
    }

    @ExceptionHandler(DeveloperExistsException.class)
    public ResponseEntity<?> handleDeveloperExists(DeveloperExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("Error", e.getMessage()));
    }

    @ExceptionHandler(DeveloperNotFoundException.class)
    public ResponseEntity<?> handleDeveloperNotFound(DeveloperNotFoundException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("Error", e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException
            (Exception exception, WebRequest webRequest) {
        Map<String, Object> body = new HashMap<>();
        body.put("code", HttpStatus.BAD_REQUEST.value());
        body.put("timestamp", LocalDateTime.now());
        body.put("message", exception.getMessage());
        body.put("path", webRequest.getContextPath());
        body.put("sessionId", webRequest.getSessionId());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Error.class)
    public ResponseEntity<?> handleError
            (Exception exception, WebRequest webRequest) {
        Map<String, Object> body = new HashMap<>();
        body.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("timestamp", LocalDateTime.now());
        body.put("message", exception.getMessage());
        body.put("path", webRequest.getContextPath());
        body.put("sessionId", webRequest.getSessionId());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
