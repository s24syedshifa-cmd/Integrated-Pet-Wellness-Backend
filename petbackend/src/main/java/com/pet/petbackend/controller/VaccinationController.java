package com.pet.petbackend.controller;

import com.pet.petbackend.entity.Vaccination;
import com.pet.petbackend.service.VaccinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class VaccinationController {

    private final VaccinationService vaccinationService;

    @PostMapping("/pets/{petId}/vaccinations")
    public Vaccination addVaccination(@PathVariable Long petId,
                                      @RequestBody Vaccination vaccination) {
        return vaccinationService.addVaccination(petId, vaccination);
    }

    @GetMapping("/pets/{petId}/vaccinations")
    public List<Vaccination> getVaccinations(@PathVariable Long petId) {
        return vaccinationService.getVaccinationsByPet(petId);
    }

    @PutMapping("/vaccinations/{id}")
    public Vaccination updateVaccination(@PathVariable Long id,
                                         @RequestBody Vaccination vaccination) {
        return vaccinationService.updateVaccination(id, vaccination);
    }

    @DeleteMapping("/vaccinations/{id}")
    public void deleteVaccination(@PathVariable Long id) {
    }
}
