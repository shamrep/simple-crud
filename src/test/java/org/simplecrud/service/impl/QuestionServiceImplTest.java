package org.simplecrud.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.simplecrud.repository.AnswerDao;
import org.simplecrud.repository.QuestionDao;
import org.simplecrud.repository.TagDao;
import org.simplecrud.repository.entity.QuestionEntity;
import org.simplecrud.service.model.Answer;
import org.simplecrud.service.model.Question;
import org.simplecrud.service.model.Tag;
import org.simplecrud.service.validator.QuestionValidator;
import org.simplecrud.service.validator.ValidationException;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceImplTest {

    @Mock
    private QuestionDao questionDao;

    @Mock
    private QuestionValidator questionValidator;

    @Mock
    private AnswerDao answerDao;

    @Mock
    private TagDao tagDao;

    private QuestionServiceImpl questionService;

    @BeforeEach
    void setUp() {
        questionService = new QuestionServiceImpl(questionDao, answerDao, tagDao, questionValidator);
    }

    @Test
    void create_questionIsInvalid_validationException() {
        // given
        Question question = new Question(null, "test", null, null);
        when(questionValidator.validate(question)).thenReturn(singletonList("error_test"));

        // when
        Throwable thrown = catchThrowable(() -> questionService.create(question));

        // then
        assertThat(thrown)
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("error_test");
    }

    @Test
    void create_questionIsValid_delegateToDao() {
        // given
        List<Answer> answers = List.of(new Answer(null, "test1", false), new Answer(null, "test2", true));
        List<Tag> tags = List.of(new Tag(1L, "tag"));
        Question question = new Question(null, "test", answers, tags);
        when(questionValidator.validate(question)).thenReturn(emptyList());
        when(questionDao.create(new QuestionEntity(null, "test"))).thenReturn(123L);

        //when
        long createdQuestionId = questionService.create(question);

        //then
        verify(questionDao).create(new QuestionEntity(null, "test"));
        assertThat(createdQuestionId).isEqualTo(123L);
    }

    @Test
    void update_questionIsInvalid_validationException() {
        // given
        Question question = new Question(1L, "test", null, null);
        when(questionValidator.validate(question)).thenReturn(singletonList("error_test"));

        // when
        Throwable thrown = catchThrowable(() -> questionService.update(question));

        // then
        assertThat(thrown).isInstanceOf(ValidationException.class).hasMessageContaining("error_test");
    }

    @Test
    void update_questionIsValid_delegateToDao() {
        // given
        Question question = new Question(1L, "test", List.of(), List.of());
        when(questionValidator.validate(question)).thenReturn(emptyList());
        doNothing().when(questionDao).update(new QuestionEntity(1L, "test"));

        // when
        questionService.update(question);

        // then
        verify(questionDao).update(new QuestionEntity(1L, "test"));
    }

    @Test
    void get_questionExists_fromDao() {
        // given
        when(questionDao.get(1L)).thenReturn(Optional.of(new QuestionEntity(1L, "test")));

        // when
        Optional<Question> optionalQuestion = questionService.get(1L);

        //then
        assertThat(optionalQuestion.get()).usingRecursiveComparison().isEqualTo(new Question(1L, "test", List.of(), List.of()));
    }

    @Test
    void get_questionsNotExists_empty() {
        // given
        when(questionDao.get(1L)).thenReturn(Optional.empty());

        // when
        Optional<Question> optionalQuestion = questionService.get(1L);

        // then
        assertThat(optionalQuestion).isEmpty();
    }

    @Test
    void delete_questionId_delegateToDao() {
        // given
        doNothing().when(questionDao).delete(1L);

        // when
        questionService.delete(1L);

        // then
        verify(questionDao).delete(1L);
    }
}
