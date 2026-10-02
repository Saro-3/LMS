package com.lms.lms_backend.repository;

import com.lms.lms_backend.entity.Course;
import com.lms.lms_backend.entity.CourseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByInstructorId(Long instructorId);

    List<Course> findByCategoryId(Long categoryId);

    List<Course> findByStatus(CourseStatus status);

    List<Course> findByInstructorIdAndStatus(Long instructorId, CourseStatus status);

}
