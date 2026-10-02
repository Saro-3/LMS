package com.lms.lms_backend.service;

import com.lms.lms_backend.entity.CourseModule;
import com.lms.lms_backend.entity.Lesson;
import com.lms.lms_backend.repository.CourseModuleRepository;
import com.lms.lms_backend.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final CourseModuleRepository courseModuleRepository;

    public List<Lesson> getLessonByModule(Long moduleId){

        if (!courseModuleRepository.existsById(moduleId)) {

            throw new RuntimeException(
                    "Course module with id " + moduleId + " does not exist"
            );
        }
        return lessonRepository
                .findByModuleIdOrderByLessonOrderAsc(moduleId);
    }

    public Lesson getLessonById(Long id) {

        return lessonRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Lesson with id " + id + " does not exist"
                        )
                );
    }

    public Lesson createLesson(
            Long moduleId,
            Lesson lesson) {

        CourseModule module = courseModuleRepository.findById(moduleId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Course module with id " + moduleId + " does not exist"
                        )
                );
        lesson.setModule(module);
        return lessonRepository.save(lesson);
    }

    public Lesson updateLesson(
            Long id,
            Lesson updatedLesson) {

        Lesson existingLesson = getLessonById(id);

        existingLesson.setTitle(updatedLesson.getTitle());
        existingLesson.setDescription(updatedLesson.getDescription());
        existingLesson.setLessonType(updatedLesson.getLessonType());
        existingLesson.setLessonOrder(updatedLesson.getLessonOrder());
        existingLesson.setDurationMinutes(updatedLesson.getDurationMinutes());
        existingLesson.setIsPreview(updatedLesson.getIsPreview());

        return lessonRepository.save(existingLesson);
    }

    public void deleteLesson(Long id) {

        Lesson lesson = getLessonById(id);

        lessonRepository.delete(lesson);
    }
}
