package org.simplecrud.controller.handler;

import org.simplecrud.controller.Request;
import org.simplecrud.controller.Response;
import org.simplecrud.controller.dto.TagDto;
import org.simplecrud.service.Service;
import org.simplecrud.service.TagServiceImpl;
import org.simplecrud.service.model.Tag;

public class UpdateTagHandler implements TagHandler {

    private final Service<Tag> tagService = new TagServiceImpl();

    @Override
    public Response handle(Request request) {
        Tag tag = toTag(request.getBody(TagDto.class));

        boolean updated = tagService.update(tag);

        if (updated) {
            return tagService.get(tag.getId()).map(t -> Response.ok(TagDto.of(t))).get();
        }

        return Response.notFound();
    }
}
