package com.lms.lms_backend.auth.service;

import com.lms.lms_backend.auth.dto.AuthResponse;
import com.lms.lms_backend.auth.dto.LoginRequest;
import com.lms.lms_backend.auth.dto.RegisterRequest;
import com.lms.lms_backend.auth.security.JwtService;
import com.lms.lms_backend.entity.Role;
import com.lms.lms_backend.entity.User;
import com.lms.lms_backend.repository.RoleRepository;
import com.lms.lms_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException(
                    "Email is already registered"
            );
        }

        Role studentRole = roleRepository.findByName("ROLE_STUDENT")
                .orElseThrow(() ->
                        new IllegalStateException(
                                "ROLE_STUDENT not found"
                        )
                );

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(studentRole)
                .isActive(true)
                .build();

        User savedUser = userRepository.save(user);

        String token = jwtService.generateToken(
                org.springframework.security.core.userdetails.User
                        .withUsername(savedUser.getEmail())
                        .password(savedUser.getPassword())
                        .authorities(savedUser.getRole().getName())
                        .build()
        );

        return AuthResponse.builder()
                .token(token)
                .userId(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .role(savedUser.getRole().getName())
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found")
                );

        String token = jwtService.generateToken(
                org.springframework.security.core.userdetails.User
                        .withUsername(user.getEmail())
                        .password(user.getPassword())
                        .authorities(user.getRole().getName())
                        .build()
        );

        return AuthResponse.builder()
                .token(token)
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().getName())
                .build();
    }
}
