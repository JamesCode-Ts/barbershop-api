package com.barbershop.infrastructure.dto.barber;

import com.barbershop.domain.entity.Barber;
import lombok.Data;

import java.util.Optional;

/**
 * Data output DTO for Barber (response-only).
 * Mirrors the style of ProjectDTO and BarbershopDTO.
 */
@Data
public class BarberDTO {

    private final String id;
    private final String name;
    private final String email;
    private final String role;

    // barbershop ID (String because ID uses UUID)
    private final String barbershopId;

    public static BarberDTO create(Barber barber) {
        return new BarberDTO(
                barber.getId(),
                barber.getName(),
                barber.getEmail(),
                barber.getRole().name(),

                // avoid NPE if barber has no barbershop assigned
                Optional.ofNullable(barber.getBarbershop())
                        .map(b -> b.getId())
                        .orElse(null)
        );
    }
}
