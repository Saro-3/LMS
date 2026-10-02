package com.lms.lms_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "quiz_questions")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column(name = "question_text", nullable = false, columnDefinition = "TEXT")
    private String questionText;

    @Column(name = "option_a", nullable = false, length = 500)
    private String optionA;

    @Column(name = "option_b", nullable = false, length = 500)
    private String optionB;

    @Column(name = "option_c", nullable = false, length = 500)
    private String optionC;

    @Column(name = "option_d", nullable = false, length = 500)
    private String optionD;

    @Enumerated(EnumType.STRING)
    @Column(name = "correct_option")
    private CorrectOptions correctOptions;

    @Column(precision = 5, scale = 2)
    private BigDecimal marks;

    @Column(name = "question_order", nullable = false)
    private Integer questionOrder;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {

        createdAt = LocalDateTime.now();

        if(marks == null) {
            marks = BigDecimal.ONE;
        }
    }
}
