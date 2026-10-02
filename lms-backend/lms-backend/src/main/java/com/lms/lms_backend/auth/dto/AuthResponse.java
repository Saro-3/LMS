package com.lms.lms_backend.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private Long userId;
    private String name;
    private String email;
    private String role;

}
