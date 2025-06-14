package com.lab.securing_project_tracker.service.impl;

import com.lab.securing_project_tracker.dto.authentication.UserRegisterDto;
import com.lab.securing_project_tracker.dto.developer.DeveloperDto;
import com.lab.securing_project_tracker.dto.developer.DeveloperResponseDto;
import com.lab.securing_project_tracker.exception.DeveloperExistsException;
import com.lab.securing_project_tracker.mapper.DeveloperMapper;
import com.lab.securing_project_tracker.model.DeveloperEntity;
import com.lab.securing_project_tracker.model.SkillEntity;
import com.lab.securing_project_tracker.model.UserEntity;
import com.lab.securing_project_tracker.repository.DeveloperRepository;
import com.lab.securing_project_tracker.service.DeveloperService;
import com.lab.securing_project_tracker.service.SkillService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DeveloperServiceImpl implements DeveloperService {
    SkillService skillService;
    DeveloperRepository developerRepository;
    PasswordEncoder passwordEncoder;

    public DeveloperServiceImpl(SkillService skillService,
                                DeveloperRepository developerRepository,
                                PasswordEncoder passwordEncoder) {
        this.skillService = skillService;
        this.developerRepository = developerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public DeveloperResponseDto create(DeveloperDto developerDto) {

        if (findDeveloperByEmail(developerDto.getEmail()).isPresent()) {
            throw new DeveloperExistsException(
                    String.format("A developer with the email '%s' already exists", developerDto.getEmail()));
        }

        Set<SkillEntity> skills = new HashSet<>(skillService.findAllById(developerDto.getSkillIds()));
        DeveloperEntity developer = DeveloperMapper.toEntity(developerDto, skills);

        //create user
        UserRegisterDto userDto = developerDto.getUser();
        UserEntity userEntity = UserEntity.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(userDto.getRole())
                .build();

        developer.setUser(userEntity);          // dev owns FK
        userEntity.setDeveloper(developer);     // inverse side (cascade target)

        DeveloperEntity saved = developerRepository.save(developer);

        return DeveloperMapper.toResponseDto(saved);
    }

    @Override
    public Optional<DeveloperEntity> findDeveloperById(Long id) {
        return this.developerRepository.findById(id);
    }

    @Override
    public Optional<DeveloperEntity> findDeveloperByEmail(String email) {

        return this.developerRepository.findDeveloperByEmail(email);
    }

    @Override
    public List<DeveloperResponseDto> findAllDevelopers() {
        List<DeveloperEntity> developers = this.developerRepository.findAll();
        return developers.stream().map(DeveloperMapper::toResponseDto).toList();
    }
}
