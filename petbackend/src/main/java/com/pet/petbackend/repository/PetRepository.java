package com.pet.petbackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pet.petbackend.entity.Pet;

public interface PetRepository extends JpaRepository<Pet, Long> {
    Optional<Pet> findByIdAndUserId(Long petId,Long userId);
}