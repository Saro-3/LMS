package com.lms.lms_backend.security;

import com.lms.lms_backend.auth.security.JwtService;
import com.lms.lms_backend.auth.service.CustomerUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomerUserDetailsService customerUserDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader =
                request.getHeader("Authorization");

        System.out.println("authHeader: " + authHeader);

        String jwt =  null;
        String userEmail = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);

            try {
                 userEmail = jwtService.extractUsername(jwt);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (userEmail != null
                && SecurityContextHolder
                .getContext()
                .getAuthentication() == null){

            UserDetails userDetails =
                    customerUserDetailsService
                            .loadUserByUsername(userEmail);

            if(jwtService.isTokenValid(jwt, userDetails)) {

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authentication.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
