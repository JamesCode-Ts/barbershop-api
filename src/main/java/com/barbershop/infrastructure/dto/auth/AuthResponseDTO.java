package com.barbershop.infrastructure.dto.auth;

import lombok.Data;

@Data
public class AuthResponseDTO {

    private final String id;
    private final String name;
    private final String email;
    private final String token;
}