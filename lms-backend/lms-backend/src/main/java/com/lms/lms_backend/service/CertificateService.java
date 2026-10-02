package com.lms.lms_backend.service;

import com.lms.lms_backend.entity.Certificate;
import com.lms.lms_backend.entity.Enrollment;
import com.lms.lms_backend.entity.EnrollmentStatus;
import com.lms.lms_backend.repository.CertificateRepository;
import com.lms.lms_backend.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CertificateService {

    private final CertificateRepository certificateRepository;
    private final EnrollmentRepository enrollmentRepository;

    public Certificate generateCertificate(
            Long enrollmentId) {

        Enrollment enrollment =
                enrollmentRepository.findById(enrollmentId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Enrollment not found with id: "
                                        + enrollmentId
                                )
                        );

        if(enrollment.getStatus() !=
                EnrollmentStatus.COMPLETED) {

            throw new RuntimeException(
                    "Certificate can only be generated  "
                    + "after course completion"
            );
        }

        if(certificateRepository
                .existsByEnrollmentId(enrollmentId)) {
            throw new RuntimeException(
                    "Certificate already exists for this enrollment"
            );
        }

        String certificateId =
                "LMS-" +
                        UUID.randomUUID()
                                .toString()
                                .substring(0, 8)
                                .toUpperCase();

        Certificate certificate =
                Certificate.builder()
                        .certificateId(certificateId)
                        .student(enrollment.getStudent())
                        .course(enrollment.getCourse())
                        .enrollment(enrollment)
                        .certificateUrl(null)
                        .build();

        return certificateRepository.save(certificate);
    }

    public Certificate getCertificateById(
            Long id) {

        return certificateRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Certificate not found with id: "
                                + id
                        )
                );
    }

    public Certificate getByCertificate(
            String certificateId) {

        return certificateRepository
                .findByCertificateId(certificateId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "certificate not found with id: "
                                + certificateId
                        )
                );
    }

    public List<Certificate> getStudentCertificates(
            Long studentId
    ) {

        return certificateRepository
                .findByStudentId(studentId);
    }

    public List<Certificate> getCourseCertificates(
            Long courseId
    ) {

        return certificateRepository
                .findByCourseId(courseId);
    }

}
