package com.lms.lms_backend.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "learning_materials")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LearningMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "material_type", nullable = false)
    private MaterialType materialType;

    @Column(name = "file_url", length = 500)
    private String fileUrl;

    @Column(name = "external_url", length = 500)
    private String externalUrl;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}
