package com.barbershop.domain.entity;


import com.barbershop.domain.entity.model.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype") // 🔥 ESSENCIAL
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name ="id", nullable = false, length = 36)
    private String id;

    @Column(nullable=false, length=150)
    private String name;

    @Column(nullable=false, unique=true, length=150)
    private String email;

    @Column(nullable=false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(length=20, nullable=false)
    private Role role;
}
