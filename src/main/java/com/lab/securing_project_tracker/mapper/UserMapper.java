package com.lab.securing_project_tracker.mapper;

import com.lab.securing_project_tracker.dto.authentication.AuthenticationRequest;
import com.lab.securing_project_tracker.dto.authentication.UserRegisterDto;
import com.lab.securing_project_tracker.dto.authentication.UserResponseDto;
import com.lab.securing_project_tracker.dto.developer.DeveloperResponseDto;
import com.lab.securing_project_tracker.model.DeveloperEntity;
import com.lab.securing_project_tracker.model.UserEntity;
import com.lab.securing_project_tracker.service.SkillService;

public class UserMapper {

    public static UserResponseDto toDto(UserEntity userEntity) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(userEntity.getId());
        dto.setUsername(userEntity.getUsername());
        dto.setRole(userEntity.getRole());
        DeveloperResponseDto developerResponseDto = DeveloperMapper.toResponseDto(userEntity.getDeveloper());
        dto.setDeveloper(developerResponseDto);

        return dto;
    }

    public static UserEntity toEntity(UserRegisterDto userDto, DeveloperEntity developer) {

        return UserEntity.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .role(userDto.getRole())
                .developer(developer)
                .build();
    }
}
