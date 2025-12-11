package com.barbershop.domain.applicationservice;

import com.barbershop.domain.entity.Barbershop;
import com.barbershop.domain.entity.Services;
import com.barbershop.domain.exception.service.DuplicateServicesException;
import com.barbershop.domain.exception.service.ServicesNotFoundException;
import com.barbershop.domain.repository.BarbershopRepository;
import com.barbershop.domain.repository.ServicesRepository;
import com.barbershop.infrastructure.dto.services.SaveServicesDataDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ServicesService {

    private final ServicesRepository servicesRepository;
    private final BarbershopRepository barbershopRepository;

    /**
     * Create new service
     */
    public Services createService(SaveServicesDataDTO dto) {

        if (existsServiceWithName(dto.getName(), null)) {
            throw new DuplicateServicesException(dto.getName());
        }

        Barbershop barbershop = barbershopRepository.findById(dto.getBarbershopId())
                .orElseThrow(() -> new RuntimeException("Barbershop not found."));

        Services s = new Services();
        s.setName(dto.getName());
        s.setDescription(dto.getDescription());
        s.setPrice(dto.getPrice());
        s.setDurationMinutes(dto.getDurationMinutes());
        s.setBarbershop(barbershop);

        return servicesRepository.save(s);
    }

    /**
     * Load service by ID
     */
    public Services loadServiceById(String id) {
        return servicesRepository.findById(id)
                .orElseThrow(() -> new ServicesNotFoundException(id));
    }

    /**
     * Update service
     */
    @Transactional
    public Services updateService(String id, SaveServicesDataDTO dto) {

        if (existsServiceWithName(dto.getName(), id)) {
            throw new DuplicateServicesException(dto.getName());
        }

        Services s = loadServiceById(id);

        Barbershop barbershop = barbershopRepository.findById(dto.getBarbershopId())
                .orElseThrow(() -> new RuntimeException("Barbershop not found."));

        s.setName(dto.getName());
        s.setDescription(dto.getDescription());
        s.setPrice(dto.getPrice());
        s.setDurationMinutes(dto.getDurationMinutes());
        s.setBarbershop(barbershop);

        return s;
    }

    /**
     * Delete service
     */
    public void deleteService(String id) {
        Services s = loadServiceById(id);
        servicesRepository.delete(s);
    }

    /**
     * Find all or by name
     */
    public List<Services> findServices(String name) {

        if (name == null) {
            return servicesRepository.findAll();
        }

        return servicesRepository.findByName(name)
                .map(List::of)
                .orElse(List.of());
    }

    /**
     * Check duplicated values
     */
    private boolean existsServiceWithName(String name, String idToExclude) {
        return servicesRepository.findByName(name)
                .filter(s -> !Objects.equals(s.getId(), idToExclude))
                .isPresent();
    }
}
