package com.barbershop.infrastructure.controller;

import com.barbershop.domain.applicationservice.ServicesService;
import com.barbershop.domain.entity.Services;
import com.barbershop.infrastructure.dto.services.SaveServicesDataDTO;
import com.barbershop.infrastructure.dto.services.ServicesDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
public class ServicesController {

    private final ServicesService servicesService;

    // CREATE
    @PostMapping
    public ResponseEntity<ServicesDTO> create(@Valid @RequestBody SaveServicesDataDTO dto) {
        Services s = servicesService.createService(dto);
        return ResponseEntity.ok(ServicesDTO.create(s));
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ServicesDTO> getById(@PathVariable String id) {
        Services s = servicesService.loadServiceById(id);
        return ResponseEntity.ok(ServicesDTO.create(s));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ServicesDTO> update(
            @PathVariable String id,
            @Valid @RequestBody SaveServicesDataDTO dto
    ) {
        Services s = servicesService.updateService(id, dto);
        return ResponseEntity.ok(ServicesDTO.create(s));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        servicesService.deleteService(id);
        return ResponseEntity.noContent().build();
    }

    // LIST (ALL or FILTER BY NAME)
    @GetMapping
    public ResponseEntity<List<ServicesDTO>> list(@RequestParam(required = false) String name) {
        List<ServicesDTO> result = servicesService
                .findServices(name)
                .stream()
                .map(ServicesDTO::create)
                .toList();

        return ResponseEntity.ok(result);
    }
}
