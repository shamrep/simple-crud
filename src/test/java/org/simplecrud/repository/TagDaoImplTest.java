package org.simplecrud.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.simplecrud.repository.entity.TagEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagDaoImplTest {
    private Dao<TagEntity> tagEntityDao;
    private TagDaoImpl tagDao = new TagDaoImpl();

    @BeforeEach
    void setUp() {
        tagEntityDao = new TagDaoImpl();
    }

    @Test
    void findTagById_tagId_tag() {
        // given
        TagEntity entity = new TagEntity(1L, "syntax");

        // when
        Optional<TagEntity> optional = tagEntityDao.get(1);
        List<TagEntity> optional1 = tagDao.getTagsByQuestionId(1);

        //then
        assertEquals(optional.get(), entity);
    }


}
