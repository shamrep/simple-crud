package org.simplecrud.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public class TagEntity {
    private long id;
    private String content;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TagEntity entity)) return false;
        return id == entity.id && Objects.equals(content, entity.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content);
    }
}
