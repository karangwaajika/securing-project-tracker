package com.lab.securing_project_tracker.controller;

import com.lab.securing_project_tracker.dto.authentication.UserRegisterDto;
import com.lab.securing_project_tracker.dto.authentication.UserResponseDto;
import com.lab.securing_project_tracker.model.AuditLogEntity;
import com.lab.securing_project_tracker.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication Controller",
        description = "Manage all the Authentication 's urls")
public class AuthenticationController {
    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register user")
    public ResponseEntity<UserResponseDto> getLogs(
            @RequestBody UserRegisterDto userRegisterDto
            ) {
        UserResponseDto savedUser = this.userService.create(userRegisterDto);
        return ResponseEntity.ok(savedUser);
    }
}

