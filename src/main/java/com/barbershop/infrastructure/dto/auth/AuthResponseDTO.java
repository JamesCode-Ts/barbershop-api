package com.barbershop.infrastructure.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDTO {

    private String id;
    private String name;
    private String email;
    private String role;
    private String barbershopId;  // pode ser null
    private String token;         // nunca pode ser null
}
