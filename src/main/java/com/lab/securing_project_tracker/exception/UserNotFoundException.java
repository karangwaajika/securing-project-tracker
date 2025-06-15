package com.lab.securing_project_tracker.exception;

public class UserNotFoundException extends AppException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
