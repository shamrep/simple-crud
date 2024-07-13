package org.simplecrud.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.simplecrud.repository.TagDao;
import org.simplecrud.repository.entity.TagEntity;
import org.simplecrud.service.model.Tag;
import org.simplecrud.service.validator.TagValidator;
import org.simplecrud.service.validator.ValidationException;

import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    private TagDao tagDao;

    @Mock
    private TagValidator tagValidator;

    private TagServiceImpl tagService;

    @BeforeEach
    void setUp() {
        tagService = new TagServiceImpl(tagDao, tagValidator);
    }

    @Test
    void create_tagIsInvalid_validationException() {
        // given
        Tag tag = new Tag(null, "test");
        when(tagValidator.validate(tag)).thenReturn(singletonList("error_test"));

        // when
        Throwable thrown = catchThrowable(() -> tagService.create(tag));

        // then
        assertThat(thrown)
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("error_test");
    }

    @Test
    void create_tagIsValid_delegateToDao() {
        // given
        Tag tag = new Tag(null, "test");
        when(tagValidator.validate(tag)).thenReturn(emptyList());
        when(tagDao.create(new TagEntity(null, "test"))).thenReturn(123L);

        // when
        long createdTagId = tagService.create(tag);

        // then
        verify(tagDao).create(new TagEntity(null, "test"));
        assertThat(createdTagId).isEqualTo(123L);
    }

    @Test
    void update_tagIsInvalid_validationException() {
        // given
        Tag tag = new Tag(1L, "test");
        when(tagValidator.validate(tag)).thenReturn(singletonList("error_test"));

        // when
        Throwable thrown = catchThrowable(() -> tagService.update(tag));

        // then
        assertThat(thrown)
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("error_test");
    }

    @Test
    void update_tagIsValid_delegateToDao() {
        // given
        Tag tag = new Tag(1L, "test");
        when(tagValidator.validate(tag)).thenReturn(emptyList());
        doNothing().when(tagDao).update(new TagEntity(1L, "test"));

        // when
        tagService.update(tag);

        // then
        verify(tagDao).update(new TagEntity(1L, "test"));
    }

    @Test
    void get_tagExists_fromDao() {
        // given
        when(tagDao.get(1L)).thenReturn(Optional.of(new TagEntity(1L, "test")));

        // when
        Optional<Tag> tag = tagService.get(1L);

        // then
        assertThat(tag.get())
                .usingRecursiveComparison()
                .isEqualTo(new Tag(1L, "test"));
    }

    @Test
    void get_tagDoesNotExists_empty() {
        // given
        when(tagDao.get(1L)).thenReturn(Optional.empty());

        // when
        Optional<Tag> tag = tagService.get(1L);

        // then
        assertThat(tag).isEmpty();
    }

    @Test
    void delete_tagId_delegateToDao() {
        // given
        doNothing().when(tagDao).delete(1L);

        // when
        tagService.delete(1L);

        // then
        verify(tagDao).delete(1L);
    }
}