package org.simplecrud.controller.handler;

import org.simplecrud.controller.Request;
import org.simplecrud.controller.Response;
import org.simplecrud.service.TagService;
import org.simplecrud.service.impl.TagServiceImpl;

public class DeleteTagHandler implements Handler {

    private final TagService tagService = new TagServiceImpl();

    @Override
    public Response handle(Request request) {
        long tagId = request.getPathParameter("id");
        tagService.delete(tagId);

        return Response.noContent();
    }
}
