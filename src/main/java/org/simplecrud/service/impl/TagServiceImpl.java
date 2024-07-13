package org.simplecrud.service.impl;

import org.simplecrud.repository.TagDao;
import org.simplecrud.repository.impl.TagDaoImpl;
import org.simplecrud.repository.entity.TagEntity;
import org.simplecrud.service.TagService;
import org.simplecrud.service.model.Tag;
import org.simplecrud.service.validator.TagValidator;
import org.simplecrud.service.validator.TagValidatorImpl;
import org.simplecrud.service.validator.ValidationException;

import java.util.List;
import java.util.Optional;

public class TagServiceImpl implements TagService {

    private final TagDao tagDao;
    private final TagValidator tagValidator;

    public TagServiceImpl(TagDao tagDao, TagValidator tagValidator) {
        this.tagDao = tagDao;
        this.tagValidator = tagValidator;
    }

    public TagServiceImpl() {
        this(new TagDaoImpl(), new TagValidatorImpl());
    }

    @Override
    public Optional<Tag> get(long tagId) {
        return tagDao.get(tagId).map(Tag::of);
    }

    @Override
    public long create(Tag tag) {
        List<String> errors = tagValidator.validate(tag);
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        TagEntity tagEntity = toTagEntity(tag);
        return tagDao.create(tagEntity);
    }

    @Override
    public void update(Tag tag) {
        List<String> errors = tagValidator.validate(tag);
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        TagEntity tagEntity = toTagEntity(tag);
        tagDao.update(tagEntity);
    }

    @Override
    public void delete(long tagId) {
        tagDao.delete(tagId);
    }

    private TagEntity toTagEntity(Tag tag) {
        return new TagEntity(tag.getId(), tag.getName());
    }
}
