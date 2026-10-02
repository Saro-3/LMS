package com.lms.lms_backend.repository;

import com.lms.lms_backend.entity.AssignmentSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentSubmissionRepository extends JpaRepository<AssignmentSubmission, Long> {

    List<AssignmentSubmission> findByStudentId(Long studentId);

    List<AssignmentSubmission> findByAssignmentId(Long assignmentId);

    List<AssignmentSubmission> findByStudentIdAndAssignmentId(Long studentId, Long assignmentId);
}
