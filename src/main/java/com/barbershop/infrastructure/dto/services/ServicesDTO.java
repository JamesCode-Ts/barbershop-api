package com.barbershop.infrastructure.dto.services;

import com.barbershop.domain.entity.Services;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ServicesDTO {

    private final String id;
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final Integer durationMinutes;

    public static ServicesDTO create(Services service) {
        return new ServicesDTO(
                service.getId(),
                service.getName(),
                service.getDescription(),
                service.getPrice(),
                service.getDurationMinutes()
        );
    }
}
