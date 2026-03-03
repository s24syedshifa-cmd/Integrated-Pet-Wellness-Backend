package com.pet.petbackend.service;

import com.pet.petbackend.entity.Pet;
import com.pet.petbackend.exception.ResourceNotFoundException;
import com.pet.petbackend.repository.PetRepository;
import com.pet.petbackend.entity.User;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.List;


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

    User loggedUser = (User) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();

    return petRepository.findByIdAndUserId(id, loggedUser.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Pet not found"));
}

    // Delete Pet
    public void deletePet(Long id) {
        if (!petRepository.existsById(id)) {
            throw new RuntimeException("Pet not found with id: " + id);
        }
        petRepository.deleteById(id);
    }
}