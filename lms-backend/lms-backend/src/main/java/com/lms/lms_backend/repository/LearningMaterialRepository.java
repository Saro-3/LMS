package com.lms.lms_backend.repository;

import com.lms.lms_backend.entity.LearningMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LearningMaterialRepository
        extends JpaRepository<LearningMaterial, Long> {

    List<LearningMaterial> findByLessonId(Long lessonId);
}
