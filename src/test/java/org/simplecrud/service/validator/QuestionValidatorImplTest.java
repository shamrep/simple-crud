package org.simplecrud.service.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.simplecrud.service.model.Answer;
import org.simplecrud.service.model.Question;
import org.simplecrud.service.model.Tag;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class QuestionValidatorImplTest {

    private final QuestionValidatorImpl validator = new QuestionValidatorImpl();

    @ParameterizedTest
    @ValueSource(strings = {"", " \t "})
    @NullSource
    void validate_contentIsBlank_error(String content) {
        // given
        Question question = validQuestion();
        question.setContent(content);

        // when
        List<String> errors = validator.validate(question);

        // then
        assertThat(errors).containsOnly("Question content is blank.");

    }

    @Test
    void validate_thereIsNoCorrectAnswer_error() {
        // given
        Question question = validQuestion();
        question.setAnswers(asList(
                new Answer(1L, "a1", false),
                new Answer(2L, "a2", false)));

        // when
        List<String> errors = validator.validate(question);

        // then
        assertThat(errors).containsOnly("There is no correct answer.");

    }

    @Test
    void validate_questionIsValid_ok() {
        // given
        Question question = validQuestion();

        // when
        List<String> errors = validator.validate(question);

        // then
        assertThat(errors).isEmpty();

    }

    private Question validQuestion() {
        List<Answer> answers = asList(
                new Answer(1L, "a1", false),
                new Answer(2L, "a2", true));

        List<Tag> tags = asList(
                new Tag(1L, "t1"),
                new Tag(2L, "t2"));

        return new Question(1L, "content", answers, tags);
    }
}