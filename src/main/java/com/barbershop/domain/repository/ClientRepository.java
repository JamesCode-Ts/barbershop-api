package com.barbershop.domain.repository;

import com.barbershop.domain.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, String> {

    @Query("""
           SELECT c FROM Client c
           JOIN FETCH c.barbershop
           WHERE c.email = :email
           """)
    Optional<Client> findByEmail(@Param("email") String email);
}
