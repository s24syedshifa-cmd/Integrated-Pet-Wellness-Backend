package com.pet.petbackend.repository;

import com.pet.petbackend.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    List<Orders> findByUserId(Long userId);

}