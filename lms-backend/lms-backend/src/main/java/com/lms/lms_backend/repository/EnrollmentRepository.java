package com.lms.lms_backend.repository;

import com.lms.lms_backend.entity.Enrollment;
import com.lms.lms_backend.entity.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    boolean existsByStudentIdAndCourseId(
            Long studentId, Long courseId
    );

    Optional<Enrollment>  findByStudentIdAndCourseId(
            Long studentId, Long courseId
    );

    List<Enrollment> findByStudentId(Long studentId);

    List<Enrollment> findByCourseId(Long courseId);

    List<Enrollment> findByStudentIdAndStatus(
            Long studentId, EnrollmentStatus status
    );
}
