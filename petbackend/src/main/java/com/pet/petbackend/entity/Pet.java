package com.pet.petbackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String petName;
    private String species;
    private String breed;
    private String gender;
    private String dateOfBirth;
    private double weight;
    private String color;
}
