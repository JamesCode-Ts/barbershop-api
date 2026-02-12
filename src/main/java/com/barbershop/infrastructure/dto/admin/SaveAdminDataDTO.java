package com.barbershop.infrastructure.dto.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Data entry DTO for creating an Admin (BARBERSHOP_ADMIN).
 * Mirrors the style of SaveBarberDataDTO.
 */
@Data
public class SaveAdminDataDTO {

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 150, message = "Invalid name size")
    private final String name;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email")
    private final String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, max = 100, message = "Invalid password size")
    private final String password;
}
