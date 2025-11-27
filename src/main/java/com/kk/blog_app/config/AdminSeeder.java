package com.kk.blog_app.config;

import com.kk.blog_app.domain.entities.Role;
import com.kk.blog_app.domain.entities.User;
import com.kk.blog_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${BOOTSTRAP_ADMIN:false}")
    private boolean bootstrapAdmin;

    @Value("${ADMIN_EMAIL}")
    private String adminEmail;

    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;

    @Override
    public void run(String... args) throws Exception {

        if(!bootstrapAdmin) return;

        if (adminEmail.isBlank() || adminPassword.isBlank()) {
            log.warn("Admin email or password is not set. Skipping Admin creation.");
            return;
        }

        if (userRepository.existsByEmail(adminEmail)) {
            log.info("Admin {} already exists. Skipping Admin creation", adminEmail);
            return;
        }

        User admin = User.builder()
                .firstName("Super")
                .lastName("Admin")
                .email(adminEmail)
                .password(passwordEncoder.encode(adminPassword))
                .role(Role.ROLE_ADMIN)
                .build();

        userRepository.save(admin);
        log.info("BOOTSTRAP: Admin user '{}' created successfully", adminEmail);
    }
}
