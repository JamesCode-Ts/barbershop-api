package com.barbershop.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "barber")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Barber extends User {

    @ManyToOne
    @JoinColumn(name = "barbershop_id")
    private Barbershop barbershop;

}

