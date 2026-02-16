package com.barbershop.domain.applicationservice;

import com.barbershop.domain.entity.*;
import com.barbershop.domain.repository.BarberRepository;
import com.barbershop.domain.repository.BarbershopRepository;
import com.barbershop.domain.repository.ClientRepository;
import com.barbershop.domain.repository.UserRepository;
import com.barbershop.infrastructure.dto.auth.AuthRequest;
import com.barbershop.infrastructure.dto.auth.AuthResponseDTO;
import com.barbershop.infrastructure.dto.auth.RegisterRequest;
import com.barbershop.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final BarberRepository barberRepository;
    private final BarbershopRepository barbershopRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // =========================
    // REGISTER (CLIENT)
    // =========================
    public AuthResponseDTO register(RegisterRequest req) {

        if (userRepository.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }

        Barbershop barbershop = barbershopRepository
                .findBySlug(req.getSlug())
                .orElseThrow(() -> new RuntimeException("Barbearia não encontrada"));

        Client client = new Client();
        client.setName(req.getName());
        client.setEmail(req.getEmail());
        client.setPassword(passwordEncoder.encode(req.getPassword()));
        client.setRole(com.barbershop.domain.entity.model.Role.CLIENT);
        client.setBarbershop(barbershop);

        clientRepository.save(client);

        return buildResponse(client, barbershop);
    }

    // =========================
    // LOGIN (DEFINITIVO)
    // =========================
    @Transactional(readOnly = true)
    public AuthResponseDTO login(AuthRequest req) {

        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword()))
            throw new RuntimeException("Senha inválida");

        Barbershop shop = extractBarbershop(user);

        return buildResponse(user, shop);
    }

    private Barbershop extractBarbershop(User user) {

        if (user instanceof Client client)
            return client.getBarbershop();

        if (user instanceof Barber barber)
            return barber.getBarbershop();

        if (user instanceof Admin admin)
            return admin.getBarbershop();

        return null; // MASTER ou usuários sem tenant
    }

    // =========================
    // RESPONSE BUILDER
    // =========================
    private AuthResponseDTO buildResponse(User user, Barbershop shop) {

        System.out.println("=========== LOGIN DEBUG ===========");
        System.out.println("USER EMAIL: " + user.getEmail());
        System.out.println("ROLE: " + user.getRole());
        System.out.println("SHOP OBJ: " + shop);
        System.out.println("SHOP SLUG: " + (shop == null ? "NULL" : shop.getSlug()));
        System.out.println("===================================");

        String token = generateToken(user);

        return new AuthResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name(),
                shop != null ? shop.getId() : null,
                shop != null ? shop.getName() : null,
                shop != null ? shop.getSlug() : null,
                token
        );
    }


    // =========================
    // JWT
    // =========================
    private String generateToken(User user) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().name());
        return jwtUtil.generateToken(claims, user.getEmail());
    }
}
