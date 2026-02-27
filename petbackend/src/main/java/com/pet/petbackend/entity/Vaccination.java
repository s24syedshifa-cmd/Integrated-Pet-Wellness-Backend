package com.pet.petbackend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vaccination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vaccineName;

    private LocalDate givenDate;
    private LocalDate lastGivenDate;

    private LocalDate nextDueDate;

    @Enumerated(EnumType.STRING)
    private VaccinationStatus status;

    @Builder.Default
    private Boolean reminderSent = false;

    @Builder.Default
    private Integer reminderCount = 0;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;
}