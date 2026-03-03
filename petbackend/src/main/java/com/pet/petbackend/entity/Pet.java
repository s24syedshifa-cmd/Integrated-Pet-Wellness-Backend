package com.pet.petbackend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

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
    

    @ManyToOne
@JoinColumn(name = "user_id", nullable = false)
private User user;

@OneToMany(mappedBy = "pet", cascade = CascadeType.ALL)
private List<Vaccination> vaccinations;
    private String petName;
    private String species;
    private String breed;
    private String gender;
    private String dateOfBirth;
    private double weight;
    private String color;



}
