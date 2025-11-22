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

    @NotNull(message = "dateTime cannot be null")
    private final String dateTime; // enviado pelo front como ISO-8601 string

    private final String status; // opcional: AGENDADO, CANCELADO, FINALIZADO
}
