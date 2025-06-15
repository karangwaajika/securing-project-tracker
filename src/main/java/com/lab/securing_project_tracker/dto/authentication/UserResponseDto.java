package com.lab.securing_project_tracker.dto.authentication;

import com.lab.securing_project_tracker.dto.developer.UserDeveloperResponseDto;
import com.lab.securing_project_tracker.util.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {
    private Long id;
    private String email;
    private Role role;
    private UserDeveloperResponseDto developer;
}
