package com.lms.lms_backend.controller;

import com.lms.lms_backend.entity.Course;
import com.lms.lms_backend.entity.CourseStatus;
import com.lms.lms_backend.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses(){
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id){
        return ResponseEntity.ok(
                courseService.getCourseById(id)
        );
    }

    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<List<Course>> getCourseByInstructorId(@PathVariable Long instructorId){
        return ResponseEntity.ok(
                courseService.getCoursesInstructor(instructorId)
        );
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Course>> getCoursesByCategoryId(@PathVariable Long categoryId){
        return ResponseEntity.ok(
                courseService.getCoursesByCategoryId(categoryId)
        );
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Course>> getCoursesByStatus(@PathVariable CourseStatus status){

        return ResponseEntity.ok(
                courseService.getCoursesByStatus(status)
        );
    }

    @GetMapping("/instructor&status/{instructorId}/{status}")
    public ResponseEntity<List<Course>> getCoursesByInstructorAndStatus(@PathVariable Long instructorId, @PathVariable CourseStatus status){
        return ResponseEntity.ok(
                courseService.getCoursesByInstructorAndStatus(instructorId, status)
        );
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course, @RequestParam Long instructorId){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(courseService.createCourse(course, instructorId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(
            @PathVariable Long id, @RequestBody Course course){
        return ResponseEntity.ok(courseService.updateCourse(id, course)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Course> deleteCourse(@PathVariable Long id){

        courseService.deleteCourse(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/submit")
    public ResponseEntity<Course> submitForApproval(
            @PathVariable Long id) {
        return ResponseEntity.ok(courseService.submitForApproval(id)
        );
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<Course> approveCourse(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                courseService.approveCourse(id)
        );
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<Course> rejectCourse(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                courseService.rejectCourse(id)
        );
    }

}
