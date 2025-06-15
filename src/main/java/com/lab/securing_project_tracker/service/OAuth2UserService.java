package com.lab.securing_project_tracker.service;

import com.lab.securing_project_tracker.model.UserEntity;
import com.lab.securing_project_tracker.repository.UserRepository;
import com.lab.securing_project_tracker.util.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class OAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder; // still needed for “local” passwords

    public OAuth2UserService(UserRepository userRepository,
                                   PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder  = encoder;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest req) throws OAuth2AuthenticationException {
        OAuth2User oauthUser = super.loadUser(req);

        String email = extractEmail(oauthUser, req.getClientRegistration().getRegistrationId());

        // If the user does not exist, create one with ROLE_CONTRACTOR
        userRepository.findByEmail(email).orElseGet(() -> {
            UserEntity user = new UserEntity();
            user.setEmail(email);
            user.setPassword(               // not used for OAuth login, but cannot be null
                    encoder.encode(UUID.randomUUID().toString()));
            user.setRole(Role.CONTRACTOR);
            return userRepository.save(user);
        });


        // Map Spring‑Security authority from local DB role
        Role role = userRepository.findByEmail(email).get().getRole();

        Collection<GrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));

        return new DefaultOAuth2User(authorities,
                oauthUser.getAttributes(),
                "id"); // key of the unique attribute
    }

    private String extractEmail(OAuth2User user, String registrationId) {
        if ("google".equals(registrationId)) {
            return (String) user.getAttributes().get("email");
        }
        // GitHub sometimes returns null if email is private ⇒ fall back to primary email API call
        Object emailAttr = user.getAttributes().get("email");
        if (emailAttr != null) return emailAttr.toString();
        throw new OAuth2AuthenticationException("Could not determine email from " + registrationId);
    }
}
