package com.pet.petbackend.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import com.pet.petbackend.entity.Pet;

public interface PetRepository extends JpaRepository<Pet, Long> {
}