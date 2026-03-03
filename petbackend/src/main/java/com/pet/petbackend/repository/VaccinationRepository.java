package com.pet.petbackend.repository;

import com.pet.petbackend.entity.Vaccination;
import com.pet.petbackend.entity.VaccinationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface VaccinationRepository extends JpaRepository<Vaccination, Long> {

    List<Vaccination> findByPetId(Long petId);
    List<Vaccination> findByStatusIn(List<VaccinationStatus> statuses);  
    List<Vaccination> findByStatusAndNextDueDateLessThanEqualAndReminderCountLessThan(
            VaccinationStatus status,
            LocalDate date,
            int reminderCount
    );
}