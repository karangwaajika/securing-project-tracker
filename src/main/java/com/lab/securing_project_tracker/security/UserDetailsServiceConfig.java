package com.exercise.authentication.security;

import com.exercise.authentication.model.User;
import com.exercise.authentication.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@Configuration
public class UserDetailsServiceConfig {
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            Optional<User> user = userRepository.findByUsername(username);
            if (!user.isPresent()) {
                throw new UsernameNotFoundException("User not found");
            }
            // Convert role to authority (Spring expects prefix "ROLE_")
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.get().getRole().name());
            return new org.springframework.security.core
                    .userdetails
                    .User(user.get().getUsername(),
                    user.get().getPassword(),
                    Collections.singletonList(authority));
        };
    }
}
