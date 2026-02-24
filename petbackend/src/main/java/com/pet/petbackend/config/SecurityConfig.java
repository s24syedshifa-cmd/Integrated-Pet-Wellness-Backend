package com.pet.petbackend.config;

import com.pet.petbackend.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter; // must be @Component

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable()) // disable CSRF for POST requests
            .authorizeHttpRequests(auth -> auth
                // ✅ Whitelist all auth endpoints (register, login, OTP)
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/pets/**").permitAll()
                // admin endpoints require ADMIN role
                .requestMatchers("/api/admin/**").hasAuthority("ROLE_ADMIN")
                // all other endpoints require JWT
                .anyRequest().authenticated()
            )
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .formLogin(form -> form.disable());

        // Add JWT filter (will skip /api/auth/** inside the filter)
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}