package com.barbershop.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Table(name = "barbershop", indexes = {
        @Index(name = "idx_barbershop_slug", columnList = "slug", unique = true)
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Barbershop {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    private String address;
    private String phone;

    @Column(nullable = false, length = 100, unique = true)
    private String slug; // novo campo

    @OneToMany(mappedBy = "barbershop")
    private Set<Barber> barbers;
}
