package com.barbershop.domain.repository;


import com.barbershop.domain.entity.Services;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServicesRepository extends JpaRepository<Services, String> {

    Optional<Services> findByName(String name);

    boolean existsByName(String name);
}
