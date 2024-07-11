package org.simplecrud.controller.handler;

import org.simplecrud.controller.Request;
import org.simplecrud.controller.Response;
import org.simplecrud.controller.dto.TagDto;
import org.simplecrud.service.Service;
import org.simplecrud.service.TagServiceImpl;
import org.simplecrud.service.model.Tag;

public class CreateTagHandler implements TagHandler {
    private final Service<Tag> tagService = new TagServiceImpl();

    @Override
    public Response handle(Request request) {
        TagDto tagDto = request.getBody(TagDto.class);

        try {
            long tagId = tagService.save(toTag(tagDto));

            return Response.created("/tags/" + tagId);
        } catch (RuntimeException e) {
            return Response.internalServerError(e);
        }
    }
}
