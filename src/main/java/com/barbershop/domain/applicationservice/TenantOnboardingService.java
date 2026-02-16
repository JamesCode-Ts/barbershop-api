package com.barbershop.domain.applicationservice;

import com.barbershop.domain.entity.Admin;
import com.barbershop.domain.entity.Barbershop;
import com.barbershop.domain.entity.User;
import com.barbershop.domain.entity.model.Role;
import com.barbershop.domain.repository.UserRepository;
import com.barbershop.infrastructure.dto.admin.SaveAdminDataDTO;
import com.barbershop.infrastructure.dto.tenant.SaveTenantDTO;
import com.barbershop.infrastructure.dto.tenant.TenantSignupDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TenantOnboardingService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BarbershopService barbershopService;

    @Transactional
    public TenantSignupDTO signup(SaveTenantDTO dto) {

        SaveAdminDataDTO adminDTO = dto.getAdmin();

        if (userRepository.existsByEmail(adminDTO.getEmail()))
            throw new RuntimeException("Email já cadastrado");

        Admin admin = new Admin();
        admin.setName(adminDTO.getName());
        admin.setEmail(adminDTO.getEmail());
        admin.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
        admin.setRole(Role.BARBERSHOP_ADMIN);

        userRepository.save(admin);

        Barbershop shop = barbershopService.createTenant(dto, admin);

        return TenantSignupDTO.create(shop, admin);
    }

}