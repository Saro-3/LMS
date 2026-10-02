package com.lms.lms_backend.repository;

import com.lms.lms_backend.entity.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {

    List<QuizQuestion> findByQuizIdOrderByQuestionOrderAsc(Long quizId);
}
