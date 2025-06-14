package com.lab.securing_project_tracker.exception;

public class TaskExistsException extends AppException{
    public TaskExistsException(String message) {
        super(message);
    }
}
