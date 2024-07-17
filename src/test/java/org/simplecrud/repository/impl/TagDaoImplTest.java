package org.simplecrud.repository.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.simplecrud.extension.DatabaseExtension;
import org.simplecrud.repository.entity.TagEntity;
import org.simplecrud.repository.exception.DaoException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class TagDaoImplTest {

    @RegisterExtension
    private static final DatabaseExtension DB = new DatabaseExtension("tag");

    private final TagDaoImpl tagDao = new TagDaoImpl(DB::getConnection);

    @Test
    void get_exists_notEmpty() throws Exception {
        // given
        insertRow(1, "test");

        // when
        Optional<TagEntity> tag = tagDao.get(1);

        // then
        assertThat(tag).hasValue(new TagEntity(1L, "test"));
    }

    @Test
    void get_notExists_empty() throws Exception {
        // given
        insertRow(1, "test");

        // when
        Optional<TagEntity> tag = tagDao.get(2);

        // then
        assertThat(tag).isEmpty();
    }

    @Test
    void getAll_exists_list() throws Exception {
        // given
        insertRow(1, "test1");
        insertRow(2, "test2");

        // when
        List<TagEntity> tags = tagDao.getAll();

        // then
        assertThat(tags)
                .extracting(TagEntity::getId)
                .containsOnly(1L, 2L);
    }

    @Test
    void getAll_notExists_emptyList() {
        // given

        // when
        List<TagEntity> tags = tagDao.getAll();

        // then
        assertThat(tags).isEmpty();
    }

    @Test
    void create_created() {
        // given

        // when
        tagDao.create(new TagEntity(null, "created"));

        // then
        List<TagEntity> tags = tagDao.getAll();
        assertThat(tags)
                .extracting(TagEntity::getName)
                .containsOnly("created");
    }

    @Test
    void update_exists_updated() throws Exception {
        // given
        insertRow(1, "test");

        // when
        tagDao.update(new TagEntity(1L, "updated"));

        // then
        Optional<TagEntity> tag = tagDao.get(1);
        assertThat(tag)
                .map(TagEntity::getName)
                .hasValue("updated");
    }

    @Test
    void update_notExists_DaoException() throws Exception {
        // given
        insertRow(1, "test");

        // when
        Throwable thrown = catchThrowable(() -> tagDao.update(new TagEntity(2L, "updated")));

        // then
        assertThat(thrown)
                .isInstanceOf(DaoException.class)
                .hasMessageContaining("Could not update tag with id = 2");
    }

    @Test
    void delete_exists_deleted() throws Exception {
        // given
        insertRow(1, "test1");
        insertRow(2, "test12");

        // when
        tagDao.delete(1);

        // then
        List<TagEntity> tags = tagDao.getAll();
        assertThat(tags)
                .extracting(TagEntity::getId)
                .containsOnly(2L);
    }

    @Test
    void delete_notExists_DaoException() throws Exception {
        // given
        insertRow(1, "test1");

        // when
        Throwable thrown = catchThrowable(() -> tagDao.delete(2));

        // then
        assertThat(thrown)
                .isInstanceOf(DaoException.class)
                .hasMessageContaining("Couldn't delete tag by id = 2");
    }

    private void insertRow(Integer id, String name) throws Exception {
        String sql = String.format("insert into tag(id, name) values (%s, '%s')", id, name);
        DB.execute(sql);
    }
}