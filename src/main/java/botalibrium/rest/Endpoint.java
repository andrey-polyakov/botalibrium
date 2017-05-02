package botalibrium.rest;

import botalibrium.entity.base.CustomFieldDefinition;
import botalibrium.entity.base.CustomFieldGroupDefinition;
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
/*        Supplier supplier = new Supplier("eBay");
        PlantMaterial pmi = new PlantMaterial(supplier, "Seeds", (long) 100);
        Set<String>names = new TreeSet<>();
        names.add("N.Foo");
        Container ci = new Container("TAG1", new Taxon(names), pmi);
        for (int ii = 0; ii < 2; ii++) {
            ci.getRecords().add(new Record());
        }*/
        CustomFieldGroupDefinition ci = new CustomFieldGroupDefinition();
        ci.setDescription("desc");
        ci.getApplicableEntities().add("entity");
        CustomFieldDefinition def = new CustomFieldDefinition();
        def.getOptions().add("ok");def.getOptions().add("not ok");
        def.setType("barbara");
        def.setMandatory(true);
        return Response.ok(ci, MediaType.APPLICATION_JSON).build();
    }
}



