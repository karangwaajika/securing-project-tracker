package com.lab.securing_project_tracker.mapper;

import com.lab.securing_project_tracker.dto.authentication.UserRegisterDto;
import com.lab.securing_project_tracker.dto.authentication.UserResponseDto;
import com.lab.securing_project_tracker.dto.developer.UserDeveloperResponseDto;
import com.lab.securing_project_tracker.model.DeveloperEntity;
import com.lab.securing_project_tracker.model.UserEntity;

public class UserMapper {

    public static UserResponseDto toDto(UserEntity userEntity) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(userEntity.getId());
        dto.setEmail(userEntity.getEmail());
        dto.setRole(userEntity.getRole());
        UserDeveloperResponseDto developerResponseDto = DeveloperMapper.toUserDevResponseDto(userEntity.getDeveloper());
        dto.setDeveloper(developerResponseDto);

        return dto;
    }

    public static UserEntity toEntity(UserRegisterDto userDto, DeveloperEntity developer) {

        return UserEntity.builder()
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .role(userDto.getRole())
                .developer(developer)
                .build();
    }
}
