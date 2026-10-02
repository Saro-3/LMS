package com.lms.lms_backend.service;

import com.lms.lms_backend.entity.Quiz;
import com.lms.lms_backend.entity.QuizQuestion;
import com.lms.lms_backend.repository.QuizQuestionRepository;
import com.lms.lms_backend.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizQuestionService {

    private final QuizQuestionRepository quizQuestionRepository;
    private final QuizRepository quizRepository;

    public List<QuizQuestion> getQuestionByQuizId(Long quizId) {

        if (!quizRepository.existsById(quizId)) {
            throw new RuntimeException(
                    "Quiz not found with id: "  + quizId
            );
        }

        return quizQuestionRepository
                .findByQuizIdOrderByQuestionOrderAsc(quizId);
    }

    public QuizQuestion getQuestionById(Long id) {

        return quizQuestionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Question not found with id: " + id
                        )
                );
    }

    public QuizQuestion createQuestion(Long quizId, QuizQuestion question) {

        Quiz quiz  = quizRepository.findById(quizId)
                .orElseThrow(() ->
                    new RuntimeException(
                            "Quiz not found with id: " + quizId
                    )
        );
        question.setQuiz(quiz);

        return quizQuestionRepository.save(question);
    }

    public QuizQuestion updateQuestion(Long id, QuizQuestion updateQuestion) {

        QuizQuestion existingQuestion = getQuestionById(id);

        existingQuestion.setQuestionText(updateQuestion.getQuestionText());
        existingQuestion.setOptionA(updateQuestion.getOptionA());
        existingQuestion.setOptionB(updateQuestion.getOptionB());
        existingQuestion.setOptionC(updateQuestion.getOptionC());
        existingQuestion.setOptionD(updateQuestion.getOptionD());
        existingQuestion.setCorrectOptions(updateQuestion.getCorrectOptions());
        existingQuestion.setMarks(updateQuestion.getMarks());
        existingQuestion.setQuestionOrder(updateQuestion.getQuestionOrder());

        return quizQuestionRepository.save(existingQuestion);
    }

    public void deleteQuestion(Long id) {

        QuizQuestion question = getQuestionById(id);

        quizQuestionRepository.delete(question);
    }
}
