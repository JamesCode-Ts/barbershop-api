package com.barbershop.infrastructure.dto.scheduling;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SaveSchedulingDataDTO {

    @NotBlank(message = "clientId cannot be empty")
    private final String clientId;

    @NotBlank(message = "barberId cannot be empty")
    private final String barberId;

    @NotBlank(message = "serviceId cannot be empty")
    private final String serviceId;

    @NotBlank(message = "dateTime cannot be empty")
    private final String dateTime; // ISO-8601 string sent from frontend

    private final String status; // AGENDADO, CANCELADO, FINALIZADO
}
