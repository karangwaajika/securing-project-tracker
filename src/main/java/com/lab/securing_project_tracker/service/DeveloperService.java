package com.lab.securing_project_tracker.service;

import com.lab.securing_project_tracker.dto.developer.DeveloperDto;
import com.lab.securing_project_tracker.dto.developer.DeveloperResponseDto;
import com.lab.securing_project_tracker.model.DeveloperEntity;

import java.util.List;
import java.util.Optional;

public interface DeveloperService {
    DeveloperResponseDto create(DeveloperDto developerDto);
    Optional<DeveloperEntity> findDeveloperById(Long id);
    Optional<DeveloperEntity> findDeveloperByEmail(String email);
    List<DeveloperResponseDto> findAllDevelopers();
}
