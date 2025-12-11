package com.barbershop.domain.applicationservice;

import com.barbershop.domain.entity.Barber;
import com.barbershop.domain.entity.Barbershop;
import com.barbershop.domain.entity.User;
import com.barbershop.domain.entity.model.Role;
import com.barbershop.domain.exception.barber.BarberNotFoundException;
import com.barbershop.domain.exception.barbershop.BarbershopNotFoundException;
import com.barbershop.domain.repository.BarberRepository;
import com.barbershop.domain.repository.BarbershopRepository;
import com.barbershop.domain.repository.UserRepository;
import com.barbershop.infrastructure.dto.barber.SaveBarberDataDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class BarberService {

    private final BarberRepository barberRepository;
    private final BarbershopRepository barbershopRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Cria um barbeiro (User + Barber)
     */
    public Barber createBarber(SaveBarberDataDTO dto) {

        if (barberRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }

        Barbershop shop = barbershopRepository.findById(dto.getBarbershopId())
                .orElseThrow(() -> new BarbershopNotFoundException(dto.getBarbershopId()));

        Barber barber = new Barber();
        barber.setName(dto.getName());
        barber.setEmail(dto.getEmail());
        barber.setPassword(passwordEncoder.encode(dto.getPassword()));
        barber.setRole(Role.BARBER);
        barber.setBarbershop(shop);

        return barberRepository.save(barber);
    }

    @Transactional
    public Barber updateBarber(String id, SaveBarberDataDTO dto) {

        Barber barber = barberRepository.findById(id)
                .orElseThrow(() -> new BarberNotFoundException(id));

        barber.setName(dto.getName());
        barber.setEmail(dto.getEmail());

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            barber.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        if (dto.getBarbershopId() != null) {
            Barbershop shop = barbershopRepository.findById(dto.getBarbershopId())
                    .orElseThrow(() -> new BarbershopNotFoundException(dto.getBarbershopId()));
            barber.setBarbershop(shop);
        }

        return barber;
    }

    public void deleteBarber(String id) {
        Barber barber = barberRepository.findById(id)
                .orElseThrow(() -> new BarberNotFoundException(id));
        barberRepository.delete(barber);
    }
}
