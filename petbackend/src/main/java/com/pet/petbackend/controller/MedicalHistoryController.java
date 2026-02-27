package com.pet.petbackend.controller;

import com.pet.petbackend.entity.MedicalHistory;
import com.pet.petbackend.service.MedicalHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MedicalHistoryController {

    private final MedicalHistoryService medicalHistoryService;

    @PostMapping("/pets/{petId}/medical-history")
    public MedicalHistory addHistory(@PathVariable Long petId,
                                     @RequestBody MedicalHistory history) {
        return medicalHistoryService.addHistory(petId, history);
    }

    @GetMapping("/pets/{petId}/medical-history")
    public List<MedicalHistory> getHistory(@PathVariable Long petId) {
        return medicalHistoryService.getHistory(petId);
    }

    @DeleteMapping("/medical-history/{id}")
    public void deleteHistory(@PathVariable Long id) {
        medicalHistoryService.deleteHistory(id);
    }
}