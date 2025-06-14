package com.lab.securing_project_tracker.mapper;

import com.lab.securing_project_tracker.dto.authentication.DeveloperUserResponseDto;
import com.lab.securing_project_tracker.dto.developer.DeveloperDto;
import com.lab.securing_project_tracker.dto.developer.DeveloperResponseDto;
import com.lab.securing_project_tracker.dto.developer.UserDeveloperResponseDto;
import com.lab.securing_project_tracker.dto.skill.SkillResponseDto;
import com.lab.securing_project_tracker.model.DeveloperEntity;
import com.lab.securing_project_tracker.model.SkillEntity;
import com.lab.securing_project_tracker.model.UserEntity;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class DeveloperMapper {

    public static DeveloperResponseDto toResponseDto(DeveloperEntity developerEntity) {
        DeveloperResponseDto dto = new DeveloperResponseDto();
        dto.setId(developerEntity.getId());
        dto.setName(developerEntity.getName());
        dto.setEmail(developerEntity.getEmail());

        UserEntity userEntity = developerEntity.getUser();
        DeveloperUserResponseDto userDto = DeveloperUserResponseDto.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .role(userEntity.getRole())
                .build();

        dto.setUser(userDto);

        Set<SkillResponseDto> skillDTOs = developerEntity.getSkills().stream()
                .map(skill -> {
                    SkillResponseDto s = new SkillResponseDto();
                    s.setId(skill.getId());
                    s.setName(skill.getName());
                    return s;
                }).collect(Collectors.toSet());

        dto.setSkills(skillDTOs);
        return dto;
    }

    public static UserDeveloperResponseDto toUserDevResponseDto(DeveloperEntity developerEntity) {
        UserDeveloperResponseDto dto = new UserDeveloperResponseDto();
        dto.setId(developerEntity.getId());
        dto.setName(developerEntity.getName());
        dto.setEmail(developerEntity.getEmail());

        Set<SkillResponseDto> skillDTOs = developerEntity.getSkills().stream()
                .map(skill -> {
                    SkillResponseDto s = new SkillResponseDto();
                    s.setId(skill.getId());
                    s.setName(skill.getName());
                    return s;
                }).collect(Collectors.toSet());

        dto.setSkills(skillDTOs);
        return dto;
    }

    // Map DTO → Developer entity (requires skills to be passed in!)
    /* this is better, since the skill need service layer. we perform the service layer
    /at the controller level and pass in the needed skills. */
    public static DeveloperEntity toEntity(DeveloperDto developerDto, Set<SkillEntity> skillsFromDb ) {
        DeveloperEntity dev = new DeveloperEntity();
        dev.setName(developerDto.getName());
        dev.setEmail(developerDto.getEmail());
        dev.setSkills(new HashSet<>(skillsFromDb));
        return dev;
    }
}
