package com.barbershop.infrastructure.controller.auth;

import com.barbershop.infrastructure.dto.auth.AuthRequest;
import com.barbershop.infrastructure.dto.auth.RegisterRequest;
import com.barbershop.infrastructure.dto.auth.AuthResponseDTO;
import com.barbershop.domain.applicationservice.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequest req) {
        System.out.println("REQ CHEGOU: " + req);
        return ResponseEntity.ok(authService.register(req));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }
}
