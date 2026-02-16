package com.barbershop.infrastructure.dto.tenant;

import com.barbershop.domain.entity.Barbershop;
import com.barbershop.domain.entity.Admin;
import lombok.Data;

@Data
public class TenantSignupDTO {

    private final String barbershopId;
    private final String barbershopName;
    private final String slug;

    private final String adminId;
    private final String adminName;
    private final String adminEmail;

    public static TenantSignupDTO create(Barbershop shop, Admin admin) {
        return new TenantSignupDTO(
                shop.getId(),
                shop.getName(),
                shop.getSlug(),
                admin.getId(),
                admin.getName(),
                admin.getEmail()
        );
    }
}
