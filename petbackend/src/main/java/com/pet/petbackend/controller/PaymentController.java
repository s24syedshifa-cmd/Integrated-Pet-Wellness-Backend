package com.pet.petbackend.controller;

import com.pet.petbackend.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create-order")
    public String createOrder(@RequestParam double amount) throws Exception {

        return paymentService.createOrder(amount);

    }
}