package com.barbershop.domain.applicationservice;

import com.barbershop.domain.entity.Admin;
import com.barbershop.domain.entity.model.Role;
import com.barbershop.domain.exception.admin.AdminNotFoundException;
import com.barbershop.domain.repository.AdminRepository;
import com.barbershop.infrastructure.dto.admin.SaveAdminDataDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Cria um Admin (BARBERSHOP_ADMIN)
     */
    public Admin createAdmin(SaveAdminDataDTO dto) {

        if (adminRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }

        Admin admin = new Admin();
        admin.setName(dto.getName());
        admin.setEmail(dto.getEmail());
        admin.setPassword(passwordEncoder.encode(dto.getPassword()));
        admin.setRole(Role.BARBERSHOP_ADMIN);

        return adminRepository.save(admin);
    }

    @Transactional
    public Admin updateAdmin(String id, SaveAdminDataDTO dto) {

        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new AdminNotFoundException(id));

        admin.setName(dto.getName());
        admin.setEmail(dto.getEmail());

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            admin.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return admin;
    }

    public void deleteAdmin(String id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new AdminNotFoundException(id));
        adminRepository.delete(admin);
    }


    public Admin getById(String id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new AdminNotFoundException(id));
    }

    public List<Admin> getAll() {
        return adminRepository.findAll();
    }

}
