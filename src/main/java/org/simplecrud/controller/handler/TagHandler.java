package org.simplecrud.controller.handler;

import org.simplecrud.controller.dto.TagDto;
import org.simplecrud.service.model.Tag;

public interface TagHandler extends Handler {
    default Tag toTag(TagDto tagDto) {
        return new Tag(tagDto.getId(), tagDto.getName());
    }
}
