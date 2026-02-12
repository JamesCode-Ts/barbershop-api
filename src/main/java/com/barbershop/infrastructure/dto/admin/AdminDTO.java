package com.barbershop.infrastructure.dto.admin;

import com.barbershop.domain.entity.Admin;
import lombok.Data;

/**
 * Data output DTO for Admin (response-only).
 * Mirrors the style of BarberDTO.
 */
@Data
public class AdminDTO {

    private final String id;
    private final String name;
    private final String email;
    private final String role;

    public static AdminDTO create(Admin admin) {
        return new AdminDTO(
                admin.getId(),
                admin.getName(),
                admin.getEmail(),
                admin.getRole().name()
        );
    }
}
