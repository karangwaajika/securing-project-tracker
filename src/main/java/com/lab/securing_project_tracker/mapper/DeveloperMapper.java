package com.lab.securing_project_tracker.mapper;

import com.lab.securing_project_tracker.dto.developer.DeveloperDto;
import com.lab.securing_project_tracker.dto.developer.DeveloperResponseDto;
import com.lab.securing_project_tracker.dto.skill.SkillResponseDto;
import com.lab.securing_project_tracker.model.DeveloperEntity;
import com.lab.securing_project_tracker.model.SkillEntity;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class DeveloperMapper {

    public static DeveloperResponseDto toResponseDto(DeveloperEntity developerEntity) {
        DeveloperResponseDto dto = new DeveloperResponseDto();
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

    /*
    public DeveloperEntity toEntity(DeveloperDto developerDto) {
        Set<SkillEntity> skills = new HashSet<>(skillService.
                findAllById(developerDto.getSkillIds()));

        // handle in service layer
        if (skills.size() != developerDto.getSkillIds().size()) {
            throw new InvalidSkillException("Some skill IDs are invalid.");
        }

        return DeveloperEntity.builder()
                .name(developerDto.getName())
                .email(developerDto.getEmail())
                .skills(skills)
                .build();
    }
    */

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
