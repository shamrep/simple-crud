package org.simplecrud.service.validator;

import org.simplecrud.service.model.Tag;

import java.util.List;

public interface TagValidator {

    List<String> validate(Tag tag);
}
