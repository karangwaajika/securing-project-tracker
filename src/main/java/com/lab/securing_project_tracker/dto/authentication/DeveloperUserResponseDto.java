package com.lab.securing_project_tracker.dto.authentication;

import com.lab.securing_project_tracker.dto.developer.UserDeveloperResponseDto;
import com.lab.securing_project_tracker.util.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//this dto is used when user is sub-object of a developer class
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeveloperUserResponseDto {
    private Long id;
    private String username;
    private Role role;
}
