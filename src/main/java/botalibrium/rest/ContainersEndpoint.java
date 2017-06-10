package botalibrium.rest;

import botalibrium.dta.Page;
import botalibrium.entity.Batch;
import botalibrium.service.ContainersService;
import botalibrium.service.exception.ServiceException;
import botalibrium.service.exception.ValidationException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by apolyakov on 4/11/2017.
 */
@Component
@Path("/v1/batches")
public class ContainersEndpoint {
    @Autowired
    private ContainersService cs;

    public ContainersEndpoint() {
        new String("");
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContainer(@PathParam("id") ObjectId id) throws ServiceException {
        Batch c = cs.getContainer(id);
        return Response.ok(c, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPageByTagOrTaxon(@QueryParam("text") String text,
                                        @QueryParam("page") int page,
                                        @QueryParam("limit") int limit) throws ValidationException {
        Page contents = cs.basicSearch(text, page, limit);
        return Response.ok(contents, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveContainer(Batch c) throws ServiceException, URISyntaxException {
        return Response.created(new URI("/batches/" + cs.save(c).getId().toString())).build();
    }
}



