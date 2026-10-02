package com.lms.lms_backend.service;

import com.lms.lms_backend.entity.Course;
import com.lms.lms_backend.entity.CourseModule;
import com.lms.lms_backend.repository.CourseModuleRepository;
import com.lms.lms_backend.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseModuleService {

    private final CourseModuleRepository courseModuleRepository;
    private final CourseRepository courseRepository;

    public List<CourseModule> getModulesByCourse(Long courseId){

        if(!courseRepository.existsById(courseId)){
            throw new RuntimeException(
                    "Course not found with id: " + courseId
            );
        }

        return courseModuleRepository
                .findByCourseIdOrderByModuleOrderAsc(courseId);
    }

    public CourseModule getModuleById(Long id){

        return courseModuleRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                        "Course not found with id: " + id
                ));
    }

    public  CourseModule createModule(
            Long courseId,
            CourseModule module) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Course not found with id: " + courseId
                        )
                );

        module.setCourse(course);

        return courseModuleRepository.save(module);
    }

    public CourseModule updateModule(
            Long id,
            CourseModule updateModule) {

        CourseModule existingModule = getModuleById(id);

        existingModule.setTitle(updateModule.getTitle());
        existingModule.setDescription(updateModule.getDescription());
        existingModule.setModuleOrder(updateModule.getModuleOrder());

        return courseModuleRepository.save(existingModule);
    }

    public void deleteModule(Long id) {
        CourseModule module = getModuleById(id);
        courseModuleRepository.delete(module);
    }
}
