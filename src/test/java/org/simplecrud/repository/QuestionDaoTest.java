package org.simplecrud.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.simplecrud.entity.Question;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class QuestionDaoTest {
    private Dao<Question> questionDao;

    @BeforeEach
    void setUp() {
        questionDao = new QuestionDaoImpl(DataSourceManager.getDataSource());
    }

    @Test
    void testGetUser() {
        Question question = new Question(1, "What is a correct syntax to output \"Hello World\" in Java?");
        questionDao.save(question);

        Optional<Question> optionalQuestion = questionDao.get(1L);
        Question selectedQuestion = optionalQuestion.get();

        assertFalse(optionalQuestion.isEmpty());

        assertEquals(1, selectedQuestion.getId());
        assertEquals(question.getText(), selectedQuestion.getText());
    }
}
