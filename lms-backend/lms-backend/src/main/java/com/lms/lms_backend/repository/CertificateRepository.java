package com.lms.lms_backend.repository;

import com.lms.lms_backend.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {

    Optional<Certificate> findByCertificateId(
            String certificateId
    );

    Optional<Certificate> findByEnrollmentId(
            Long enrollmentId
    );

    List<Certificate> findByStudentId(
            Long studentId
    );

    List<Certificate> findByCourseId(
            Long courseId
    );

    boolean existsByEnrollmentId(
            Long enrollmentId
    );
}
