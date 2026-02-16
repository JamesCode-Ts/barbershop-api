package com.barbershop.infrastructure.dto.tenant;

import com.barbershop.infrastructure.dto.admin.SaveAdminDataDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SaveTenantDTO {

    @Valid
    private SaveAdminDataDTO admin;

    @NotBlank
    private String barbershopName;

    private String address;
    private String phone;
}
