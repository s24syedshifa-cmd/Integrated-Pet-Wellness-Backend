package com.pet.petbackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long petId;

    private Long userId;

    private Long slotId;

    private String notes;

    private String status; // BOOKED / CANCELLED / COMPLETED
}