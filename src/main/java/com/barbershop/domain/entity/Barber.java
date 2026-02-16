package com.barbershop.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "barber")
@DiscriminatorValue("BARBER")
@Getter
@Setter
@NoArgsConstructor
public class Barber extends User {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "barbershop_id", nullable = false)
    private Barbershop barbershop;
}



