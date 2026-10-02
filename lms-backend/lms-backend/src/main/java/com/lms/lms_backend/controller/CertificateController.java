package com.lms.lms_backend.controller;

import com.lms.lms_backend.entity.Certificate;
import com.lms.lms_backend.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certificates")
@RequiredArgsConstructor
public class CertificateController {

    private final CertificateService certificateService;

    @PostMapping("/generate/{enrollmentId}")
    public ResponseEntity<Certificate>
    generateCertificate(
            @PathVariable Long enrollmentId) {

        return
                ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(
                                certificateService
                                        .generateCertificate(enrollmentId)
                        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Certificate>
    getCertificate(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                certificateService
                        .getCertificateById(id)
        );
    }

    @GetMapping("/verify/{certificateId}")
    public ResponseEntity<Certificate>
    verifyCertificate(
            @PathVariable String certificateId) {

        return ResponseEntity.ok(
                certificateService
                        .getByCertificate(certificateId)
        );
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Certificate>>
    getStudentCertificate(
            @PathVariable Long studentId) {

        return ResponseEntity.ok(
                certificateService
                        .getStudentCertificates(studentId)
        );
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Certificate>>
    getCourseCertificate(
            @PathVariable Long courseId) {

        return
                ResponseEntity.ok(
                        certificateService
                                .getCourseCertificates(courseId)
                );
    }
}
