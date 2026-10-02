package com.lms.lms_backend.controller;

import com.lms.lms_backend.entity.CourseModule;
import com.lms.lms_backend.service.CourseModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CourseModuleController {

    private final CourseModuleService courseModuleService;

    @GetMapping("/courses/{courseId}/modules")
    public ResponseEntity<List<CourseModule>> getModulesByCourse(
            @PathVariable("courseId") Long courseId) {
        return ResponseEntity.ok(courseModuleService.getModulesByCourse(courseId)
        );
    }

    @GetMapping("/modules/{id}")
    public ResponseEntity<CourseModule> getModuleById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                courseModuleService.getModuleById(id)
        );
    }

    @PostMapping("/courses/{courseId}/modules")
    public ResponseEntity<CourseModule> createModule(
            @PathVariable Long courseId,
            @RequestBody CourseModule module) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        courseModuleService.createModule(
                                courseId, module)
                );
    }

    @PutMapping("/modules/{id}")
    public ResponseEntity<CourseModule> updateModule(
            @PathVariable Long id,
            @RequestBody CourseModule module
    ) {
        return ResponseEntity.ok(
                courseModuleService.updateModule(id, module)
        );
    }

    @DeleteMapping("/modules/{id}")
    public ResponseEntity<Void> deleteModule(
            @PathVariable Long id) {

        courseModuleService.deleteModule(id);

        return ResponseEntity.noContent().build();
    }
}