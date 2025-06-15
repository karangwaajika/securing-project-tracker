package com.lab.securing_project_tracker.controller;

import com.lab.securing_project_tracker.dto.authentication.AuthenticationRequest;
import com.lab.securing_project_tracker.dto.authentication.AuthenticationResponse;
import com.lab.securing_project_tracker.dto.authentication.UserRegisterDto;
import com.lab.securing_project_tracker.dto.authentication.UserResponseDto;
import com.lab.securing_project_tracker.model.AuditLogEntity;
import com.lab.securing_project_tracker.security.jwt.JwtUtil;
import com.lab.securing_project_tracker.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication Controller",
        description = "Manage all the Authentication 's urls")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public AuthenticationController(UserService userService,
                                    JwtUtil jwtUtil,
                                    AuthenticationManager authenticationManager,
                                    UserDetailsService userDetailsService) {

        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register user",
            description = "This request inserts a project to the database and returns " +
                                                        "the inserted project ")
    public ResponseEntity<UserResponseDto> registerUser(
            @RequestBody UserRegisterDto userRegisterDto
            ) {
        UserResponseDto savedUser = this.userService.create(userRegisterDto);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    @Operation(summary = "Log user in",
            description = "Upon successful credentials, user is authenticated " +
                          "and a new token is generated")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(
            @RequestBody AuthenticationRequest authenticationRequest) {

        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(), authenticationRequest.getPassword()));

        final UserDetails userDetails = this.userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String jwt = this.jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @GetMapping("/oauth2/success")
    @Operation(summary = "Log in using oauth2",
            description = "Upon successful credentials, user is authenticated " +
                          "and a new token is generated")
    public Map<String,String> success(@RequestParam String token) {
        return Map.of("token", token);
    }
}

