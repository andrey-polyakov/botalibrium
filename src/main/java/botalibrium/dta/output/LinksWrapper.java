package botalibrium.dta.output;

import botalibrium.entity.Batch;
import lombok.Data;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by apolyakov on 6/16/2017.
 */
@Data
public class LinksWrapper {

    private Batch batch;

    private List<String> links = new LinkedList<>();

    public LinksWrapper(Batch batch, UriInfo uriInfo) {
        this.batch = batch;
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path("/price");
        links.add(builder.toString());
    }

    public List<String> getLinks() {
        return links;
    }
}
