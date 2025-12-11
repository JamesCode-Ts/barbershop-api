package com.barbershop;

import com.barbershop.domain.entity.User;
import com.barbershop.domain.entity.model.Role;
import com.barbershop.domain.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BarbershopApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BarbershopApiApplication.class, args);
	}

    @Bean
    CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder encoder) {
        return args -> {

            if (!userRepository.existsByEmail("admin@barbershop.com")) {

                User admin = new User();
                admin.setName("Master Admin");
                admin.setEmail("admin@barbershop.com");
                admin.setPassword(encoder.encode("123456"));
                admin.setRole(Role.MASTER_ADMIN);

                userRepository.save(admin);
                System.out.println(">>> ADMIN criado!");
            }
        };
    }

}
