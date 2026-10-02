package com.lms.lms_backend.controller;

import com.lms.lms_backend.entity.LessonProgress;
import com.lms.lms_backend.service.LessonProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
@RequiredArgsConstructor
public class LessonProgressController {

    private final LessonProgressService lessonProgressService;

    @PostMapping("/start")
    public ResponseEntity<LessonProgress> startLesson(
            @RequestParam Long enrollmentId,
            @RequestParam Long lessonId) {

        return ResponseEntity.ok(lessonProgressService.startLesson(
                enrollmentId, lessonId)
        );
    }

    @PutMapping("/completed")
    public ResponseEntity<LessonProgress> completedLesson(
            @RequestParam Long enrollmentId,
            @RequestParam Long lessonId) {

        return ResponseEntity.ok(
                lessonProgressService.completedLesson(
                        enrollmentId, lessonId
                )
        );

    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonProgress> getProgressById(
            @PathVariable Long id) {

        return ResponseEntity.ok(lessonProgressService.getProgressById(id));
    }

    @GetMapping("/enrollment/{enrollmentId}")
    public ResponseEntity<List<LessonProgress>> getEnrollmentProgress(
            @PathVariable Long enrollmentId) {

        return ResponseEntity.ok(
                lessonProgressService.getEnrollmentProgress(enrollmentId)
        );
    }

    @GetMapping("/enrollment/{enrollmentId}/percentage")
    public ResponseEntity<Double> getCourseProgressPercentage(
            @PathVariable Long enrollmentId,
            @RequestParam Long lessonId) {

        return ResponseEntity.ok(
                lessonProgressService
                        .getCourseProgressPercentage(
                                enrollmentId, lessonId
                        )
        );
    }
}
