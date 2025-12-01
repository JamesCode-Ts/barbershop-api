package com.barbershop.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "barber")
@Getter
@Setter
@NoArgsConstructor
public class Barber extends User {

    @ManyToOne
    private Barbershop barbershop;

    public Barber(User user) {
        super(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );
    }
}


