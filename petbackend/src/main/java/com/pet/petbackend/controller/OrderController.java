package com.pet.petbackend.controller;

import com.pet.petbackend.entity.Orders;
import com.pet.petbackend.service.OrderService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/place")
    public Orders placeOrder(@RequestBody Orders order) {
        return orderService.placeOrder(order);
    }

    @GetMapping("/user/{userId}")
    public List<Orders> getUserOrders(@PathVariable Long userId) {
        return orderService.getUserOrders(userId);
    }
}