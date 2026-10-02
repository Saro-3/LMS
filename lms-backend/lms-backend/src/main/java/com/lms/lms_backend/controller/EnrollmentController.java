package com.lms.lms_backend.controller;


import com.lms.lms_backend.entity.Enrollment;
import com.lms.lms_backend.service.EnrollmentService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<Enrollment> enrollStudent(
            @RequestParam Long studentId,
            @RequestParam Long courseId) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        enrollmentService.enrollStudent(
                                studentId, courseId
                        )
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Enrollment> getEnrollmentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                enrollmentService.getEnrollmentById(id)
        );
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Enrollment>> getStudentEnrollments(
            @PathVariable Long studentId) {

        return ResponseEntity.ok(
                enrollmentService.getStudentEnrollments(
                        studentId)
        );
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Enrollment>> getCourseEnrollments(
            @PathVariable Long courseId) {

        return ResponseEntity.ok(
                enrollmentService.getCourseEnrollments(
                        courseId)
        );
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Enrollment> completeEnrollment(
            @PathVariable Long id,
            @RequestParam Long studentId,
            @RequestParam Long courseId) {

        return ResponseEntity.ok(
                enrollmentService.completeEnrollment(id)
        );
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Enrollment> cancelEnrollment(
            @PathVariable Long id,
            @RequestParam Long studentId,
            @RequestParam Long courseId) {

        return ResponseEntity.ok(
                enrollmentService.cancelEnrollment(id)
        );
    }

}
