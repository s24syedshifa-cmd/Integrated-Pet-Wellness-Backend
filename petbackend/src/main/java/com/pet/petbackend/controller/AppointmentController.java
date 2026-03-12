package com.pet.petbackend.controller;

import com.pet.petbackend.entity.Appointment;
import com.pet.petbackend.entity.AppointmentSlot;
import com.pet.petbackend.service.AppointmentService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping("/slots")
    public List<AppointmentSlot> getAvailableSlots() {
        return appointmentService.getAvailableSlots();
    }

    @PostMapping("/book")
    public Appointment bookAppointment(@RequestBody Appointment request) {

        return appointmentService.bookAppointment(
                request.getPetId(),
                request.getUserId(),
                request.getSlotId(),
                request.getNotes()
        );
    }
}
