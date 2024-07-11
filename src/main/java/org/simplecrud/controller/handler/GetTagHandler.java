package org.simplecrud.controller.handler;

import org.simplecrud.controller.Request;
import org.simplecrud.controller.Response;
import org.simplecrud.controller.dto.TagDto;
import org.simplecrud.service.Service;
import org.simplecrud.service.TagServiceImpl;
import org.simplecrud.service.model.Tag;

import java.util.Optional;

public class GetTagHandler implements TagHandler {
    private final Service<Tag> tagService = new TagServiceImpl();

    @Override
    public Response handle(Request request) {
        long tagId = request.getPathParameter("id", Long.class);

        Optional<Tag> optionalTag = tagService.get(tagId);

        return optionalTag.map(t -> Response.ok(TagDto.of(t))).orElse(Response.notFound());
    }
}