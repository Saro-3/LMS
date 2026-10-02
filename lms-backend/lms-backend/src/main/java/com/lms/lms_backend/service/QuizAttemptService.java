package com.lms.lms_backend.service;


import com.lms.lms_backend.entity.*;
import com.lms.lms_backend.repository.QuizAttemptRepository;
import com.lms.lms_backend.repository.QuizQuestionRepository;
import com.lms.lms_backend.repository.QuizRepository;
import com.lms.lms_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QuizAttemptService {

    private final QuizAttemptRepository quizAttemptRepository;
    private final QuizRepository quizRepository;
    private final QuizQuestionRepository quizQuestionRepository;
    private final UserRepository userRepository;

    public QuizAttempt submitAttempt(
            Long quizId,
            Long studentId,
            Map<Long, CorrectOptions> answers) {

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Quiz not found with id: " + quizId
                        )
                );

        User student = userRepository.findById(studentId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Quiz not found with id: " + studentId
                        )
                );

        if(!"ROLE_STUDENT".equals(student.getRole().getName())) {

            throw new RuntimeException(
                    "Only students can attempt quizzes"
            );
        }

        List<QuizQuestion> questions =
                quizQuestionRepository
                        .findByQuizIdOrderByQuestionOrderAsc(quizId);

        if (questions.isEmpty()) {
            throw new RuntimeException(
                    "Quiz has no questions"
            );
        }

        BigDecimal totalMarks = BigDecimal.ZERO;
        BigDecimal score = BigDecimal.ZERO;
        for (QuizQuestion question : questions) {

            BigDecimal marks = question.getMarks();

            if (marks == null) {
                marks = BigDecimal.ONE;
            }

            totalMarks = totalMarks.add(marks);

            CorrectOptions selectedOption = answers.get(question.getId());

            if (selectedOption != null && selectedOption == question.getCorrectOptions()) {
                score = score.add(marks);
            }
        }

        BigDecimal percentage = score
                .divide(
                        totalMarks,
                        4,
                        RoundingMode.HALF_UP
                )
                .multiply(BigDecimal.valueOf(100))
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal passingScore =
                quiz.getPassingScore();

        if(passingScore == null) {
            passingScore = BigDecimal.valueOf(50);
        }

        boolean passed =
                percentage.compareTo(passingScore) >= 0;

        QuizAttempt attempt = QuizAttempt.builder()
                .quiz(quiz)
                .student(student)
                .score(score)
                .totalMarks(totalMarks)
                .percentage(percentage)
                .passed(passed)
                .startedAt(LocalDateTime.now())
                .submittedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();

        return quizAttemptRepository.save(attempt);
    }

    public QuizAttempt getAttempt(Long id) {

        return quizAttemptRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Quiz attempt not found with id: " + id
                        )
                );
    }

    public List<QuizAttempt> getStudentAttempts(Long studentId) {
        return quizAttemptRepository.findByStudentId(studentId);
    }

    public List<QuizAttempt> getQuizAttempts(Long quizId) {
        return quizAttemptRepository.findByQuizId(quizId);
    }
}
