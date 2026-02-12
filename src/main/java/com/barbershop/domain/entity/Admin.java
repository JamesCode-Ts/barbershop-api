package com.barbershop.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "admin")
@Getter
@Setter
@NoArgsConstructor
public class Admin extends User {

    public Admin(User user) {
        super(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );
    }
}
