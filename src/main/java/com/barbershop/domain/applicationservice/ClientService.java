package com.barbershop.domain.applicationservice;

import com.barbershop.domain.entity.Client;
import com.barbershop.domain.entity.model.Role;
import com.barbershop.domain.exception.client.ClientNotFoundException;
import com.barbershop.domain.repository.ClientRepository;
import com.barbershop.infrastructure.dto.client.SaveClientDataDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    /**
     * Create a Client
     */
    public Client createClient(SaveClientDataDTO dto) {

        Client client = new Client();
        client.setName(dto.getName());
        client.setEmail(dto.getEmail());
        client.setPassword(dto.getPassword()); // se quiser, codifica no AuthService
        client.setRole(Role.CLIENT); // sempre CLIENT

        return clientRepository.save(client);
    }

    /**
     * Load client by ID
     */
    public Client loadClientById(String id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
    }

    /**
     * Update a client
     */
    @Transactional
    public Client updateClient(String id, SaveClientDataDTO dto) {

        Client client = loadClientById(id);

        client.setName(dto.getName());
        client.setEmail(dto.getEmail());

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            client.setPassword(dto.getPassword());
        }

        client.setRole(Role.CLIENT); // sempre CLIENT

        return client;
    }

    /**
     * Delete a client
     */
    @Transactional
    public void deleteClient(String id) {
        Client client = loadClientById(id);
        clientRepository.delete(client);
    }

    /**
     * Find all or filter by email
     */
    public List<Client> findClients(String email) {

        if (email == null) {
            return clientRepository.findAll();
        }

        return clientRepository
                .findByEmail(email)
                .map(List::of)
                .orElse(List.of());
    }
}
