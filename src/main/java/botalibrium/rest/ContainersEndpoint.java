package botalibrium.rest;

import botalibrium.dta.input.bulk.InsertRecordsInBulk;
import botalibrium.dta.input.bulk.UnpopulatedBatch;
import botalibrium.dta.output.BatchDto;
import botalibrium.dta.output.ContainerWrapper;
import botalibrium.dta.output.Page;
import botalibrium.dta.output.bulk.BulkOperationPreview;
import botalibrium.dta.output.pricing.BatchPriceEstimation;
import botalibrium.entity.Batch;
import botalibrium.entity.embedded.containers.EmptyContainer;
import botalibrium.service.BatchesService;
import botalibrium.service.exception.ServiceException;
import botalibrium.service.exception.ValidationException;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;

@Produces(MediaType.APPLICATION_JSON)
@Validated
@Component
@Path(ContainersEndpoint.BASE_URI)
public class ContainersEndpoint {
    public static final String BASE_URI = "/v1/containers";

//todo expand embedded

    @Autowired
    private BatchesService cs;

    public ContainersEndpoint() {
        //
    }

    @GET
    @Path("{id}")
    public Response getContainer(@PathParam("id") String tag, @Context UriInfo uriInfo, @QueryParam("onlyData") boolean showOnlyData) throws ServiceException, URISyntaxException {
        EmptyContainer c = cs.getContainer(tag);
        if (showOnlyData) {
            return Response.ok(c.toDto(showOnlyData)).build();
        }
        return Response.temporaryRedirect(new URI(new ContainerWrapper(c, showOnlyData).getLinks().get("self"))).build();
    }

    @PUT
    @Path("{id}")
    public Response replaceContainer(@PathParam("id") String tag, BatchDto.EmptyContainerDto c, @Context UriInfo uriInfo, @QueryParam("onlyData") boolean showOnlyData) throws ServiceException {
        EmptyContainer container = cs.updateContainer(tag, c.toEntity());
        return Response.ok(new ContainerWrapper(container, showOnlyData), MediaType.APPLICATION_JSON).build();
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
    public Response bulkRecordsInsert(InsertRecordsInBulk operation) throws ValidationException, URISyntaxException {
        BulkOperationPreview contents = cs.bulkSelect(operation);
        return Response.ok(contents, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("unpopulated")
    public Response newUnpopulatedBatch(UnpopulatedBatch unpopulatedBatch, @Context UriInfo uriInfo) throws ServiceException, URISyntaxException {
        Key<Batch> key = cs.newUnpopulatedBatch(unpopulatedBatch);
        return Response.created(new URI(ContainersEndpoint.BASE_URI + "/" + key.getId())).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveBatch(@Valid BatchDto batchDto, @Context UriInfo uriInfo) throws ServiceException, URISyntaxException {
        Batch c = batchDto.toEntity();
        return Response.created(uriInfo.getAbsolutePathBuilder().path(cs.save(c).getId().toString()).build()).build();
    }
}



