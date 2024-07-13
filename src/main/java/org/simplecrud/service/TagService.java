package org.simplecrud.service;

import org.simplecrud.service.model.Tag;

import java.util.Optional;

public interface TagService {

    Optional<Tag> get(long tagId);

    long create(Tag tag);

    void update(Tag tag);

    void delete(long tagId);
}
