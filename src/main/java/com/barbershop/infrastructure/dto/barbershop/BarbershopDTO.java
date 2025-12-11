package com.barbershop.infrastructure.dto.barbershop;

import com.barbershop.domain.entity.Barber;
import com.barbershop.domain.entity.Barbershop;
import lombok.Data;

import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * Data output DTO (response-only).
 */
@Data
public class BarbershopDTO {

    private final String id;
    private final String name;
    private final String address;
    private final String phone;
    private final String slug;        // <-- ADICIONADO
    private final Set<String> barbersIds;

    public static BarbershopDTO create(Barbershop barbershop) {
        return new BarbershopDTO(
                barbershop.getId(),
                barbershop.getName(),
                barbershop.getAddress(),
                barbershop.getPhone(),
                barbershop.getSlug(),   // <-- ADICIONADO
                Optional
                        .ofNullable(barbershop.getBarbers())
                        .orElse(Set.of())
                        .stream()
                        .map(Barber::getId)
                        .collect(toSet())
        );
    }
}
