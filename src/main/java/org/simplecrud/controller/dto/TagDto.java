package org.simplecrud.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.simplecrud.service.model.Tag;

@Getter
@EqualsAndHashCode
public class TagDto {

    private final long id;
    private final String name;

    @JsonCreator
    public TagDto(
            @JsonProperty("id") long id,
            @JsonProperty("name") String name) {

        this.id = id;
        this.name = name;
    }

    public static TagDto of(Tag tag) {
        return new TagDto(tag.getId(), tag.getName());
    }

    public Tag toTag() {
        return new Tag(id, name);
    }

}
