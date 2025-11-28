package com.barbershop.domain.applicationservice;

import com.barbershop.domain.entity.Barbershop;
import com.barbershop.domain.repository.BarbershopRepository;
import com.barbershop.infrastructure.dto.barbershop.SaveBarbershopDataDTO;
import com.barbershop.domain.exception.barbershop.DuplicateBarbershopException;
import com.barbershop.domain.exception.barbershop.BarbershopNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BarbershopService {

    private final BarbershopRepository barbershopRepository;

    // ============================================================
    // CREATE
    // ============================================================
    public Barbershop createBarbershop(SaveBarbershopDataDTO saveData) {

        if (existsBarbershopByName(saveData.getName(), null)) {
            throw new DuplicateBarbershopException(saveData.getName());
        }

        Barbershop barbershop = new Barbershop();
        barbershop.setName(saveData.getName());
        barbershop.setAddress(saveData.getAddress());
        barbershop.setPhone(saveData.getPhone());

        barbershopRepository.save(barbershop);

        return barbershop;
    }

    // ============================================================
    // LOAD BY ID
    // ============================================================
    public Barbershop loadBarbershopById(String id) {
        return barbershopRepository
                .findById(id)
                .orElseThrow(() -> new BarbershopNotFoundException(id));
    }

    // ============================================================
    // DELETE
    // ============================================================
    @Transactional
    public void deleteBarbershop(String id) {
        Barbershop barbershop = loadBarbershopById(id);
        barbershopRepository.delete(barbershop);
    }

    // ============================================================
    // UPDATE
    // ============================================================
    @Transactional
    public Barbershop updateBarbershop(String id, SaveBarbershopDataDTO saveData) {

        if (existsBarbershopByName(saveData.getName(), id)) {
            throw new DuplicateBarbershopException(saveData.getName());
        }

        Barbershop barbershop = loadBarbershopById(id);

        barbershop.setName(saveData.getName());
        barbershop.setAddress(saveData.getAddress());
        barbershop.setPhone(saveData.getPhone());

        return barbershop; // JPA autoupdate
    }

    // ============================================================
    // LIST / SEARCH
    // ============================================================
    public List<Barbershop> findBarbershops(String name) {

        if (Objects.isNull(name)) {
            return barbershopRepository.findAll();
        }

        return barbershopRepository
                .findByName(name)
                .map(List::of)
                .orElse(List.of());
    }

    // ============================================================
    // VALIDATION HELPERS
    // ============================================================
    private boolean existsBarbershopByName(String name, String idToExclude) {
        return barbershopRepository
                .findByName(name)
                .filter(b -> !Objects.equals(b.getId(), idToExclude))
                .isPresent();
    }
}
