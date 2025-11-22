package com.barbershop.infrastructure.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SaveUserDataDTO {

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 150, message = "Invalid name size")
    private final String name;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private final String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, max = 100, message = "Password too short")
    private final String password;

    @NotNull(message = "Role cannot be null")
    private final String role;  // ADMIN, BARBER, CLIENT
}
