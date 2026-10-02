package com.lms.lms_backend.controller;

import com.lms.lms_backend.entity.Quiz;
import com.lms.lms_backend.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @GetMapping
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        return ResponseEntity.ok(
                quizService.getAllQuizzes()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable Long id) {

        return ResponseEntity.ok(quizService.getQuizById(id));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Quiz>> getAllQuizzesByCourseId(@PathVariable Long courseId) {

        return ResponseEntity.ok(
                quizService.getQuizzesByCourseId(courseId)
        );
    }

    @GetMapping("/course/{courseId}/isPublished")
    public ResponseEntity<List<Quiz>> getAllQuizzesByCourseIdAndIsPublished(@PathVariable Long courseId, Boolean isPublished) {

        return ResponseEntity.ok(
                quizService.getQuizzesByCourseIdAndIsPublished(courseId, isPublished)
        );
    }

    @PostMapping("/course/{courseId}")
    public ResponseEntity<Quiz> createQuiz(@PathVariable Long courseId, @RequestBody Quiz quiz) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        quizService.createdQuiz(courseId, quiz)
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable Long id, @RequestBody Quiz quiz) {

        return ResponseEntity.ok(
                quizService.updatedQuiz(id, quiz)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long id) {

        quizService.deleteQuiz(id);
        return ResponseEntity.noContent().build();
    }

}
