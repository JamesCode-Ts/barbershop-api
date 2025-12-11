package com.barbershop.infrastructure.security.config;


import com.barbershop.domain.entity.User;
import com.barbershop.domain.entity.model.Role;
import com.barbershop.domain.repository.UserRepository;
import com.barbershop.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public void run(String... args) throws Exception {

        String masterEmail = "master@system.com";

        if (!userRepository.existsByEmail(masterEmail)) {

            User master = new User();
            master.setName("Master Admin");
            master.setEmail(masterEmail);
            master.setPassword(passwordEncoder.encode("123456"));
            master.setRole(Role.MASTER_ADMIN);

            userRepository.save(master);

            String token = jwtUtil.generateToken(
                    java.util.Map.of("role", master.getRole().name()),
                    master.getEmail()
            );

            System.out.println("\n============================================");
            System.out.println(" SUPERUSER CREATED:");
            System.out.println(" Email: " + masterEmail);
            System.out.println(" Password: 123456");
            System.out.println(" Role: MASTER_ADMIN");
            System.out.println(" TOKEN: " + token);
            System.out.println("============================================\n");
        }
    }
}