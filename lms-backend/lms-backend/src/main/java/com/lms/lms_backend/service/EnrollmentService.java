package com.lms.lms_backend.service;

import com.lms.lms_backend.entity.*;
import com.lms.lms_backend.repository.CourseRepository;
import com.lms.lms_backend.repository.EnrollmentRepository;
import com.lms.lms_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public Enrollment enrollStudent(
            Long studentId,
            Long courseId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student not found with id: " + studentId
                        )
                );

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Course not found with id: " + courseId
                        )
                );

        if (!"ROLE_STUDENT".equals(student.getRole().getName())) {
            throw new RuntimeException(
                    "Only students can enroll in courses"
            );
        }

        if(course.getStatus() != CourseStatus.PUBLISHED){
            throw new RuntimeException(
                    "Only published courses can be enrolled"
            );
        }

        if (enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            throw new RuntimeException(
                    "Student is already enrolled in this course"
            );
        }

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .status(EnrollmentStatus.ACTIVE)
                .enrolledAt(LocalDateTime.now())
                .build();

        return enrollmentRepository.save(enrollment);
    }

    public Enrollment getEnrollmentById(Long id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Enrollment not found with id: " + id
                        )
                );
    }

    public List<Enrollment> getStudentEnrollments(Long studentId) {

        if (!userRepository.existsById(studentId)) {
            throw new RuntimeException(
                    "Student not found with id: " + studentId
            );
        }

        return enrollmentRepository.findByStudentId(studentId);
    }

    public List<Enrollment> getCourseEnrollments(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new RuntimeException(
                    "Course not found with id: " + courseId
            );
        }
        return enrollmentRepository.findByCourseId(courseId);
    }

    public Enrollment completeEnrollment(Long id) {
        Enrollment enrollment = getEnrollmentById(id);

        enrollment.setStatus(EnrollmentStatus.COMPLETED);
        enrollment.setCompletedAt(LocalDateTime.now());

        return enrollmentRepository.save(enrollment);
    }

    public Enrollment cancelEnrollment(Long id){
        Enrollment enrollment = getEnrollmentById(id);
        enrollment.setStatus(EnrollmentStatus.CANCELED);
        return
                enrollmentRepository.save(enrollment);
    }
}
