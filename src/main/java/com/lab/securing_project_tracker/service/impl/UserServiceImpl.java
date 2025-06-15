package com.lab.securing_project_tracker.service.impl;

import com.lab.securing_project_tracker.dto.authentication.UserRegisterDto;
import com.lab.securing_project_tracker.dto.authentication.UserResponseDto;
import com.lab.securing_project_tracker.dto.developer.DeveloperDto;
import com.lab.securing_project_tracker.exception.DeveloperNotFoundException;
import com.lab.securing_project_tracker.exception.UserExistsException;
import com.lab.securing_project_tracker.mapper.DeveloperMapper;
import com.lab.securing_project_tracker.mapper.UserMapper;
import com.lab.securing_project_tracker.model.DeveloperEntity;
import com.lab.securing_project_tracker.model.SkillEntity;
import com.lab.securing_project_tracker.model.UserEntity;
import com.lab.securing_project_tracker.repository.UserRepository;
import com.lab.securing_project_tracker.service.SkillService;
import com.lab.securing_project_tracker.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    SkillService skillService;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           SkillService skillService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.skillService = skillService;
    }

    @Override
    public UserResponseDto create(UserRegisterDto userDto) {
        if(findByEmail(userDto.getEmail()).isPresent()){
            throw new UserExistsException(
                    String.format("User with email '%s' exists already",
                            userDto.getEmail()));
        }
        //extract a developer dto to convert it to developer entity
        DeveloperDto developerDto = userDto.getDeveloper();
        if(developerDto == null){
            throw new DeveloperNotFoundException("A user must have an associated class with it, " +
                                                 "cannot create user alone!");
        }
        Set<SkillEntity> skills = new HashSet<>(skillService.findAllById(developerDto.getSkillIds()));
        DeveloperEntity developerEntity = DeveloperMapper.toEntity(developerDto, skills);


        // hash the password
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        UserEntity user = UserMapper.toEntity(userDto, developerEntity);

        developerEntity.setUser(user);          // owning side – FK column
        user.setDeveloper(developerEntity);     // inverse side

        UserEntity savedUser = this.userRepository.save(user);

        return UserMapper.toDto(savedUser);
    }

    @Override
    public Optional<UserEntity> findByEmail(String username) {
        return this.userRepository.findByEmail(username);
    }

    @Override
    public Page<UserResponseDto> findAll(Pageable pageable) {
        Page<UserEntity> users = this.userRepository.findAll(pageable);
        return users.map(UserMapper::toDto);
    }
}
