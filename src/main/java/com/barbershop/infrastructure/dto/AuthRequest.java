package com.barbershop.infrastructure.dto;

import lombok.*;
/**
 * Data Transfer Object representing the user's login request,
 * containing only the credentials required for authentication.
 */

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AuthRequest {
    private String email;
    private String password;
}