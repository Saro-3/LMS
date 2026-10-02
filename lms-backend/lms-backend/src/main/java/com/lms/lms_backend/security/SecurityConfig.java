package com.lms.lms_backend.security;

import com.lms.lms_backend.auth.service.CustomerUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomerUserDetailsService customerUserDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(
                        customerUserDetailsService
                );

        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityWebFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .cors(cors ->
                        cors.configurationSource(
                                corsConfigurationSource()
                        )
                )

                .formLogin(form -> form.disable())

                .httpBasic(basic -> basic.disable())

                .authorizeHttpRequests(auth -> auth

                        // ==============================
                        // Authentication
                        // ==============================
                        .requestMatchers(
                                "/api/auth/**"
                        ).permitAll()


                        // ==============================
                        // Public Course APIs
                        // ==============================
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/courses/**"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/categories/**"
                        ).permitAll()


                        // ==============================
                        // Student APIs
                        // ==============================
                        .requestMatchers(
                                "/api/student/**"
                        ).hasRole("STUDENT")

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/assignment-submissions/student/**"
                        ).hasRole("STUDENT")


                        // ==============================
                        // Assignment Submission
                        // ==============================
                        // Student/Instructor can view one submission
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/assignment-submissions/*"
                        ).authenticated()


                        // ==============================
                        // Instructor APIs
                        // ==============================
                        .requestMatchers(
                                "/api/instructor/**"
                        ).hasRole("INSTRUCTOR")


                        // Assignment management
                        .requestMatchers(
                                "/api/assignments/**"
                        ).hasRole("INSTRUCTOR")


                        // Assignment submission management
                        .requestMatchers(
                                "/api/assignment-submissions/**"
                        ).hasRole("INSTRUCTOR")


                        // ==============================
                        // Admin APIs
                        // ==============================
                        .requestMatchers(
                                "/api/admin/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/categories"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/categories/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/categories/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/categories/**"
                        ).authenticated()


                        // ==============================
                        // Everything Else
                        // ==============================
                        .anyRequest().authenticated()
                )

                // ==============================
                // Session Management
                // ==============================
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                // ==============================
                // Authentication Provider
                // ==============================
                .authenticationProvider(
                        authenticationProvider()
                )

                // ==============================
                // JWT Filter
                // ==============================
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }


    // ==========================================
    // CORS Configuration
    // ==========================================
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of(
                        "http://localhost:5173"
                )
        );

        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "OPTIONS"
                )
        );

        configuration.setAllowedHeaders(
                List.of("*")
        );

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }
}