package com.barbershop.infrastructure.dto.barbershop;

import com.barbershop.domain.entity.Barber;
import com.barbershop.domain.entity.Barbershop;
import lombok.Data;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * Data output DTO (response-only).
 * Mirrors the style and structure of ProjectDTO from the PManager project.
 */
@Data
public class BarbershopDTO {

    private final String id;
    private final String name;
    private final String address;
    private final String phone;

    // IDs of barbers that belong to this barbershop (String, because ID is UUID)
    private final Set<String> barbersIds;

    public static BarbershopDTO create(Barbershop barbershop) {
        return new BarbershopDTO(
                barbershop.getId(),
                barbershop.getName(),
                barbershop.getAddress(),
                barbershop.getPhone(),
                Optional
                        .ofNullable(barbershop.getBarbers())   // avoid NPE
                        .orElse(Set.of())                    // fallback empty list
                        .stream()                             // Stream<Barber>
                        .map(Barber::getId)                   // String id
                        .collect(toSet())                     // Set<String>
        );
    }
}
