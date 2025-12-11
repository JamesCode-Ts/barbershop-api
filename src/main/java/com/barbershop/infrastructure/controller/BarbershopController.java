package com.barbershop.infrastructure.controller;

import com.barbershop.domain.applicationservice.BarbershopService;
import com.barbershop.infrastructure.dto.barbershop.BarbershopDTO;
import com.barbershop.infrastructure.dto.barbershop.SaveBarbershopDataDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller responsible for exposing CRUD operations for Barbershop.
 * It follows the same structure and design used in PManager's ProjectController.
 */

@RestController
@RequestMapping("/barbershops")
@RequiredArgsConstructor
public class BarbershopController {

    private final BarbershopService service;
    private final BarbershopService barbershopService;


    // ============================================================
    // CREATE
    // ============================================================
    @PostMapping
    public ResponseEntity<BarbershopDTO> create(@Valid @RequestBody SaveBarbershopDataDTO dto) {
        var shop = service.createBarbershop(dto);
        return ResponseEntity.ok(BarbershopDTO.create(shop));
    }

    // ============================================================
    // GET BY ID
    // ============================================================
    @GetMapping("/{slug}")
    public ResponseEntity<BarbershopDTO> getBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(
                BarbershopDTO.create(barbershopService.loadBySlug(slug))
        );
    }

    // ============================================================
    // LIST / SEARCH
    // ============================================================
    @GetMapping
    public ResponseEntity<List<BarbershopDTO>> find(
            @RequestParam(required = false) String name
    ) {
        var shops = service.findBarbershops(name)
                .stream()
                .map(BarbershopDTO::create)
                .toList();

        return ResponseEntity.ok(shops);
    }

    // ============================================================
    // UPDATE
    // ============================================================
    @PutMapping("/{id}")
    public ResponseEntity<BarbershopDTO> update(
            @PathVariable String id,
            @Valid @RequestBody SaveBarbershopDataDTO dto
    ) {
        var shop = service.updateBarbershop(id, dto);
        return ResponseEntity.ok(BarbershopDTO.create(shop));
    }

    // ============================================================
    // DELETE
    // ============================================================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.deleteBarbershop(id);
        return ResponseEntity.noContent().build();
    }
}
