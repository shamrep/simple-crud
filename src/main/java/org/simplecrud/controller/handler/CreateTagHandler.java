package org.simplecrud.controller.handler;

import org.simplecrud.controller.Request;
import org.simplecrud.controller.Response;
import org.simplecrud.controller.dto.TagDto;
import org.simplecrud.service.TagService;
import org.simplecrud.service.impl.TagServiceImpl;

public class CreateTagHandler implements Handler {

    private final TagService tagService = new TagServiceImpl();

    @Override
    public Response handle(Request request) {
        TagDto tagDto = request.getBody(TagDto.class);
        long tagId = tagService.create(tagDto.toTag());

        return Response.created("/tags/" + tagId);
    }
}
