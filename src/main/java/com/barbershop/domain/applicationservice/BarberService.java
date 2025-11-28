package com.barbershop.domain.applicationservice;

import com.barbershop.domain.entity.Barber;
import com.barbershop.domain.entity.Barbershop;
import com.barbershop.domain.entity.model.Role;
import com.barbershop.domain.exception.barber.BarberNotFoundException;
import com.barbershop.domain.exception.barbershop.BarbershopNotFoundException;
import com.barbershop.domain.repository.BarberRepository;
import com.barbershop.domain.repository.BarbershopRepository;
import com.barbershop.infrastructure.dto.barber.SaveBarberDataDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BarberService {

    private final BarberRepository barberRepository;
    private final BarbershopRepository barbershopRepository;

    /**
     * Creates a Barber inside a Barbershop.
     */
    public Barber createBarber(SaveBarberDataDTO dto) {

        Barbershop barbershop = barbershopRepository
                .findById(dto.getBarbershopId())
                .orElseThrow(() -> new BarbershopNotFoundException(dto.getBarbershopId()));

        Barber barber = new Barber();
        barber.setName(dto.getName());
        barber.setEmail(dto.getEmail());
        barber.setPassword(dto.getPassword()); // opcional codificar, depende da arquitetura
        barber.setRole(Role.BARBER); // ← FIXO NO SERVICE
        barber.setBarbershop(barbershop);

        return barberRepository.save(barber);
    }

    /**
     * Load Barber by ID
     */
    public Barber loadBarberById(String id) {
        return barberRepository.findById(id)
                .orElseThrow(() -> new BarberNotFoundException(id));
    }

    /**
     * Update barber
     */
    @Transactional
    public Barber updateBarber(String id, SaveBarberDataDTO dto) {

        Barber barber = loadBarberById(id);

        barber.setName(dto.getName());
        barber.setEmail(dto.getEmail());
        barber.setPassword(dto.getPassword());
        barber.setRole(Role.BARBER); // ← FIXO NO SERVICE


        if (dto.getBarbershopId() != null) {
            Barbershop shop = barbershopRepository.findById(dto.getBarbershopId())
                    .orElseThrow(() -> new BarbershopNotFoundException(dto.getBarbershopId()));
            barber.setBarbershop(shop);
        }

        return barber;
    }

    /**
     * Delete Barber
     */
    public void deleteBarber(String id) {
        Barber barber = loadBarberById(id);
        barberRepository.delete(barber);
    }

    /**
     * Find all or filter by barbershop
     */
    public List<Barber> findBarbers(String barbershopId) {
        if (barbershopId == null) {
            return barberRepository.findAll();
        }

        return barberRepository.findByBarbershop_Id(barbershopId);
    }
}
