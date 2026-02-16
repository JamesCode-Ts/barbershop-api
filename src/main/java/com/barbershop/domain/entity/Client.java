package com.barbershop.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "client")
@DiscriminatorValue("CLIENT")
@Getter
@Setter
@NoArgsConstructor
public class Client extends User {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "barbershop_id", nullable = false)
    private Barbershop barbershop;
}

