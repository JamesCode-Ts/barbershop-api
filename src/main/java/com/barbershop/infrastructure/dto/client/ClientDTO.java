package com.barbershop.infrastructure.dto.client;

import com.barbershop.domain.entity.Client;
import lombok.Data;

@Data
public class ClientDTO {

    private final String id;
    private final String name;
    private final String email;
    private final String role;

    public static ClientDTO create(Client client) {
        return new ClientDTO(
                client.getId(),
                client.getName(),
                client.getEmail(),
                client.getRole().name()
        );
    }
}
