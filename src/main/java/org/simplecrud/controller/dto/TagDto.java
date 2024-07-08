package org.simplecrud.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.simplecrud.service.model.Tag;

@Getter
public class TagDto {

    private final Long id;
    private final String name;

    @JsonCreator
    public TagDto(
            @JsonProperty("id") Long id,
            @JsonProperty("name") String name) {

        this.id = id;
        this.name = name;
    }

    public static TagDto of(Tag tag) {
        return new TagDto(tag.getId(), tag.getName());
    }

}
