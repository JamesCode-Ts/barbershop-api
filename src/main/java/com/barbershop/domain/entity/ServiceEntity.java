package com.barbershop.domain.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "service")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ServiceEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(columnDefinition = "text")
    private String description;
    private java.math.BigDecimal price;
    private Integer durationMinutes;
}

