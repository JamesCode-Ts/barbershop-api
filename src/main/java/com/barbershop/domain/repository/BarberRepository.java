package com.barbershop.domain.repository;

import com.barbershop.domain.entity.Barber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BarberRepository extends JpaRepository<Barber, String> {

    List<Barber> findByBarbershop_Id(String barbershopId);

    boolean existsByEmail(String email); // ← ADICIONE ISTO
}

