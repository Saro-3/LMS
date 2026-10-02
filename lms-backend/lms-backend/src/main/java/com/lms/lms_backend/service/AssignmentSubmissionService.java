package com.lms.lms_backend.service;

import com.lms.lms_backend.entity.Assignment;
import com.lms.lms_backend.entity.AssignmentStatus;
import com.lms.lms_backend.entity.AssignmentSubmission;
import com.lms.lms_backend.entity.User;
import com.lms.lms_backend.repository.AssignmentRepository;
import com.lms.lms_backend.repository.AssignmentSubmissionRepository;
import com.lms.lms_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentSubmissionService {

    private final AssignmentSubmissionRepository assignmentSubmissionRepository;
    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;

    public AssignmentSubmission submitAssignment(
            Long assignmentId,
            Long studentId,
            AssignmentSubmission submission) {

        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException(
                        "Assignment not found with id " + assignmentId)
                );

        User student =
                userRepository.findById(studentId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Student not found with id " + studentId
                                )
                        );

        if (!"ROLE_STUDENT".equals(
                student.getRole().getName()
        )) {
            throw new RuntimeException(
                    "Only students can submit assignments"
            );
        }

        submission.setAssignment(assignment);
        submission.setStudent(student);
        submission.setStatus(
                AssignmentStatus.SUBMITTED
        );
        submission.setSubmittedAt(
                LocalDateTime.now()
        );

        return assignmentSubmissionRepository.save(submission);
    }

    public AssignmentSubmission getSubmissionById(
            Long id
    ) {

        return assignmentSubmissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Submission not found with id " + id
                ));
    }

    public List<AssignmentSubmission> getStudentSubmissions(
            Long studentId
    ) {

        return assignmentSubmissionRepository.findByStudentId(studentId);
    }

    public List<AssignmentSubmission> getAssignmentSubmissions(
            Long assignmentId
    ) {

        return assignmentSubmissionRepository.findByAssignmentId(assignmentId);
    }

    public AssignmentSubmission evaluateSubmission(
            Long submissionId,
            BigDecimal marks,
            String feedback
    ) {

        AssignmentSubmission submission = getSubmissionById(submissionId);

        BigDecimal maxMarks =
                submission.getAssignment().getMaxMarks();

        if (marks == null || marks.compareTo(BigDecimal.ZERO) < 0 ||
                                marks.compareTo(maxMarks) > 0) {
            throw new RuntimeException(
                    "Marks must be between 0 and  " + maxMarks
            );
        }

        submission.setMarks(marks);
        submission.setInstructorFeedback(feedback);
        submission.setStatus(AssignmentStatus.EVALUATED);
        submission.setEvaluatedAt(LocalDateTime.now());

        return assignmentSubmissionRepository.save(submission);
    }

    public AssignmentSubmission returnSubmission(
            Long submissionId,
            String feedback
    ) {

        AssignmentSubmission submission = getSubmissionById(submissionId);

        submission.setInstructorFeedback(feedback);
        submission.setStatus(AssignmentStatus.RETURNED);
        submission.setEvaluatedAt(LocalDateTime.now());
        return assignmentSubmissionRepository.save(submission);
    }
}
