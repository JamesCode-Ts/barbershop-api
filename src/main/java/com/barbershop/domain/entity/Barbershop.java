package com.barbershop.domain.entity;

import com.barbershop.domain.entity.model.SubscriptionStatus;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Table(name = "barbershop", indexes = {
        @Index(name = "idx_barbershop_slug", columnList = "slug", unique = true)
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Barbershop {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    private String address;
    private String phone;

    @Column(nullable = false, length = 100, unique = true)
    private String slug;

    // 🔐 Dono do tenant
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false, unique = true)
    private User owner;

    // 👥 Equipe
    @OneToMany(mappedBy = "barbershop")
    private Set<Barber> barbers;

    // 💳 Assinatura
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus subscriptionStatus;

    private java.time.LocalDate trialEndsAt;

    private java.time.LocalDateTime createdAt;
}
