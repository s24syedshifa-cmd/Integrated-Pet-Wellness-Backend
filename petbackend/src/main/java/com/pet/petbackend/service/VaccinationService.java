package com.pet.petbackend.service;

import com.pet.petbackend.entity.*;
import com.pet.petbackend.repository.PetRepository;
import com.pet.petbackend.repository.VaccinationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VaccinationService {

    private final VaccinationRepository vaccinationRepository;
    private final PetRepository petRepository;

    // ✅ Get logged-in user ID safely
    private Long getLoggedInUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user.getId();
    }

    // ✅ ADD Vaccination
    public Vaccination addVaccination(Long petId, Vaccination vaccination) {

        Long loggedUserId = getLoggedInUserId();

        Pet pet = petRepository.findByIdAndUserId(petId, loggedUserId)
                .orElseThrow(() -> new RuntimeException("Pet not found"));

        vaccination.setPet(pet);
        vaccination.setCreatedAt(LocalDateTime.now());

        vaccination.setReminderSent(false);
        vaccination.setReminderCount(0);

        updateStatus(vaccination);

        return vaccinationRepository.save(vaccination);
    }

    // ✅ GET Vaccinations
    public List<Vaccination> getVaccinationsByPet(Long petId) {

        Long loggedUserId = getLoggedInUserId();

        petRepository.findByIdAndUserId(petId, loggedUserId)
                .orElseThrow(() -> new RuntimeException("Pet not found"));

        return vaccinationRepository.findByPetId(petId);
    }

    // ✅ UPDATE Vaccination (Important Production Logic Added)
    public Vaccination updateVaccination(Long id, Vaccination updatedVaccination) {

        Long loggedUserId = getLoggedInUserId();

        Vaccination vaccination = vaccinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vaccination not found"));

        // 🔐 Security check
        if (!vaccination.getPet().getUser().getId().equals(loggedUserId)) {
            throw new RuntimeException("Unauthorized access");
        }

        vaccination.setVaccineName(updatedVaccination.getVaccineName());

        // 🎯 If vaccine is marked as given (VISITED case)
        if (updatedVaccination.getGivenDate() != null &&
                vaccination.getGivenDate() == null) {

            LocalDate givenDate = updatedVaccination.getGivenDate();

            vaccination.setGivenDate(givenDate);
            vaccination.setLastGivenDate(givenDate);

            // ✅ Calculate next due date (Example: 1 year cycle)
            vaccination.setNextDueDate(givenDate.plusYears(1));

            // ✅ Reset Reminder System
            vaccination.setReminderCount(0);
            vaccination.setReminderSent(false);

            vaccination.setStatus(VaccinationStatus.UPCOMING);

        } else {

            // Normal update
            vaccination.setGivenDate(updatedVaccination.getGivenDate());
            vaccination.setNextDueDate(updatedVaccination.getNextDueDate());

            updateStatus(vaccination);
        }

        return vaccinationRepository.save(vaccination);
    }

    public void deleteVaccination(Long id) {
        vaccinationRepository.deleteById(id);
    }

    // ✅ STATUS MANAGEMENT
    private void updateStatus(Vaccination vaccination) {

        LocalDate today = LocalDate.now();

        if (vaccination.getNextDueDate() == null) {
            vaccination.setStatus(VaccinationStatus.UPCOMING);
            return;
        }

        if (today.isBefore(vaccination.getNextDueDate())) {
            vaccination.setStatus(VaccinationStatus.UPCOMING);
        } else if (today.isEqual(vaccination.getNextDueDate())) {
            vaccination.setStatus(VaccinationStatus.DUE_TODAY);
        } else {
            vaccination.setStatus(VaccinationStatus.OVERDUE);
        }
    }
}