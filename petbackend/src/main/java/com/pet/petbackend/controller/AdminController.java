package com.pet.petbackend.controller;

import com.pet.petbackend.dto.UserResponse;
import com.pet.petbackend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/users")
    public List<UserResponse> getAllUsers() {
        return adminService.getAllUsers();
    }

    @PutMapping("/approve/{id}")
    public String approveUser(@PathVariable Long id) {
        adminService.approveUser(id);
        return "User approved successfully";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return "User deleted successfully";
    }
}
