package com.barbershop.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "services")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Services {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36)
    private String id;

    private String name;

    @Column(columnDefinition = "text")
    private String description;

    private BigDecimal price;

    private Integer durationMinutes;

    @ManyToOne(optional = false)
    @JoinColumn(name = "barbershop_id")
    private Barbershop barbershop;

}
