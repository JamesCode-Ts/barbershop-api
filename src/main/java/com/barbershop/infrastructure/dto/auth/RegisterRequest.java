package com.barbershop.infrastructure.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 150, message = "Invalid name size")
    private String name;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, max = 100, message = "Invalid password size")
    private String password;

    @NotBlank(message = "Barbershop slug cannot be empty")
    private String barbershopSlug; // <-- NOVO!
}
