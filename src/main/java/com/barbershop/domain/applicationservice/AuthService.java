package com.barbershop.domain.applicationservice;

import com.barbershop.domain.entity.Client;
import com.barbershop.domain.entity.User;
import com.barbershop.domain.entity.model.Role;
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
    private final JwtUtil jwtUtil;

    public AuthResponseDTO register(RegisterRequest req) {

        if (userRepository.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }

        Client client = new Client();
        client.setName(req.getName());
        client.setEmail(req.getEmail());
        client.setPassword(passwordEncoder.encode(req.getPassword()));
        client.setRole(Role.CLIENT);

        clientRepository.save(client);

        String token = generateToken(client);

        return new AuthResponseDTO(
                client.getId(),
                client.getName(),
                client.getEmail(),
                client.getRole().name(),
                null,                 // sem barbershop no cadastro
                token
        );
    }

    public AuthResponseDTO login(AuthRequest req) {

        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Senha inválida");
        }

        String token = generateToken(user);

        String barbershopId = null;
        if (user instanceof com.barbershop.domain.entity.Barber barber &&
                barber.getBarbershop() != null) {
            barbershopId = barber.getBarbershop().getId();
        }

        return new AuthResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name(),
                barbershopId,
                token
        );
    }

    private String generateToken(User user) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().name());
        return jwtUtil.generateToken(claims, user.getEmail());
    }
}
