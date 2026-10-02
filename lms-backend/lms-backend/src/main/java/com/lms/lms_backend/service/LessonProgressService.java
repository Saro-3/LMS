package com.lms.lms_backend.service;

import com.lms.lms_backend.entity.Enrollment;
import com.lms.lms_backend.entity.Lesson;
import com.lms.lms_backend.entity.LessonProgress;
import com.lms.lms_backend.repository.EnrollmentRepository;
import com.lms.lms_backend.repository.LessonProgressRepository;
import com.lms.lms_backend.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonProgressService {

    private final LessonProgressRepository lessonProgressRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final LessonRepository lessonRepository;

    public LessonProgress startLesson(
            Long enrollmentId,
            Long lessonId) {

        Enrollment enrollment = enrollmentRepository
                .findById(enrollmentId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Enrollment with id " + enrollmentId + " not found."
                        )
                );
        Lesson lesson = lessonRepository
                .findById(lessonId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Lesson not found with id " + lessonId + " and enrollment with id " + enrollmentId
                        )
                );

        LessonProgress progress =
                lessonProgressRepository
                        .findByEnrollmentIdAndLessonId(
                                enrollmentId,
                                lessonId
                        )
                        .orElseGet(() ->
                                LessonProgress.builder()
                                        .enrollment(enrollment)
                                        .lesson(lesson)
                                        .isCompleted(false)
                                        .createdAt(LocalDateTime.now())
                                        .build()

                        );

        LocalDateTime now = LocalDateTime.now();

        if(progress.getStartedAt() == null) {
            progress.setStartedAt(now);
        }

        progress.setLastAccessedAt(now);

        return lessonProgressRepository.save(progress);
    }

    public LessonProgress completedLesson(
            Long enrollmentId,
            Long lessonId) {

        LessonProgress progress =
                startLesson(enrollmentId, lessonId);

        LocalDateTime now = LocalDateTime.now();

        progress.setCompleted(true);
        progress.setCompletedAt(now);
        progress.setLastAccessedAt(now);

        return lessonProgressRepository.save(progress);
    }

    public LessonProgress getProgressById(Long id){

        return lessonProgressRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Lesson progress not found with id: " + id
                        )
                );
    }

    public List<LessonProgress> getEnrollmentProgress(
            Long enrollmentId) {

        if(!enrollmentRepository.existsById(enrollmentId)) {
            throw new RuntimeException(
                    "Enrollment not found with id: " + enrollmentId
            );
        }

        return lessonProgressRepository
                .findByEnrollmentId(enrollmentId);
    }

    public double getCourseProgressPercentage(
            Long enrollmentId,
            Long lessonId) {

        Enrollment enrollment =
                enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() ->
                                new RuntimeException(
                                        "Enrollment not found with id: " + enrollmentId
                                )
                );

        Long courseId = enrollment.getCourse().getId();

        long totalLessons =
                lessonRepository
                        .countByModuleCourseId(courseId);

        if(totalLessons == 0) {
            return 0.0;
        }

        long completedLessons =
                lessonProgressRepository
                        .countByEnrollmentIdAndIsCompletedTrue(
                                enrollmentId
                        );

        return Math.round(
                ((double) completedLessons / totalLessons) *10000
        ) / 100.0;
    }
}
