package com.barbershop.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "barbershop")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Barbershop {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false, length = 36)
    private String id;

    private String name;
    private String address;
    private String phone;

    @OneToMany(mappedBy = "barbershop")
    private Set<Barber> barbers = new HashSet<>();
}
