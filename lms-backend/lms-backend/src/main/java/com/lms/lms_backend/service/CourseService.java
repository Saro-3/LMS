package com.lms.lms_backend.service;

import com.lms.lms_backend.entity.Course;
import com.lms.lms_backend.entity.CourseStatus;
import com.lms.lms_backend.entity.User;
import com.lms.lms_backend.repository.CourseRepository;
import com.lms.lms_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id){
        return courseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Course not found")
                );
    }

    public List<Course> getCoursesInstructor(Long instructorId){
        return courseRepository.findByInstructorId(instructorId);
    }

    public List<Course> getCoursesByCategoryId(Long categoryId){
        return courseRepository.findByCategoryId(categoryId);
    }

    public List<Course> getCoursesByStatus(CourseStatus status){
        return courseRepository.findByStatus(status);
    }

    public List<Course> getCoursesByInstructorAndStatus(Long instructorId, CourseStatus status){
        return courseRepository.findByInstructorIdAndStatus(instructorId, status);
    }

    public Course createCourse(
            Course course,
            Long instructorId
    ) {
        User instructor = userRepository.findById(instructorId)
                .orElseThrow(() -> new RuntimeException(
                        "Instructor not found"
                )
                );
        course.setInstructor(instructor);

        course.setStatus(CourseStatus.DRAFT);

        return courseRepository.save(course);
    }

    public Course updateCourse(
            Long id,
            Course updateCourse) {

        Course existingCourse = getCourseById(id);

        existingCourse.setTitle(updateCourse.getTitle());
        existingCourse.setDescription(updateCourse.getDescription());
        existingCourse.setThumbnailUrl(updateCourse.getThumbnailUrl());
        existingCourse.setCategory(updateCourse.getCategory());
        existingCourse.setLevel(updateCourse.getLevel());
        existingCourse.setDurationHours(updateCourse.getDurationHours()
        );
        return courseRepository.save(existingCourse);
    }

    public void deleteCourse(Long id) {

        Course course = getCourseById(id);

        courseRepository.delete(course);
    }

    public Course submitForApproval(Long id) {

        Course course = getCourseById(id);

        if (course.getStatus() != CourseStatus.DRAFT) {
            throw new RuntimeException(
                    "only DRAFT course can be submitted approval"
            );
        }

        course.setStatus(CourseStatus.PENDING_APPROVAL);

        return courseRepository.save(course);
    }

    public Course approveCourse(Long id) {
        Course course = getCourseById(id);

        if (course.getStatus() != CourseStatus.PENDING_APPROVAL) {
            throw new RuntimeException(
                    "only PENDING_APPROVAL course can be approved"
            );
        }

        course.setStatus(CourseStatus.PUBLISHED);

        return courseRepository.save(course);
    }

    public Course rejectCourse(Long id) {
        Course course = getCourseById(id);
        if (course.getStatus() != CourseStatus.PENDING_APPROVAL) {
            throw new RuntimeException(
                    "only PENDING_APPROVAL course can be rejected"
            );
        }
            course.setStatus(CourseStatus.REJECTED);

            return courseRepository.save(course);
    }

}
