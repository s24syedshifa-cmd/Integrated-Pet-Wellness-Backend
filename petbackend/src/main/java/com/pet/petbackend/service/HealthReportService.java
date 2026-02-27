package com.pet.petbackend.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.pet.petbackend.entity.*;
import com.pet.petbackend.repository.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HealthReportService {

private final PetRepository petRepository;  
private final VaccinationRepository vaccinationRepository;  
private final MedicalHistoryRepository medicalHistoryRepository;  

public byte[] generateHealthReport(Long petId) {  

    Pet pet = petRepository.findById(petId)  
            .orElseThrow(() -> new RuntimeException("Pet not found"));  

    List<Vaccination> vaccinations = vaccinationRepository.findByPetId(petId);  
    List<MedicalHistory> histories = medicalHistoryRepository.findByPetId(petId);  

    try {  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        Document document = new Document();  
        PdfWriter.getInstance(document, out);  

        document.open();  

        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);  
        Paragraph title = new Paragraph("Pet Health Report", titleFont);  
        title.setAlignment(Element.ALIGN_CENTER);  
        document.add(title);  

        document.add(new Paragraph(" "));  
        document.add(new Paragraph("Generated on: " + LocalDate.now()));  
        document.add(new Paragraph(" "));  

        Font sectionFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);  

        // Pet Details  
        document.add(new Paragraph("Pet Details", sectionFont));  
        document.add(new Paragraph("Name: " + pet.getPetName()));  
        document.add(new Paragraph("Species: " + pet.getSpecies()));  
        document.add(new Paragraph("Breed: " + pet.getBreed()));  
        document.add(new Paragraph("Gender: " + pet.getGender()));  
        document.add(new Paragraph("Weight: " + pet.getWeight()));  
        document.add(new Paragraph(" "));  

        // Vaccinations  
        document.add(new Paragraph("Vaccination Records", sectionFont));  

        for (Vaccination v : vaccinations) {  
            document.add(new Paragraph(  
                    v.getVaccineName()  
                            + " | Given: " + v.getGivenDate()  
                            + " | Next Due: " + v.getNextDueDate()  
                            + " | Status: " + v.getStatus()  
            ));  
        }  

        document.add(new Paragraph(" "));  

        // Medical History  
        document.add(new Paragraph("Medical History", sectionFont));  

        for (MedicalHistory m : histories) {  
            document.add(new Paragraph(  
                    m.getConditionName()  
                            + " | Treatment: " + m.getTreatment()  
                            + " | Notes: " + m.getNotes()  
            ));  
        }  

        document.close();  
        return out.toByteArray();  

    } catch (Exception e) {  
        throw new RuntimeException("Error generating PDF", e);  
    }  
  }
}
