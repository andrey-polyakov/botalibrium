package botalibrium.rest;

import botalibrium.dta.Page;
import botalibrium.dta.pricing.BatchPriceEstimation;
import botalibrium.dta.pricing.SellPriceEstimation;
import botalibrium.entity.Batch;
import botalibrium.service.BatchesService;
import botalibrium.service.exception.ServiceException;
import botalibrium.service.exception.ValidationException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by apolyakov on 4/11/2017.
 */
@Produces(MediaType.APPLICATION_JSON)
@Validated
@Component
@Path("/v1/batches")
public class BatchesEndpoint {
    @Autowired
    private BatchesService cs;

    public BatchesEndpoint() {
        new String("");
    }

    @GET
    @Path("{id}")
    public Response getContainer(@PathParam("id") ObjectId id) throws ServiceException {
        Batch c = cs.getContainer(id);
        return Response.ok(c, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("{id}/price")
    public Response estimateSellPrice(@PathParam("id") ObjectId id, @QueryParam("profit") long profit, @QueryParam("shipping") long shipping) throws ServiceException {
        BatchPriceEstimation c = cs.calculatePrice(id, profit, shipping);
        return Response.ok(c, MediaType.APPLICATION_JSON).build();
    }

    @GET
    public Response getPageByTagOrTaxon(@QueryParam("text") String text,
                                        @QueryParam("page") int page,
                                        @QueryParam("limit") int limit) throws ValidationException {
        Page contents = cs.basicSearch(text, page, limit);
        return Response.ok(contents, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveContainer(@Valid Batch c) throws ServiceException, URISyntaxException {
        return Response.created(new URI("/batches/" + cs.save(c).getId().toString())).build();
    }
}



