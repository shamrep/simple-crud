package org.simplecrud.service.validator;

import org.simplecrud.service.model.Answer;
import org.simplecrud.service.model.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionValidatorImpl implements QuestionValidator {

    @Override
    public List<String> validate(Question question) {
        List<String> errors = new ArrayList<>();

        validateContent(errors, question.getContent());
        validateAnswers(errors, question.getAnswers());

        return errors;
    }

    private void validateContent(List<String> errors, String content) {
        if (content == null || content.isBlank()) {
            errors.add("Question content is blank.");
        }
    }

    private void validateAnswers(List<String> errors, List<Answer> answers) {
        if (answers == null || answers.isEmpty()) {
            errors.add("There are no answers.");
            return;
        }

        boolean correctAnswerNotExists = answers.stream().noneMatch(Answer::isCorrect);
        if (correctAnswerNotExists) {
            errors.add("There is no correct answer.");
        }
    }
}
