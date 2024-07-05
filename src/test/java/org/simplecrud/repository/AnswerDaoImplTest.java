package org.simplecrud.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.simplecrud.repository.entity.AnswerEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnswerDaoImplTest {
    private Dao<AnswerEntity> dao;

    @BeforeEach
    void setUp() {
        dao = new AnswerDaoImpl();
    }

    @Test
    void findAnswerById_answerId_answer() {
        // given
        AnswerEntity entity = new AnswerEntity(1, "Console.WriteLine(\"Hello World\");", false);

        // when
        Optional<AnswerEntity> optional = dao.get(1);

        //then
        assertEquals(optional.get(), entity);
    }
}
