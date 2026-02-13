package com.barbershop.domain.repository;

import com.barbershop.domain.entity.Barber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BarberRepository extends JpaRepository<Barber, String> {

    List<Barber> findByBarbershop_Id(String barbershopId);

    boolean existsByEmail(String email); // ← ADICIONE ISTO


    @Query("""
           SELECT b FROM Barber b
           JOIN FETCH b.barbershop
           WHERE b.email = :email
           """)
    Optional<Barber> findByEmail(@Param("email") String email);

}

