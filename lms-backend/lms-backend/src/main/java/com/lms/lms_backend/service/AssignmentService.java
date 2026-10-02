package com.lms.lms_backend.service;

import com.lms.lms_backend.entity.Assignment;
import com.lms.lms_backend.entity.Course;
import com.lms.lms_backend.repository.AssignmentRepository;
import com.lms.lms_backend.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AssignmentService {

    private AssignmentRepository assignmentRepository;
    private final CourseRepository courseRepository;

    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    public Assignment getAssignmentById(Long id) {

        return assignmentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Assignment not found with id " + id
                        )
                );
    }

    public List<Assignment> getAssignmentsByCourseId(Long courseId) {

        if(!courseRepository.existsById(courseId)) {
            throw new RuntimeException(
                    "Course not found with id " + courseId
            );
        }

        return assignmentRepository.findByCourseId(courseId);
    }

    public Assignment createAssignment(Long courseId,Assignment assignment) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException(
                        "Course not found with id " + courseId
                )
                );
        assignment.setCourse(course);
        return assignmentRepository.save(assignment);
    }

    public Assignment updateAssignment(Long id, Assignment updatedAssignment) {

        Assignment existing = getAssignmentById(id);

        existing.setTitle(updatedAssignment.getTitle());
        existing.setDescription(updatedAssignment.getDescription());
        existing.setMaxMarks(updatedAssignment.getMaxMarks());
        existing.setDueDate(updatedAssignment.getDueDate());

        return assignmentRepository.save(existing);
    }

    public void deleteAssignment(Long id) {

        Assignment assignment = getAssignmentById(id);

        assignmentRepository.delete(assignment);
    }
}
