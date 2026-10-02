package com.lms.lms_backend.config;

import com.lms.lms_backend.entity.Role;
import com.lms.lms_backend.entity.User;
import com.lms.lms_backend.repository.RoleRepository;
import com.lms.lms_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() ->
                        new RuntimeException("Role Admin Not Found"));

        Role instructorRole = roleRepository.findByName("ROLE_INSTRUCTOR")
                .orElseThrow(() ->
                        new RuntimeException("Role Instructor Not Found"));

        createUserIfNotExists(
                "LMS Admin",
                "admin@lms.com",
                "Admin@123",
                adminRole
        );

        createUserIfNotExists(
                "LMS Instructor",
                "instructor@lms.com",
                "Instructor@123",
                instructorRole
        );
    }
    private void createUserIfNotExists(
            String name,
            String email,
            String password,
            Role role) {

        if (!userRepository.existsByEmail(email)) {
            User user = User.builder()
                    .name(name)
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .role(role)
                    .isActive(true)
                    .build();

            userRepository.save(user);

            System.out.println(
                    "Created user: " + email +
                            " With role: " + role.getName()
            );
        }
    }
}
