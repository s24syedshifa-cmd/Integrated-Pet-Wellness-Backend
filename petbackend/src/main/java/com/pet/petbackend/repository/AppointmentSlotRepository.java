package com.pet.petbackend.repository;

import com.pet.petbackend.entity.AppointmentSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentSlotRepository extends JpaRepository<AppointmentSlot, Long> {

    List<AppointmentSlot> findByAvailableTrue();

}