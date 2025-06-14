package com.lab.securing_project_tracker.dto.authentication;

import com.lab.securing_project_tracker.model.DeveloperEntity;
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
    private String username;
    private Role role;
    private DeveloperEntity developer;
}
