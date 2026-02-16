package com.barbershop.infrastructure.controller;

import com.barbershop.domain.applicationservice.TenantOnboardingService;
import com.barbershop.infrastructure.dto.tenant.SaveTenantDTO;
import com.barbershop.infrastructure.dto.tenant.TenantSignupDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class PublicSignupController {

    private final TenantOnboardingService onboardingService;

    @PostMapping("/signup")
    public ResponseEntity<TenantSignupDTO> signup(@RequestBody SaveTenantDTO dto) {
        return ResponseEntity.ok(onboardingService.signup(dto));
    }
}
