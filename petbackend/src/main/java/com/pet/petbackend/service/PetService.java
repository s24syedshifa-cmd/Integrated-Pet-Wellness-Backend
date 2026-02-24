package com.pet.petbackend.service;

import com.pet.petbackend.entity.Pet;
import com.pet.petbackend.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    // Add Pet
    public Pet savePet(Pet pet) {
        return petRepository.save(pet);
    }

    // Get All Pets
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    // Get Pet By Id
    public Pet getPetById(Long id) {
        Optional<Pet> pet = petRepository.findById(id);
        return pet.orElseThrow(() -> new RuntimeException("Pet not found with id: " + id));
    }

    // Delete Pet
    public void deletePet(Long id) {
        if (!petRepository.existsById(id)) {
            throw new RuntimeException("Pet not found with id: " + id);
        }
        petRepository.deleteById(id);
    }
}