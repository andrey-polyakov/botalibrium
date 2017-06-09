package botalibrium.rest;

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
import java.util.List;

/**
 * Created by apolyakov on 4/11/2017.
 */
@Component
@Path("/containers")
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
/*
    @GET
    @Path("/taxon")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPageByTaxon(@QueryParam("name") String text) throws ValidationException {
        List<Batch> contents = cs.searchByTaxon(text, 1, 25);
        return Response.ok(contents, MediaType.APPLICATION_JSON).build();
    }*/

    @GET
    @Path("/basicSearch")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPageByTag(@QueryParam("text") String text,
                                 @QueryParam("page") int page,
                                 @QueryParam("limit") int limit) throws ValidationException {
        List<Batch> contents = cs.basicSearch(text, page, limit);
        return Response.ok(contents, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveContainer(Batch c) throws ServiceException {
        return Response.ok(cs.save(c).getId(), MediaType.APPLICATION_JSON).build();
    }
}



