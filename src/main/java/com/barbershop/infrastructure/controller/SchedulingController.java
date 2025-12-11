package com.barbershop.infrastructure.controller;

import com.barbershop.domain.applicationservice.SchedulingService;
import com.barbershop.infrastructure.dto.scheduling.SaveSchedulingDataDTO;
import com.barbershop.infrastructure.dto.scheduling.SchedulingDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedulings")
@RequiredArgsConstructor
public class SchedulingController {

    private final SchedulingService schedulingService;

    // CREATE
    @PostMapping
    public ResponseEntity<SchedulingDTO> create(@Valid @RequestBody SaveSchedulingDataDTO dto) {
        return ResponseEntity.ok(
                SchedulingDTO.create(schedulingService.createScheduling(dto))
        );
    }

    // FIND BY ID
    @GetMapping("/{id}")
    public ResponseEntity<SchedulingDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok(
                SchedulingDTO.create(schedulingService.loadSchedulingById(id))
        );
    }

    // LIST ALL OR BY BARBER
    @GetMapping
    public ResponseEntity<List<SchedulingDTO>> find(
            @RequestParam(value = "barberId", required = false) String barberId
    ) {
        return ResponseEntity.ok(
                schedulingService.findScheduling(barberId)
                        .stream()
                        .map(SchedulingDTO::create)
                        .toList()
        );
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<SchedulingDTO> update(
            @PathVariable String id,
            @Valid @RequestBody SaveSchedulingDataDTO dto
    ) {
        return ResponseEntity.ok(
                SchedulingDTO.create(schedulingService.updateScheduling(id, dto))
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        schedulingService.deleteScheduling(id);
        return ResponseEntity.noContent().build();
    }
}
