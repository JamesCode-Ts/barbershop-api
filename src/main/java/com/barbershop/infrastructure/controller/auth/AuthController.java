package com.barbershop.infrastructure.auth;


import com.barbershop.domain.entity.Client;
import com.barbershop.domain.entity.User;
import com.barbershop.domain.entity.model.Role;
import com.barbershop.domain.repository.UserRepository;
import com.barbershop.infrastructure.dto.AuthRequest;
import com.barbershop.infrastructure.dto.RegisterRequest;
import com.barbershop.domain.repository.*;
import com.barbershop.infrastructure.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;

/**
 * REST controller responsible for user authentication operations such as
 * login and registration. Generates and returns JWT tokens for authenticated
 * users, allowing them to access protected endpoints.
 */

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepo;
    private final ClientRepository clientRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepo, ClientRepository clientRepo, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.clientRepo = clientRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        if (userRepo.existsByEmail(req.getEmail())) {
            return ResponseEntity.badRequest().body("Email já cadastrado");
        }
        User u = new User();
        u.setName(req.getName());
        u.setEmail(req.getEmail());
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        u.setRole(Role.CLIENT);
        userRepo.save(u);

        Client c = new Client();
        c.setId(u.getId());
        clientRepo.save(c);

        HashMap<String,Object> claims = new HashMap<>();
        claims.put("role", u.getRole().name());
        String token = jwtUtil.generateToken(claims, u.getEmail());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest req) {
        var opt = userRepo.findByEmail(req.getEmail());
        if (opt.isEmpty()) return ResponseEntity.status(401).body("Usuário não encontrado");
        User u = opt.get();
        if (!passwordEncoder.matches(req.getPassword(), u.getPassword())) return ResponseEntity.status(401).body("Senha inválida");
        HashMap<String,Object> claims = new HashMap<>();
        claims.put("role", u.getRole().name());
        String token = jwtUtil.generateToken(claims, u.getEmail());
        return ResponseEntity.ok(token);
    }
}
