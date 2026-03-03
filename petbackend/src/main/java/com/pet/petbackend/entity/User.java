package com.pet.petbackend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @Builder.Default
    private String role = "ROLE_USER";

    private boolean approved;

    @Column(name = "email_verified")
    private boolean verified;

    private String otp;

    private LocalDateTime otpExpiry;

    @Builder.Default
    private boolean firstLogin = true;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
private List<Pet> pets;
}