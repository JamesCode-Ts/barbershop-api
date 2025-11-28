package com.barbershop.domain.entity;


import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "scheduling")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Scheduling {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name ="id", nullable = false, length = 36)
    private String id;

    @ManyToOne(optional=false)
    @JoinColumn(name="client_id")
    private Client client;

    @ManyToOne(optional=false)
    @JoinColumn(name="barber_id")
    private Barber barber;

    @ManyToOne(optional=false)
    @JoinColumn(name="service_id")
    private Services service;

    @Column(name="date_time", nullable=false)
    private OffsetDateTime dateTime;

    @Column(length = 30)
    private String status; // AGENDADO, CANCELADO, FINALIZADO
}