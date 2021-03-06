package botalibrium.rest;

import botalibrium.dto.input.bulk.InsertRecordsInBulk;
import botalibrium.dto.input.bulk.UnpopulatedBatch;
import botalibrium.dto.output.BatchDto;
import botalibrium.dto.output.ContainerWrapper;
import botalibrium.dto.output.LinksWrapper;
import botalibrium.dto.output.Page;
import botalibrium.dto.output.bulk.BulkOperationPreview;
import botalibrium.dto.output.pricing.BatchPriceEstimation;
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
@Path(BatchesEndpoint.BASE_URI)
public class BatchesEndpoint {
    public static final String BASE_URI = "/v1/batches";

//todo expand embedded

    @Autowired
    private BatchesService cs;

    public BatchesEndpoint() {
        //
    }

    @GET
    @Path("{id}")
    public Response getBatch(@PathParam("id") ObjectId id, @Context UriInfo uriInfo, @QueryParam("onlyData") boolean showOnlyData) throws ServiceException {
        Batch c = cs.getBatch(id);
        return Response.ok(new LinksWrapper(c.toDto(showOnlyData), uriInfo), MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("{id}/containers/{tag}")
    public Response getContainer(@PathParam("id") ObjectId id, @PathParam("tag") String tag, @Context UriInfo uriInfo, @QueryParam("onlyData") boolean showOnlyData) throws ServiceException, URISyntaxException {
        EmptyContainer c = cs.getContainer(id, tag);
        return Response.ok(new ContainerWrapper(c, showOnlyData)).build();
    }

    @PUT
    @Path("{id}")
    public Response replaceBatch(@PathParam("id") ObjectId id, BatchDto batch, @Context UriInfo uriInfo, @QueryParam("onlyData") boolean showOnlyData) throws ServiceException {
        Batch batchUpdated = cs.updateBatch(id, batch.toEntity());
        return Response.ok(new LinksWrapper(batchUpdated.toDto(showOnlyData), uriInfo), MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("{id}/price")
    public Response price(@PathParam("id") ObjectId id, @QueryParam("profit") long profit, @QueryParam("shipping") long shipping) throws ServiceException {
        BatchPriceEstimation c = cs.calculatePrice(id, profit, shipping);
        return Response.ok(c, MediaType.APPLICATION_JSON).header("x-readonly", true).build();
    }

    @GET
    public Response getPageByTagOrTaxon(@QueryParam("text") String text,
                                        @QueryParam("page") int page,
                                        @QueryParam("limit") int limit, @Context UriInfo uriInfo) throws ValidationException {
        Page contents = cs.basicSearch(text, page, limit, uriInfo);
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
        return Response.created(new URI(BatchesEndpoint.BASE_URI + "/" + key.getId())).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveBatch(@Valid BatchDto batchDto, @Context UriInfo uriInfo) throws ServiceException, URISyntaxException {
        Batch c = batchDto.toEntity();
        return Response.created(uriInfo.getAbsolutePathBuilder().path(cs.save(c).getId().toString()).build()).build();
    }
}



