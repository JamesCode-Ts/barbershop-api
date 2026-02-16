package com.barbershop.domain.applicationservice;

import com.barbershop.domain.entity.Barbershop;
import com.barbershop.domain.entity.User;
import com.barbershop.domain.entity.model.SubscriptionStatus;
import com.barbershop.domain.exception.barbershop.BarbershopNotFoundException;
import com.barbershop.domain.exception.barbershop.DuplicateBarbershopException;
import com.barbershop.domain.repository.BarbershopRepository;
import com.barbershop.infrastructure.dto.barbershop.SaveBarbershopDataDTO;
import com.barbershop.infrastructure.dto.tenant.SaveTenantDTO;
import com.barbershop.infrastructure.util.SlugUtil;
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

        // ============================================
        // SLUG AUTO-GERADO
        // ============================================
        String baseSlug = SlugUtil.toSlug(saveData.getName());
        String finalSlug = baseSlug;

        while (barbershopRepository.existsBySlug(finalSlug)) {
            finalSlug = baseSlug + SlugUtil.uniqueSuffix();
        }

        barbershop.setSlug(finalSlug);
        // ============================================

        return barbershopRepository.save(barbershop);
    }
    public Barbershop loadBySlug(String slug) {
        return barbershopRepository.findBySlug(slug)
                .orElseThrow(() -> new BarbershopNotFoundException("Slug: " + slug));
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

        // Regerar slug apenas se o nome mudou
        String newSlug = SlugUtil.toSlug(saveData.getName());
        String finalSlug = newSlug;

        while (barbershopRepository.existsBySlug(finalSlug)) {
            finalSlug = newSlug + SlugUtil.uniqueSuffix();
        }

        barbershop.setSlug(finalSlug);

        return barbershop; // JPA atualiza automaticamente
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
    @Transactional
    public Barbershop createTenant(SaveTenantDTO dto, User owner) {

        if (barbershopRepository.existsBySlug(SlugUtil.toSlug(dto.getBarbershopName())))
            throw new DuplicateBarbershopException(dto.getBarbershopName());

        Barbershop shop = new Barbershop();
        shop.setName(dto.getBarbershopName());
        shop.setAddress(dto.getAddress());
        shop.setPhone(dto.getPhone());
        shop.setOwner(owner);

        // slug
        String baseSlug = SlugUtil.toSlug(dto.getBarbershopName());
        String finalSlug = baseSlug;

        while (barbershopRepository.existsBySlug(finalSlug)) {
            finalSlug = baseSlug + SlugUtil.uniqueSuffix();
        }

        shop.setSlug(finalSlug);

        // assinatura inicial
        shop.setSubscriptionStatus(SubscriptionStatus.TRIAL);
        shop.setTrialEndsAt(java.time.LocalDate.now().plusDays(7));
        shop.setCreatedAt(java.time.LocalDateTime.now());

        return barbershopRepository.save(shop);
    }

}
