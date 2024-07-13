package org.simplecrud.service.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.simplecrud.service.model.Tag;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TagValidatorImplTest {

    private final TagValidatorImpl validator = new TagValidatorImpl();

    @Test
    void validate_tagIsValid_ok() {
        // given
        Tag tag = new Tag(1L, "test");

        // when
        List<String> errors = validator.validate(tag);

        // then
        assertThat(errors).isEmpty();
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "  \t  "})
    void validate_tagNameIsBlank_error(String tagName) {
        // given
        Tag tag = new Tag(1L, tagName);

        // when
        List<String> errors = validator.validate(tag);

        // then
        assertThat(errors).containsOnly("Tag name is blank.");
    }
}