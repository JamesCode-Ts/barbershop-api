package com.barbershop.infrastructure.dto.scheduling;

import com.barbershop.domain.entity.Scheduling;
import lombok.Data;

@Data
public class SchedulingDTO {

    private final String id;
    private final String dateTime;
    private final String status;

    private final String clientId;
    private final String clientName;

    private final String barberId;
    private final String barberName;

    private final String serviceId;
    private final String serviceName;

    public static SchedulingDTO create(Scheduling s) {
        return new SchedulingDTO(
                s.getId(),
                s.getDateTime().toString(),
                s.getStatus(),

                s.getClient().getId(),
                s.getClient().getName(),

                s.getBarber().getId(),
                s.getBarber().getName(),

                s.getService().getId(),
                s.getService().getName()
        );
    }
}
