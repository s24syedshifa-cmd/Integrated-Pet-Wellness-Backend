package com.pet.petbackend.controller;

import com.pet.petbackend.dto.UserResponse;
import com.pet.petbackend.entity.AppointmentSlot;
import com.pet.petbackend.repository.AppointmentSlotRepository;
import com.pet.petbackend.service.AdminService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // Repository for appointment slots
    private final AppointmentSlotRepository slotRepository;


    // Get all users
    @GetMapping("/users")
    public List<UserResponse> getAllUsers() {
        return adminService.getAllUsers();
    }


    // Approve user
    @PutMapping("/approve/{id}")
    public String approveUser(@PathVariable Long id) {
        adminService.approveUser(id);
        return "User approved successfully";
    }


    // Delete user
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return "User deleted successfully";
    }


    // Create appointment slot
    @PostMapping("/create-slot")
    public AppointmentSlot createSlot(@RequestBody AppointmentSlot slot) {
        return slotRepository.save(slot);
    }

}