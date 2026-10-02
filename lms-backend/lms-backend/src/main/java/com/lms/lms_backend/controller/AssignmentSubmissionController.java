package com.lms.lms_backend.controller;


import com.lms.lms_backend.entity.AssignmentSubmission;
import com.lms.lms_backend.service.AssignmentSubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/assignment-submissions")
@RequiredArgsConstructor
public class AssignmentSubmissionController {

    private final AssignmentSubmissionService assignmentSubmissionService;

    @PostMapping("/submit")
    public ResponseEntity<AssignmentSubmission> submitAssignment(
            @RequestParam Long assignmentId,
            @RequestParam Long studentId,
            @RequestBody AssignmentSubmission submission
    ) {

        return ResponseEntity.ok(
                assignmentSubmissionService.submitAssignment(assignmentId, studentId, submission)

        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssignmentSubmission> getSubmission(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                assignmentSubmissionService.getSubmissionById(id)
        );
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<AssignmentSubmission>>
    getStudentSubmissions(
            @PathVariable Long studentId
    ) {

        return ResponseEntity.ok(
                assignmentSubmissionService.getStudentSubmissions(studentId)
        );
    }

    @GetMapping("/assignment/{assignmentId}")
    public ResponseEntity<List<AssignmentSubmission>>
    getAssignmentSubmissions(
            @PathVariable Long assignmentId
    ) {

        return ResponseEntity.ok(
                assignmentSubmissionService
                        .getAssignmentSubmissions(assignmentId)
        );
    }

    @PutMapping("/{id}/evaluate")
    public ResponseEntity<AssignmentSubmission>
    evaluateAssignment(
            @PathVariable Long id,
            @RequestParam BigDecimal marks,
            @RequestParam(required = false) String feedback
            ) {

        return ResponseEntity.ok(
                assignmentSubmissionService.evaluateSubmission(
                        id, marks, feedback
                )
        );
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<AssignmentSubmission>
    returnSubmission(
            @PathVariable Long id,
            @RequestParam(required = false) String feedback
    ) {

        return ResponseEntity.ok(
                assignmentSubmissionService.returnSubmission(id, feedback)
        );
    }
}
