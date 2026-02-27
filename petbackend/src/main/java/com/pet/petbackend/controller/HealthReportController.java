package com.pet.petbackend.controller;

import com.pet.petbackend.service.HealthReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class HealthReportController {

    private final HealthReportService healthReportService;

    @GetMapping("/{petId}/health-report")
    public ResponseEntity<byte[]> generateReport(@PathVariable Long petId,
                                                 @RequestParam(defaultValue = "inline") String type) {

        byte[] pdfBytes = healthReportService.generateHealthReport(petId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        if (type.equals("download")) {
            headers.setContentDisposition(
                    ContentDisposition.attachment()
                            .filename("health-report.pdf")
                            .build());
        } else {
            headers.setContentDisposition(
                    ContentDisposition.inline()
                            .filename("health-report.pdf")
                            .build());
        }

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}