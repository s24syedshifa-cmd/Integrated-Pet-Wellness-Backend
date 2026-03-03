package com.pet.petbackend.service;

import com.pet.petbackend.dto.LoginRequest;
import com.pet.petbackend.dto.RegisterRequest;
import com.pet.petbackend.entity.User;
import com.pet.petbackend.repository.UserRepository;
import com.pet.petbackend.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final EmailService emailService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // ================= REGISTER =================
    public String register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email already exists!";
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole("ROLE_USER");
        user.setApproved(false);

        // Generate OTP
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        user.setOtp(otp);
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(5));
        user.setVerified(false);

        userRepository.save(user);

        // Send OTP Email
        emailService.sendEmail(
                user.getEmail(),
                "OTP Verification",
                "Your OTP is: " + otp + "\nValid for 5 minutes."
        );

        return "Registration successful. OTP sent to email.";
    }

    // ================= VERIFY OTP =================
    public String verifyOtp(String email, String otp) {

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            return "User not found";
        }

        User user = optionalUser.get();

        if (user.getOtp() == null || user.getOtpExpiry() == null) {
            return "OTP not generated. Please register again.";
        }

        if (user.getOtpExpiry().isBefore(LocalDateTime.now())) {
            return "OTP expired. Please request a new OTP.";
        }

        if (!user.getOtp().equals(otp)) {
            return "Invalid OTP. Please try again.";
        }

        user.setVerified(true);
        user.setOtp(null);
        user.setOtpExpiry(null);

        userRepository.save(user);

        return "OTP verified successfully. You can now login.";
    }

    // ================= RESEND OTP =================
    public String resendOtp(String email) {

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            return "User not found";
        }

        User user = optionalUser.get();

        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        user.setOtp(otp);
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(5));
        user.setVerified(false);

        userRepository.save(user);

        emailService.sendEmail(
                user.getEmail(),
                "Resend OTP",
                "Your new OTP is: " + otp + "\nValid for 5 minutes."
        );

        return "OTP resent successfully. Check your email.";
    }

    // ================= LOGIN =================
    public String login(LoginRequest request) {

        Optional<User> optionalUser =
                userRepository.findByEmail(request.getEmail());

        if (optionalUser.isEmpty()) {
            return "User not found";
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return "Invalid password";
        }

        if (!user.isVerified()) {
            return "Please verify your email OTP before login.";
        }

        if (!user.isApproved()) {
            return "Wait for admin approval.";
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return token;
    }
}