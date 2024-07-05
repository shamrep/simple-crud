package org.simplecrud.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.simplecrud.repository.entity.QuestionEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class QuestionDaoImplTest {
    private Dao<QuestionEntity> questionEntityDao;

    @BeforeEach
    void setUp() {
        questionEntityDao = new QuestionDaoImpl(DataSourceManager.getDataSource());
    }

    @Test
    void testGetUser() {
        QuestionEntity questionEntity = new QuestionEntity(1, "What is a correct syntax to output \"Hello World\" in Java?");
        questionEntityDao.save(questionEntity);

        Optional<QuestionEntity> optionalQuestion = questionEntityDao.get(1L);
        QuestionEntity selectedQuestionEntity = optionalQuestion.get();

        assertFalse(optionalQuestion.isEmpty());

        assertEquals(1, selectedQuestionEntity.getId());
        assertEquals(questionEntity.getContent(), selectedQuestionEntity.getContent());
    }
}
