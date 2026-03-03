package com.pet.petbackend.repository;

import com.pet.petbackend.entity.MedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Long> {

    List<MedicalHistory> findByPetId(Long petId);
}