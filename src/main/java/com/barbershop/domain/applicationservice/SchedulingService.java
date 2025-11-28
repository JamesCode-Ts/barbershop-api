package com.barbershop.domain.applicationservice;

import com.barbershop.domain.entity.*;
import com.barbershop.domain.exception.barber.BarberNotFoundException;
import com.barbershop.domain.exception.client.ClientNotFoundException;
import com.barbershop.domain.exception.scheduling.SchedulingNotFoundException;
import com.barbershop.domain.exception.service.ServicesNotFoundException;
import com.barbershop.domain.repository.*;
import com.barbershop.infrastructure.dto.scheduling.SaveSchedulingDataDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.ServiceNotFoundException;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulingService {

    private final SchedulingRepository schedulingRepository;
    private final ClientRepository clientRepository;
    private final BarberRepository barberRepository;
    private final ServicesRepository serviceRepository;

    /**
     * Create a new scheduling
     */
    public Scheduling createScheduling(SaveSchedulingDataDTO dto) {

        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new ClientNotFoundException(dto.getClientId()));

        Barber barber = barberRepository.findById(dto.getBarberId())
                .orElseThrow(() -> new BarberNotFoundException(dto.getBarberId()));

        Services service = serviceRepository.findById(dto.getServiceId())
                .orElseThrow(() -> new ServicesNotFoundException(dto.getServiceId()));

        Scheduling scheduling = new Scheduling();
        scheduling.setClient(client);
        scheduling.setBarber(barber);
        scheduling.setService(service);
        scheduling.setDateTime(OffsetDateTime.parse(dto.getDateTime()));
        scheduling.setStatus(dto.getStatus());

        return schedulingRepository.save(scheduling);
    }

    /**
     * Load scheduling by ID
     */
    public Scheduling loadSchedulingById(String id) {
        return schedulingRepository.findById(id)
                .orElseThrow(() -> new SchedulingNotFoundException(id));
    }

    /**
     * Update scheduling
     */
    @Transactional
    public Scheduling updateScheduling(String id, SaveSchedulingDataDTO dto) {

        Scheduling scheduling = loadSchedulingById(id);

        // Update client if changed
        if (dto.getClientId() != null) {
            Client client = clientRepository.findById(dto.getClientId())
                    .orElseThrow(() -> new ClientNotFoundException(dto.getClientId()));
            scheduling.setClient(client);
        }

        // Update barber if changed
        if (dto.getBarberId() != null) {
            Barber barber = barberRepository.findById(dto.getBarberId())
                    .orElseThrow(() -> new BarberNotFoundException(dto.getBarberId()));
            scheduling.setBarber(barber);
        }

        // Update service if changed
        if (dto.getServiceId() != null) {
            Services service = serviceRepository.findById(dto.getServiceId())
                    .orElseThrow(() -> new ServicesNotFoundException(dto.getServiceId()));
            scheduling.setService(service);
        }

        scheduling.setDateTime(OffsetDateTime.parse(dto.getDateTime()));
        scheduling.setStatus(dto.getStatus());

        return scheduling;
    }

    /**
     * Delete scheduling
     */
    public void deleteScheduling(String id) {
        Scheduling scheduling = loadSchedulingById(id);
        schedulingRepository.delete(scheduling);
    }

    /**
     * Find all or filter by barber
     */
    public List<Scheduling> findScheduling(String barberId) {

        if (barberId == null) {
            return schedulingRepository.findAll();
        }

        return schedulingRepository.findByBarber_Id(barberId);
    }
}
