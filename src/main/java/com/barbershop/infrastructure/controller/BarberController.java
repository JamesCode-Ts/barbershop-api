package com.barbershop.infrastructure.controller;

import com.barbershop.domain.applicationservice.BarberService;
import com.barbershop.domain.entity.Barber;
import com.barbershop.infrastructure.dto.barber.SaveBarberDataDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/barbers")
@RequiredArgsConstructor
public class BarberController {

    private final BarberService barberService;

    // Somente ADMIN ou MASTER
    @PreAuthorize("hasAnyRole('MASTER_ADMIN')")
    @PostMapping
    public ResponseEntity<Barber> createBarber(@RequestBody SaveBarberDataDTO dto) {
        return ResponseEntity.ok(barberService.createBarber(dto));
    }

    @PreAuthorize("hasAnyRole('MASTER_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Barber> updateBarber(
            @PathVariable String id,
            @RequestBody SaveBarberDataDTO dto) {
        return ResponseEntity.ok(barberService.updateBarber(id, dto));
    }

    @PreAuthorize("hasAnyRole('MASTER_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBarber(@PathVariable String id) {
        barberService.deleteBarber(id);
        return ResponseEntity.noContent().build();
    }
}

