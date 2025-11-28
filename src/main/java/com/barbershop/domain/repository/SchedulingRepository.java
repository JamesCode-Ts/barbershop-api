package com.barbershop.domain.repository;

import com.barbershop.domain.entity.Scheduling;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchedulingRepository extends JpaRepository<Scheduling, String> {

    List<Scheduling> findByBarber_Id(String barberId);
}
