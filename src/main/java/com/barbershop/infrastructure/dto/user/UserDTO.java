package com.barbershop.infrastructure.dto.user;

import com.barbershop.domain.entity.User;
import lombok.Data;

@Data
public class UserDTO {

    private final String id;
    private final String name;
    private final String email;
    private final String role;

    public static UserDTO create(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name()
        );
    }
}
