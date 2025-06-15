package com.lab.securing_project_tracker.dto.developer;

import com.lab.securing_project_tracker.dto.skill.SkillResponseDto;
import com.lab.securing_project_tracker.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

// this dto is used when developer is a sub-object of a user class
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDeveloperResponseDto {
    private Long id;
    private String name;
    private Set<SkillResponseDto> skills;
}
