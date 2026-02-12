package com.barbershop.infrastructure.controller;

import com.barbershop.domain.applicationservice.AdminService;
import com.barbershop.domain.entity.Admin;
import com.barbershop.infrastructure.dto.admin.AdminDTO;
import com.barbershop.infrastructure.dto.admin.SaveAdminDataDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PreAuthorize("hasRole('BARBERSHOP_ADMIN')")
    @PostMapping
    public ResponseEntity<AdminDTO> createAdmin(@RequestBody SaveAdminDataDTO dto) {
        return ResponseEntity.ok(AdminDTO.create(adminService.createAdmin(dto)));
    }

    @PreAuthorize("hasRole('BARBERSHOP_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<AdminDTO> updateAdmin(
            @PathVariable String id,
            @RequestBody SaveAdminDataDTO dto) {
        return ResponseEntity.ok(AdminDTO.create(adminService.updateAdmin(id, dto)));
    }

    @PreAuthorize("hasRole('BARBERSHOP_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<AdminDTO> getAdminById(@PathVariable String id) {
        return ResponseEntity.ok(AdminDTO.create(adminService.getById(id)));
    }

    @PreAuthorize("hasRole('BARBERSHOP_ADMIN')")
    @GetMapping
    public ResponseEntity<List<AdminDTO>> getAllAdmins() {
        return ResponseEntity.ok(
                adminService.getAll()
                        .stream()
                        .map(AdminDTO::create)
                        .toList()
        );
    }

    @PreAuthorize("hasRole('BARBERSHOP_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable String id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }
}
