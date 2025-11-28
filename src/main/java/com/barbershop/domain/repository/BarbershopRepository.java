package com.barbershop.domain.repository;

import com.barbershop.domain.entity.Barbershop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BarbershopRepository extends JpaRepository<Barbershop, String> {

    Optional<Barbershop> findByName(String name);

    boolean existsByName(String name);
}
