package com.lab.securing_project_tracker.exception;

public class UserExistsException extends AppException{
    public UserExistsException(String message) {
        super(message);
    }
}
