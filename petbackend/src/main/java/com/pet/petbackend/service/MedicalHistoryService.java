package com.pet.petbackend.service;

import com.pet.petbackend.entity.MedicalHistory;
import com.pet.petbackend.entity.Pet;
import com.pet.petbackend.entity.User;
import com.pet.petbackend.repository.MedicalHistoryRepository;
import com.pet.petbackend.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalHistoryService {

    private final MedicalHistoryRepository medicalHistoryRepository;
    private final PetRepository petRepository;

    // ✅ Get logged-in user ID
    private Long getLoggedInUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user.getId();
    }

    public MedicalHistory addHistory(Long petId, MedicalHistory history) {

        Long loggedUserId = getLoggedInUserId();

        Pet pet = petRepository.findByIdAndUserId(petId, loggedUserId)
                .orElseThrow(() -> new RuntimeException("Pet not found"));

        history.setPet(pet);
        history.setCreatedAt(LocalDateTime.now());

        return medicalHistoryRepository.save(history);
    }

    public List<MedicalHistory> getHistory(Long petId) {

        Long loggedUserId = getLoggedInUserId();

        petRepository.findByIdAndUserId(petId, loggedUserId)
                .orElseThrow(() -> new RuntimeException("Pet not found"));

        return medicalHistoryRepository.findByPetId(petId);
    }

    public void deleteHistory(Long id) {
        medicalHistoryRepository.deleteById(id);
    }
}