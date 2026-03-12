package com.pet.petbackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    private Long userId;

    private double amount;

    private String paymentMethod; // CARD / UPI / NETBANKING

    private String paymentStatus; // SUCCESS / FAILED

    private String transactionId;
}