package com.barbershop.domain.repository;

import com.barbershop.domain.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, String> {

    boolean existsByEmail(String email);
}
