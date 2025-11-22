package com.barbershop.infrastructure.dto.service;

import com.barbershop.domain.entity.ServiceEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ServiceDTO {

    private final String id;
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final Integer durationMinutes;

    public static ServiceDTO create(ServiceEntity service) {
        return new ServiceDTO(
                service.getId(),
                service.getName(),
                service.getDescription(),
                service.getPrice(),
                service.getDurationMinutes()
        );
    }
}
