package com.lms.lms_backend.service;


import com.lms.lms_backend.entity.Course;
import com.lms.lms_backend.entity.Quiz;
import com.lms.lms_backend.repository.CourseRepository;
import com.lms.lms_backend.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final CourseRepository courseRepository;

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public Quiz getQuizById(Long id){

        return quizRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Quiz not found with id " + id
                        )
                );
    }

    public List<Quiz> getQuizzesByCourseId(Long courseId) {

        if(!courseRepository.existsById(courseId)){
            throw new RuntimeException(
                    "Course not found with id " + courseId
            );
        }
        return quizRepository.findByCourseId(courseId);
    }

    public List<Quiz> getQuizzesByCourseIdAndIsPublished(Long courseId, Boolean isPublished) {

        if (!courseRepository.existsById(courseId)) {
            throw new RuntimeException(
                    "Course not found with id " + courseId
            );
        }

        if (isPublished == false) {
            throw new RuntimeException(
                    "This quiz is not published"
            );
        }

        return quizRepository.findByCourseIdAndIsPublished(courseId, isPublished);

    }

    public Quiz createdQuiz(Long courseId, Quiz quiz){

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException(
                        "Course not found with id " + courseId
                )
                );
        quiz.setCourse(course);

        return quizRepository.save(quiz);
    }

    public Quiz updatedQuiz(Long id, Quiz updatedQuiz){

        Quiz existingQuiz = getQuizById(id);

        existingQuiz.setTitle(updatedQuiz.getTitle());
        existingQuiz.setDescription(updatedQuiz.getDescription());
        existingQuiz.setPassingScore(updatedQuiz.getPassingScore());
        existingQuiz.setTimeLimitMinutes(updatedQuiz.getTimeLimitMinutes());
        existingQuiz.setIsPublished(updatedQuiz.getIsPublished());

        return quizRepository.save(existingQuiz);
    }

    public void deleteQuiz(Long id){

        Quiz quiz = getQuizById(id);

        quizRepository.delete(quiz);
    }

}
