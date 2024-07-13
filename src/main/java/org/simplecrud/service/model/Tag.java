package org.simplecrud.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.simplecrud.repository.entity.TagEntity;

@Getter
@AllArgsConstructor
public class Tag {

    private Long id;
    private String name;

    public static Tag of(TagEntity entity) {
        return new Tag(entity.getId(), entity.getName());
    }
}
