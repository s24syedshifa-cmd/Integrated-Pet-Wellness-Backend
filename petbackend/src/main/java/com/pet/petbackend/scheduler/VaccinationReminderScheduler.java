package com.pet.petbackend.scheduler;

import com.pet.petbackend.entity.Vaccination;
import com.pet.petbackend.entity.VaccinationStatus;
import com.pet.petbackend.repository.VaccinationRepository;
import com.pet.petbackend.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class VaccinationReminderScheduler {

    private final VaccinationRepository vaccinationRepository;
    private final EmailService emailService;

    // Runs every day at 9 AM
    @Scheduled(cron = "0 0 9 * * ?")
    @Transactional
    public void sendVaccinationReminders() {

        LocalDate today = LocalDate.now();

        // Fetch all vaccinations which are not completed
        List<Vaccination> vaccinations =
                vaccinationRepository.findByStatusIn(
                        List.of(VaccinationStatus.UPCOMING, VaccinationStatus.OVERDUE)
                );

        for (Vaccination vaccination : vaccinations) {

            LocalDate dueDate = vaccination.getNextDueDate();

            if (dueDate == null) continue;

            int reminderCount = vaccination.getReminderCount() == null
                    ? 0
                    : vaccination.getReminderCount();

            // ✅ Mark OVERDUE
            if (today.isAfter(dueDate)) {
                vaccination.setStatus(VaccinationStatus.OVERDUE);
            }

            long daysDifference = today.until(dueDate).getDays();

            // ✅ Send only if within 2 days and max 3 reminders
            if (reminderCount < 3 &&
                    (daysDifference == 2 || daysDifference == 1 || daysDifference == 0)) {

                String email = vaccination.getPet().getUser().getEmail();

                emailService.sendEmail(
                        email,
                        "Vaccination Reminder",
                        "Dear User,\n\nYour pet "
                                + vaccination.getPet().getPetName()
                                + " has vaccination due on "
                                + dueDate
                                + ".\n\nPlease visit the clinic.\n\nThank You."
                );

                vaccination.setReminderSent(true);
                vaccination.setReminderCount(reminderCount + 1);
            }

            vaccinationRepository.save(vaccination);
        }
    }
}