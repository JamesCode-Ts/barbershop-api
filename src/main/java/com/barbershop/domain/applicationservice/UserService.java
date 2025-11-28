package com.barbershop.domain.applicationservice;


import com.barbershop.domain.entity.User;
import com.barbershop.domain.entity.model.Role;
import com.barbershop.domain.exception.user.UserNotFoundException;
import com.barbershop.domain.exception.user.DuplicateEmailException;
import com.barbershop.domain.repository.UserRepository;
import com.barbershop.infrastructure.dto.user.SaveUserDataDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Create a User (ADM creates Barber or Barbershop Admin)
     */
    public User createUser(SaveUserDataDTO dto) {

        if (emailExists(dto.getEmail(), null)) {
            throw new DuplicateEmailException(dto.getEmail());
        }

        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(dto.getRole()) // CLIENT, BARBER, BARBERSHOP_ADMIN, MASTER_ADMIN
                .build();

        return userRepository.save(user);
    }

    /**
     * Load User by ID
     */
    public User loadUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    /**
     * Delete User (soft delete not needed unless required)
     */
    @Transactional
    public void deleteUser(String id) {
        User user = loadUserById(id);
        userRepository.delete(user);
    }

    /**
     * Update User
     */
    @Transactional
    public User updateUser(String id, SaveUserDataDTO dto) {

        if (emailExists(dto.getEmail(), id)) {
            throw new DuplicateEmailException(dto.getEmail());
        }

        User user = loadUserById(id);

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        // update password only if provided
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        user.setRole(dto.getRole());

        return user;
    }

    /**
     * Find by email or return all
     */
    public List<User> findUsers(String email) {
        if (email == null) {
            return userRepository.findAll();
        }

        return userRepository
                .findByEmail(email)
                .map(List::of)
                .orElse(List.of());
    }

    /**
     * Helper — Checks if email belongs to another user
     */
    private boolean emailExists(String email, String idToExclude) {
        return userRepository.findByEmail(email)
                .filter(u -> !Objects.equals(u.getId(), idToExclude))
                .isPresent();
    }
}
