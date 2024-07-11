package org.simplecrud.controller.handler;

import org.simplecrud.controller.Request;
import org.simplecrud.controller.Response;
import org.simplecrud.service.Service;
import org.simplecrud.service.TagServiceImpl;
import org.simplecrud.service.model.Tag;

public class DeleteTagHandler implements TagHandler {
    private final Service<Tag> tagService = new TagServiceImpl();

    @Override
    public Response handle(Request request) {
        long tagId = request.getPathParameter("id", Long.class);

        try {
            tagService.delete(tagId);
            return Response.noContent();

        } catch (RuntimeException e) {
            return Response.internalServerError(e);
        }
    }
}
