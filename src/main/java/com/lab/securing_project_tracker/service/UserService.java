package com.lab.securing_project_tracker.service;

import com.lab.securing_project_tracker.dto.authentication.UserRegisterDto;
import com.lab.securing_project_tracker.dto.authentication.UserResponseDto;
import com.lab.securing_project_tracker.dto.project.ProjectResponseDto;
import com.lab.securing_project_tracker.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    UserResponseDto create(UserRegisterDto userDto);
    Optional<UserEntity> findByEmail(String email);
    Page<UserResponseDto> findAll(Pageable pageable);
}
