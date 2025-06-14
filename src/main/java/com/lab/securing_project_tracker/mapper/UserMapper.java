package com.lab.securing_project_tracker.mapper;

import com.lab.securing_project_tracker.dto.authentication.AuthenticationRequest;
import com.lab.securing_project_tracker.dto.authentication.UserRegisterDto;
import com.lab.securing_project_tracker.dto.authentication.UserResponseDto;
import com.lab.securing_project_tracker.model.UserEntity;

public class UserMapper {

    public static UserResponseDto toDto(UserEntity userEntity) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(userEntity.getId());
        dto.setUsername(userEntity.getUsername());
        dto.setRole(userEntity.getRole());
        dto.setDeveloper(userEntity.getDeveloper());

        return dto;
    }

    public static UserEntity toEntity(UserRegisterDto userDto) {

        return UserEntity.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .role(userDto.getRole())
                .developer(userDto.getDeveloper())
                .build();
    }
}
