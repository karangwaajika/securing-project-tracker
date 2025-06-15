package com.lab.securing_project_tracker.dto.developer;

import com.lab.securing_project_tracker.dto.authentication.DeveloperUserResponseDto;
import com.lab.securing_project_tracker.dto.skill.SkillResponseDto;
import com.lab.securing_project_tracker.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeveloperResponseDto {
    private Long id;
    private String name;
    private Set<SkillResponseDto> skills;
    private DeveloperUserResponseDto user;
}
