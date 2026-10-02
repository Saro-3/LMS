package com.lms.lms_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "assignment_submissions")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignmentSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @Column(name = "submission_text", columnDefinition = "TEXT")
    private String submissionText;

    @Column(name = "file_url", length = 500)
    private String fileUrl;

    @Column(precision = 5, scale = 2)
    private BigDecimal marks;

    @Column(name = "instructor_feedback", columnDefinition = "TEXT")
    private String instructorFeedback;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private AssignmentStatus status = AssignmentStatus.SUBMITTED;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    @Column(name = "evaluated_at")
    private LocalDateTime evaluatedAt;

    @PrePersist
    protected void onCreate() {
        if (submittedAt == null) {
            submittedAt = LocalDateTime.now();
        }

        if (status == null) {
            status = AssignmentStatus.SUBMITTED;
        }
    }
}
