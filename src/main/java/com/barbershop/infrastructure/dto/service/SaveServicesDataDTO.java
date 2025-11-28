package com.barbershop.infrastructure.dto.service;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SaveServicesDataDTO {

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 120, message = "Invalid name size")
    private final String name;

    @NotBlank(message = "Description cannot be empty")
    private final String description;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    private final BigDecimal price;

    @NotNull(message = "Duration cannot be null")
    @Min(value = 1, message = "Duration must be at least 1 minute")
    private final Integer durationMinutes;
}
