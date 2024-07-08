package org.simplecrud.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.simplecrud.service.model.Tag;

@RequiredArgsConstructor
@Getter
public class TagDto {

    private final long id;
    private final String name;

    public static TagDto of(Tag tag) {
        return new TagDto(tag.getId(), tag.getName());
    }

}
