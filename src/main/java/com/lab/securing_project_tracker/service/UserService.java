package com.lab.securing_project_tracker.service;

import com.lab.securing_project_tracker.dto.authentication.UserRegisterDto;
import com.lab.securing_project_tracker.dto.authentication.UserResponseDto;
import com.lab.securing_project_tracker.model.UserEntity;

import java.util.Optional;

public interface UserService {
    UserResponseDto create(UserRegisterDto userDto);
    Optional<UserEntity> findByEmail(String email);
}
