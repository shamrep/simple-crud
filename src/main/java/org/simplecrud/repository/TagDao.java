package org.simplecrud.repository;

import org.simplecrud.repository.entity.TagEntity;

import java.util.List;
import java.util.Optional;

public interface TagDao {

    Optional<TagEntity> get(long tagId);

    List<TagEntity> getAll();

    List<TagEntity> getTagsByQuestionId(long questionId);

    long save(TagEntity tagEntity);

    void update(TagEntity tagEntity);

    void delete(long tagId);
}
