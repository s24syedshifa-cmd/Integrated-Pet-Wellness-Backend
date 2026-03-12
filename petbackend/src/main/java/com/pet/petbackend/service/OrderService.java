package com.pet.petbackend.service;

import com.pet.petbackend.entity.Orders;
import com.pet.petbackend.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Orders placeOrder(Orders order) {

        order.setPaymentStatus("PAID");
        order.setOrderStatus("PLACED");

        return orderRepository.save(order);
    }

    public List<Orders> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}