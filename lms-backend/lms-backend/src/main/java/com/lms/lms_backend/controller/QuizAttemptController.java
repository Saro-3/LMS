package com.lms.lms_backend.controller;


import com.lms.lms_backend.entity.CorrectOptions;
import com.lms.lms_backend.entity.QuizAttempt;
import com.lms.lms_backend.service.QuizAttemptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/quiz-attempts")
@RequiredArgsConstructor
public class QuizAttemptController {

    private final QuizAttemptService quizAttemptService;

    @PostMapping("/submit")
    public ResponseEntity<QuizAttempt> submitAttempt(
            @RequestParam Long quizId,
            @RequestParam Long studentId,
            @RequestBody Map<Long, CorrectOptions> answers) {

        return ResponseEntity.ok(
                quizAttemptService.submitAttempt(quizId, studentId, answers)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizAttempt> getAttempt(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                quizAttemptService.getAttempt(id)
        );
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<QuizAttempt>> getStudentAttempts(
            @PathVariable Long studentId
    ) {

        return ResponseEntity.ok(
                quizAttemptService.getStudentAttempts(studentId)
        );
    }

    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<List<QuizAttempt>> getQuizAttempts(
            @PathVariable Long quizId
    ) {

        return ResponseEntity.ok(
                quizAttemptService.getQuizAttempts(quizId)
        );
    }
}
