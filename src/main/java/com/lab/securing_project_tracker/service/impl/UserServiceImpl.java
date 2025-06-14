package com.lab.securing_project_tracker.service.impl;

import com.lab.securing_project_tracker.dto.authentication.UserRegisterDto;
import com.lab.securing_project_tracker.dto.authentication.UserResponseDto;
import com.lab.securing_project_tracker.exception.UserExistsException;
import com.lab.securing_project_tracker.mapper.UserMapper;
import com.lab.securing_project_tracker.model.UserEntity;
import com.lab.securing_project_tracker.repository.UserRepository;
import com.lab.securing_project_tracker.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDto create(UserRegisterDto userDto) {
        if(findByUsername(userDto.getUsername()).isPresent()){
            throw new UserExistsException(
                    String.format("User with username '%s' exists already",
                            userDto.getUsername()));
        }
        // hash the password
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        UserEntity user = UserMapper.toEntity(userDto);
        UserEntity savedUser = this.userRepository.save(user);

        return UserMapper.toDto(savedUser);
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }
}
