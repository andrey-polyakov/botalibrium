package botalibrium.rest;

import botalibrium.dta.Page;
import botalibrium.dta.input.BulkOperation;
import botalibrium.dta.output.BulkOperationPreview;
import botalibrium.dta.pricing.BatchPriceEstimation;
import botalibrium.entity.Batch;
import botalibrium.dta.LinksWrapper;
import botalibrium.service.BatchesService;
import botalibrium.service.exception.ServiceException;
import botalibrium.service.exception.ValidationException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URISyntaxException;

@Produces(MediaType.APPLICATION_JSON)
@Validated
@Component
@Path(BatchesEndpoint.BASE_URI)
public class BatchesEndpoint {
    public static final String BASE_URI = "/v1/batches";

    @Autowired
    private BatchesService cs;

    public BatchesEndpoint() {
        new String("");
    }

    @GET
    @Path("{id}")
    public Response getContainer(@PathParam("id") ObjectId id, @Context UriInfo uriInfo) throws ServiceException {
        Batch c = cs.getContainer(id);
        return Response.ok(new LinksWrapper(c, uriInfo), MediaType.APPLICATION_JSON).build();
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
                                        @QueryParam("limit") int limit, @Context UriInfo uriInfo) throws ValidationException {
        Page contents = cs.basicSearch(text, page, limit,uriInfo);
        return Response.ok(contents, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("records")
    public Response bulkOperation(BulkOperation operation) throws ValidationException, URISyntaxException {
        BulkOperationPreview contents = cs.bulkSelect(operation);
        return Response.ok(contents, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveContainer(@Valid Batch c, @Context UriInfo uriInfo) throws ServiceException, URISyntaxException {
        return Response.created(uriInfo.getAbsolutePathBuilder().path(cs.save(c).getId().toString()).build()).build();
    }
}



