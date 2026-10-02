package com.lms.lms_backend.repository;

import com.lms.lms_backend.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    List<Quiz> findByCourseId(Long courseId);

    List<Quiz> findByCourseIdAndIsPublished(Long courseId, Boolean isPublished);
}
