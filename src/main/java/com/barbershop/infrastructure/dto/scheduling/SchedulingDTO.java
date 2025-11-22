package com.barbershop.infrastructure.dto.scheduling;

import com.barbershop.domain.entity.Scheduling;
import lombok.Data;

import java.util.Optional;

@Data
public class SchedulingDTO {

    private final String id;
    private final String clientId;
    private final String barberId;
    private final String serviceId;
    private final String status;
    private final String dateTime;

    public static SchedulingDTO create(Scheduling scheduling) {
        return new SchedulingDTO(
                scheduling.getId(),
                Optional.ofNullable(scheduling.getClient())
                        .map(c -> c.getId())
                        .orElse(null),
                Optional.ofNullable(scheduling.getBarber())
                        .map(b -> b.getId())
                        .orElse(null),
                Optional.ofNullable(scheduling.getService())
                        .map(s -> String.valueOf(s.getId()))   // 👈 CONVERSÃO IMPORTANTE
                        .orElse(null),
                scheduling.getStatus(),
                scheduling.getDateTime().toString()
        );
    }
}
