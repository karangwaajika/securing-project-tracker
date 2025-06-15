package com.lab.securing_project_tracker.security;

import com.lab.securing_project_tracker.model.UserEntity;
import com.lab.securing_project_tracker.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.Optional;

@Configuration
public class UserDetailsServiceConfig {
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            Optional<UserEntity> user = userRepository.findByEmail(username);
            if (user.isEmpty()) {
                throw new UsernameNotFoundException("User not found");
            }
            // Convert role to authority (Spring expects prefix "ROLE_")
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.get().getRole().name());
            return new org.springframework.security.core
                    .userdetails
                    .User(user.get().getEmail(),
                    user.get().getPassword(),
                    Collections.singletonList(authority));
        };
    }
}
