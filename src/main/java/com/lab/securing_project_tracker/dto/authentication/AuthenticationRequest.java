package com.lab.securing_project_tracker.dto.authentication;


import com.lab.securing_project_tracker.model.DeveloperEntity;
import com.lab.securing_project_tracker.util.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationRequest {
    private String email;
    private String password;
}
