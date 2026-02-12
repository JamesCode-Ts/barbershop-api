package com.barbershop.domain.applicationservice;

import com.barbershop.domain.entity.Barbershop;
import com.barbershop.domain.entity.Client;
import com.barbershop.domain.entity.User;
import com.barbershop.domain.entity.Barber;
import com.barbershop.domain.entity.model.Role;
import com.barbershop.domain.repository.BarbershopRepository;
import com.barbershop.domain.repository.ClientRepository;
import com.barbershop.domain.repository.UserRepository;
import com.barbershop.infrastructure.dto.auth.AuthRequest;
import com.barbershop.infrastructure.dto.auth.RegisterRequest;
import com.barbershop.infrastructure.dto.auth.AuthResponseDTO;
import com.barbershop.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final BarbershopRepository barbershopRepository;

    private final JwtUtil jwtUtil;

    // =========================
    // REGISTER
    // =========================
    public AuthResponseDTO register(RegisterRequest req) {

        if (userRepository.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }

        // 🔥 BUSCA BARBEARIA PELO SLUG
        Barbershop barbershop = barbershopRepository
                .findBySlug(req.getSlug())
                .orElseThrow(() -> new RuntimeException("Barbearia não encontrada"));

        Client client = new Client();
        client.setName(req.getName());
        client.setEmail(req.getEmail());
        client.setPassword(passwordEncoder.encode(req.getPassword()));
        client.setRole(Role.CLIENT);
        client.setBarbershop(barbershop); // ✅ VÍNCULO REAL

        clientRepository.save(client);

        String token = generateToken(client);

        return new AuthResponseDTO(
                client.getId(),
                client.getName(),
                client.getEmail(),
                client.getRole().name(),
                barbershop.getId(),
                barbershop.getName(),
                barbershop.getSlug(),
                token
        );
    }


    // =========================
    // LOGIN
    // =========================
    public AuthResponseDTO login(AuthRequest req) {

        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Senha inválida");
        }

        String token = generateToken(user);

        String barbershopId = null;
        String barbershopName = null;
        String barbershopSlug = null;

        // 🔥 BARBER
        if (user instanceof Barber barber && barber.getBarbershop() != null) {
            barbershopId = barber.getBarbershop().getId();
            barbershopName = barber.getBarbershop().getName();
            barbershopSlug = barber.getBarbershop().getSlug();
        }

        // 🔥 CLIENT (somente se existir relacionamento)
        if (user instanceof Client client && client.getBarbershop() != null) {
            barbershopId = client.getBarbershop().getId();
            barbershopName = client.getBarbershop().getName();
            barbershopSlug = client.getBarbershop().getSlug();
        }

        return new AuthResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name(),
                barbershopId,
                barbershopName,
                barbershopSlug,
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
