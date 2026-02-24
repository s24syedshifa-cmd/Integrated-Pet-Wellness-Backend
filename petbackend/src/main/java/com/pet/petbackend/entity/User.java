package com.pet.petbackend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

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

    private String role = "ROLE_USER";

    private boolean approved;

    private boolean verified;

    private String otp;

    private LocalDateTime otpExpiry;

    private boolean firstLogin = true;
}