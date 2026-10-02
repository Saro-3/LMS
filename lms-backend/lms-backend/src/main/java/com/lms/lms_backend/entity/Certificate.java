package com.lms.lms_backend.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "certificates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "certificate_id", nullable = false, unique = true, length = 100)
    private String certificateId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id", nullable = false, unique = true)
    private Enrollment enrollment;

    @Column(name = "issued_at")
    private LocalDateTime issuedAt;

    @Column(name = "certificate_url", length = 500)
    private String certificateUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {

        if (issuedAt == null) {
            issuedAt = LocalDateTime.now();
        }

        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}
