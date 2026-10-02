package com.lms.lms_backend.repository;

import com.lms.lms_backend.entity.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {

    List<QuizAttempt> findByStudentId(Long studentId);

    List<QuizAttempt> findByQuizId(Long quizId);

    List<QuizAttempt> findByStudentIdAndQuizId(Long studentId, Long quizId);
}
