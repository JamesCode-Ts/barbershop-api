package com.barbershop.infrastructure.dto.barbershop;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class SaveBarbershopDataDTO {

    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be empty")
    @Size(min = 1, max = 200, message = "Invalid name")
    private final String name;

    @NotNull(message = "Address cannot be null")
    @NotBlank(message = "Address cannot be empty")
    @Size(min = 1, max = 255, message = "Invalid address")
    private final String address;

    @NotNull(message = "Phone cannot be null")
    @NotBlank(message = "Phone cannot be empty")
    @Size(min = 8, max = 20, message = "Invalid phone number")
    private final String phone;

    // Barbers linked to this barbershop (IDs only)
    private final Set<Long> barbersIds;

    // Additional validation rule (same style as SaveProjectDataDTO)
    @AssertTrue(message = "Phone must contain only numbers, spaces, parentheses, + or -")
    @SuppressWarnings("unused")
    private boolean isPhoneValid() {
        return phone.matches("[0-9 +\\-()]+");
    }
}
