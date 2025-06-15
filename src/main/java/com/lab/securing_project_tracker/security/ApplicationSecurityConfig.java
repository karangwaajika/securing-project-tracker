package com.lab.securing_project_tracker.security;

import com.lab.securing_project_tracker.repository.UserRepository;
import com.lab.securing_project_tracker.security.jwt.JwtAuthenticationFilter;
import com.lab.securing_project_tracker.security.jwt.JwtAuthorizationFilter;
import com.lab.securing_project_tracker.security.jwt.JwtUtil;
import com.lab.securing_project_tracker.security.oauth.OAuth2AuthenticationSuccessHandler;
import com.lab.securing_project_tracker.service.OAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    private final OAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2LoginSuccessHandler;

    public ApplicationSecurityConfig(JwtUtil jwtUtil,
                                     UserDetailsService userDetailsService,
                                     UserRepository userRepository,
                                     OAuth2UserService customOAuth2UserService,
                                     OAuth2AuthenticationSuccessHandler oAuth2LoginSuccessHandler) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.customOAuth2UserService = customOAuth2UserService;
        this.oAuth2LoginSuccessHandler = oAuth2LoginSuccessHandler;
    }

    // API CHAIN
    @Bean
    @Order(1)
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.securityMatcher("/api/**")
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui.html",
                                "/swagger-ui/**").hasRole("ADMIN")

                        .requestMatchers("/api/users/view").hasRole("ADMIN")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/users/delete/*").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        // 401 when there is *no* or *bad* token
                        .authenticationEntryPoint(
                                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                        // 403 when token is valid but role is insufficient
                        .accessDeniedHandler((req, res, ae) ->
                                res.sendError(HttpStatus.FORBIDDEN.value(), "Forbidden")))
                .addFilter(new JwtAuthenticationFilter(authenticationManager(httpSecurity.getSharedObject(AuthenticationConfiguration.class)), jwtUtil))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(httpSecurity.getSharedObject(AuthenticationConfiguration.class)), jwtUtil, userDetailsService));
        return httpSecurity.build();
    }


    //  WEB / OAUTH2 CHAIN
    @Bean
    @Order(2)
    public SecurityFilterChain webChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/css/**", "/js/**","/webjars/**", "/images/**",
                                "/login", "/oauth2/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/login"))       // optional classic login
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService))
                        .successHandler(oAuth2LoginSuccessHandler))

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true));

        // Browser chain keeps default session + CSRF settings

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }



    // the custom user creation bean
    @Bean
    public OAuth2UserService customOAuth2UserService(UserRepository userRepository,
                                                     PasswordEncoder passwordEncoder) {
        return new OAuth2UserService(userRepository, passwordEncoder);
    }

    @Bean
    public OAuth2AuthenticationSuccessHandler oAuth2LoginSuccessHandler() {
        return new OAuth2AuthenticationSuccessHandler(jwtUtil, userRepository);
    }

}
