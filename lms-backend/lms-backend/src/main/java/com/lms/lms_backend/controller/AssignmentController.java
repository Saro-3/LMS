package com.lms.lms_backend.controller;

import com.lms.lms_backend.entity.Assignment;
import com.lms.lms_backend.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;

    @GetMapping
    public ResponseEntity<List<Assignment>> getAllAssignments() {

        return ResponseEntity.ok(
                assignmentService.getAllAssignments()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Assignment> getAssignmentById(@PathVariable Long id) {

        return ResponseEntity.ok(
                assignmentService.getAssignmentById(id)
        );
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Assignment>> getAssignmentsByCourseId(@PathVariable Long courseId) {

        return ResponseEntity.ok(
                assignmentService.getAssignmentsByCourseId(courseId)
        );
    }

    @PostMapping("/course/{courseId}")
    public ResponseEntity<Assignment> createAssignment(@PathVariable Long courseId, @RequestBody Assignment assignment) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        assignmentService.createAssignment(courseId, assignment)
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Assignment> updateAssignment(@PathVariable Long id, @RequestBody Assignment assignment) {

        return ResponseEntity.ok(
                assignmentService.updateAssignment(id, assignment)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        assignmentService.deleteAssignment(id);
        return ResponseEntity.noContent().build();
    }
}
