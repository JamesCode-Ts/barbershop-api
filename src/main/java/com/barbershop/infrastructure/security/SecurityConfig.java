package com.barbershop.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    /**
     * Central security configuration for the application.
     * Defines which endpoints are public, which require authentication,
     * integrates the JWT authentication filter, disables form-based login,
     * configures session management for stateless APIs, and enables H2 console access.
     */
    /**
     * • JwtUtil – Handles JWT creation, validation and claim extraction.
     * • JwtAuthenticationFilter – Validates JWT tokens for every request and sets authentication.
     * • UserDetailsServiceImpl – Loads users from the database for authentication.
     * • SecurityConfig – Defines the security rules, public endpoints, JWT filter, and stateless session.
     * • AuthController (optional) – Handles login/registration and generates tokens.
     */

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/h2-console/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }
}

