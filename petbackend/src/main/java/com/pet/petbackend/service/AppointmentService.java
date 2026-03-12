package com.pet.petbackend.service;

import com.pet.petbackend.entity.Appointment;
import com.pet.petbackend.entity.AppointmentSlot;
import com.pet.petbackend.repository.AppointmentRepository;
import com.pet.petbackend.repository.AppointmentSlotRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentSlotRepository slotRepository;

    public List<AppointmentSlot> getAvailableSlots() {
        return slotRepository.findByAvailableTrue();
    }

    public Appointment bookAppointment(Long petId, Long userId, Long slotId, String notes) {

        AppointmentSlot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        if (!slot.isAvailable()) {
            throw new RuntimeException("Slot already booked");
        }

        slot.setAvailable(false);
        slotRepository.save(slot);

        Appointment appointment = Appointment.builder()
                .petId(petId)
                .userId(userId)
                .slotId(slotId)
                .notes(notes)
                .status("BOOKED")
                .build();

        return appointmentRepository.save(appointment);
    }

}