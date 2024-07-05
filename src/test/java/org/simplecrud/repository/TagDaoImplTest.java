package org.simplecrud.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.simplecrud.repository.entity.TagEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagDaoImplTest {
    private Dao<TagEntity> tagEntityDao;

    @BeforeEach
    void setUp() {
        tagEntityDao = new TagDaoImpl();
    }

    @Test
    void findTagById_tagId_tag() {
        // given
        TagEntity entity = new TagEntity(1, "syntax");

        // when
        Optional<TagEntity> optional = tagEntityDao.get(1);

        //then
        assertEquals(optional.get(), entity);
    }


}
