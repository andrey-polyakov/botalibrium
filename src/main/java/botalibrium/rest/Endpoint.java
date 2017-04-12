package botalibrium.rest;

import botalibrium.entity.PlantFile;
import botalibrium.entity.PlantMaterial;
import botalibrium.entity.Supplier;
import botalibrium.entity.Taxon;
import botalibrium.entity.embedded.Record;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by apolyakov on 4/11/2017.
 */
@Component
@Path("customer")
public class Endpoint {

    public Endpoint() {
        new String("");
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomer() {
        Supplier supplier = new Supplier("eBay");
        PlantMaterial pmi = new PlantMaterial(supplier, "Seeds", (long) 100);
        PlantFile ci = new PlantFile("TAG1", new Taxon("N.Foo"), pmi);
        for (int ii = 0; ii < 2; ii++) {
            ci.getRecords().add(new Record());
        }
        return Response.ok(ci, MediaType.APPLICATION_JSON).build();
    }
}



