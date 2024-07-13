package org.simplecrud.controller.handler;

import org.simplecrud.controller.Request;
import org.simplecrud.controller.Response;
import org.simplecrud.controller.dto.TagDto;
import org.simplecrud.service.TagService;
import org.simplecrud.service.impl.TagServiceImpl;
import org.simplecrud.service.model.Tag;

public class UpdateTagHandler implements TagHandler {

    private final TagService tagService = new TagServiceImpl();

    @Override
    public Response handle(Request request) {
        Tag tag = toTag(request.getBody(TagDto.class));

        try{
            tagService.update(tag);
            return tagService.get(tag.getId()).map(t -> Response.ok(TagDto.of(t))).get();
        } catch (RuntimeException e) {
            return Response.notFound();
        }
    }
}
