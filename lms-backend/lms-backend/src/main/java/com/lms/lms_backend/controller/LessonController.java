package com.lms.lms_backend.controller;

import com.lms.lms_backend.entity.Lesson;
import com.lms.lms_backend.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @GetMapping("/modules/{moduleId}/lessons")
    public ResponseEntity<List<Lesson>> getLessonsByModule(
            @PathVariable Long moduleId) {
        return ResponseEntity.ok(
                lessonService.getLessonByModule(moduleId)
        );
    }

    @GetMapping("/lessons/{id}")
    public ResponseEntity<Lesson> getLessonById(@PathVariable Long id) {

        return ResponseEntity.ok(
                lessonService.getLessonById(id)
        );
    }

    @PostMapping("modules/{moduleId}/lessons")
    public ResponseEntity<Lesson> createLesson(
            @PathVariable Long moduleId,
            @RequestBody Lesson lesson) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        lessonService.createLesson(
                                moduleId, lesson
                        )
                );
    }

    @PutMapping("lessons/{id}")
    public ResponseEntity<Lesson> updateLesson(
            @PathVariable Long id,
            @RequestBody Lesson lesson) {

        return ResponseEntity.ok(
                lessonService.updateLesson(id, lesson)
        );
    }

    @DeleteMapping("/lessons/{id}")
    public ResponseEntity<Void> deleteLesson(
            @PathVariable Long id) {

        lessonService.deleteLesson(id);

        return ResponseEntity.noContent().build();
    }

}
