package org.simplecrud.controller.handler;

import org.simplecrud.controller.Request;
import org.simplecrud.controller.Response;
import org.simplecrud.controller.dto.TagDto;
import org.simplecrud.service.TagService;
import org.simplecrud.service.impl.TagServiceImpl;
import org.simplecrud.service.model.Tag;

import java.util.Optional;

public class UpdateTagHandler implements Handler {

    private final TagService tagService = new TagServiceImpl();

    @Override
    public Response handle(Request request) {
        long tagId = request.getPathParameter("id");
        TagDto tagDto = request.getBody(TagDto.class);
        Optional<Tag> tag = tagService.get(tagId);

        if (tag.isEmpty()) {
            return Response.notFound();
        }

        tagService.update(tagDto.toTag());
        return Response.noContent();
    }
}
