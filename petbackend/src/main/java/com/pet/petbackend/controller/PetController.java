package com.pet.petbackend.controller;

import com.pet.petbackend.entity.Pet;
import com.pet.petbackend.service.PetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public Pet addPet(@RequestBody Pet pet) {
        return petService.savePet(pet);
    }

    @GetMapping
    public List<Pet> getAllPets() {
        return petService.getAllPets();
    }

    @GetMapping("/{id}")
    public Pet getPetById(@PathVariable Long id) {
        return petService.getPetById(id);
    }

    @DeleteMapping("/{id}")
    public String deletePet(@PathVariable Long id) {
        petService.deletePet(id);
        return "Pet deleted successfully";
    }
}