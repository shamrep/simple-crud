package org.simplecrud.repository.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.simplecrud.extension.DatabaseExtension;
import org.simplecrud.repository.entity.QuestionEntity;
import org.simplecrud.repository.exception.DaoException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class QuestionDaoImplTest {

    @RegisterExtension
    private static final DatabaseExtension DB = new DatabaseExtension("question");

    private final QuestionDaoImpl questionDao = new QuestionDaoImpl(DB::getConnection);

    @Test
    void get_exists_notEmpty() throws Exception {
        // given
        insertRow(1, "test");

        // when
        Optional<QuestionEntity> question = questionDao.get(1);

        // then
        assertThat(question).hasValue(new QuestionEntity(1L, "test"));
    }

    @Test
    void get_notExists_empty() throws Exception {
        // given
        insertRow(1, "test");

        // when
        Optional<QuestionEntity> question = questionDao.get(2);

        // then
        assertThat(question).isEmpty();
    }

    @Test
    void getAll_exists_list() throws Exception {
        // given
        insertRow(1, "test1");
        insertRow(2, "test2");

        // when
        List<QuestionEntity> questions = questionDao.getAll();

        // then
        assertThat(questions)
                .extracting(QuestionEntity::getId)
                .containsOnly(1L, 2L);
    }

    @Test
    void getAll_notExists_emptyList() {
        // given

        // when
        List<QuestionEntity> questions = questionDao.getAll();

        // then
        assertThat(questions).isEmpty();
    }

    @Test
    void create_created() {
        // given

        // when
        questionDao.create(new QuestionEntity(null, "created"));

        // then
        List<QuestionEntity> questions =  questionDao.getAll();
        assertThat(questions)
                .extracting(QuestionEntity::getContent)
                .containsOnly("created");
    }

    @Test
    void update_exists_updated() throws Exception {
        // given
        insertRow(1, "test");

        // when
        questionDao.update(new QuestionEntity(1L, "updated"));

        // then
        Optional<QuestionEntity> question =  questionDao.get(1);
        assertThat(question)
                .map(QuestionEntity::getContent)
                .hasValue("updated");
    }

    @Test
    void update_notExists_DaoException() throws Exception {
        // given
        insertRow(1, "test");

        // when
        Throwable thrown = catchThrowable(() -> questionDao.update(new QuestionEntity(2L, "updated")));

        // then
        assertThat(thrown)
                .isInstanceOf(DaoException.class)
                .hasMessageContaining("Could not update question with id = 2");
    }

    @Test
    void delete_exists_deleted() throws Exception {
        // given
        insertRow(1, "test1");
        insertRow(2, "test12");

        // when
        questionDao.delete(1);

        // then
        List<QuestionEntity> questions =  questionDao.getAll();
        assertThat(questions)
                .extracting(QuestionEntity::getId)
                .containsOnly(2L);
    }

    @Test
    void delete_notExists_DaoException() throws Exception {
        // given
        insertRow(1, "test1");

        // when
        Throwable thrown = catchThrowable(() -> questionDao.delete(2));

        // then
        assertThat(thrown)
                .isInstanceOf(DaoException.class)
                .hasMessageContaining("Could not delete question by id = 2");
    }


    private void insertRow(Integer id, String content) throws Exception {
        String sql = String.format("insert into question(id, content) values (%s, '%s')", id, content);
        DB.execute(sql);
    }
}
