package com.lms.lms_backend.controller;


import com.lms.lms_backend.entity.QuizQuestion;
import com.lms.lms_backend.service.QuizQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class QuizQuestionController {

    private final QuizQuestionService quizQuestionService;

    @GetMapping("/quizzes/{quizId}/questions")
    public ResponseEntity<List<QuizQuestion>> getQuestions(@PathVariable("quizId") Long quizId) {

        return ResponseEntity.ok(
                quizQuestionService.getQuestionByQuizId(quizId)
        );
    }

    @GetMapping("/questions/{id}")
    public ResponseEntity<QuizQuestion> getQuestion(@PathVariable("id") Long id) {

        return ResponseEntity.ok(quizQuestionService.getQuestionById(id));
    }

    @PostMapping("/quizzes/{quizId}/questions")
    public ResponseEntity<QuizQuestion> createQuestion(
            @PathVariable Long quizId,
            @RequestBody QuizQuestion quizQuestion) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        quizQuestionService.createQuestion(quizId, quizQuestion));
    }

    @PutMapping("/questions/{id}")
    public ResponseEntity<QuizQuestion> updateQuestion(
            @PathVariable Long id,
            @RequestBody QuizQuestion quizQuestion) {

        return ResponseEntity.ok(
                quizQuestionService.updateQuestion(id, quizQuestion)
        );
    }

    @DeleteMapping("/questions/{id}")
    public ResponseEntity<Void> deleteQuestion(
            @PathVariable Long id
    ) {

        quizQuestionService.deleteQuestion(id);

        return ResponseEntity.noContent().build();
    }
}
