package com.lms.lms_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "quizzes")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "passing_score", precision = 5, scale = 2)
    private BigDecimal passingScore;

    @Column(name = "time_limit_minutes")
    private Integer timeLimitMinutes;

    @Column(name = "is_published")
    private Boolean isPublished = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();

        createdAt = now;
        updatedAt = now;

        if(passingScore == null) {
            passingScore = BigDecimal.valueOf(50.00);
        }

        if(isPublished == null) {
            isPublished = false;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
