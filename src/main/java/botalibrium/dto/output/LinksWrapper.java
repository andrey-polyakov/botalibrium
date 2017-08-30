package botalibrium.dto.output;

import lombok.Data;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by apolyakov on 6/16/2017.
 */
@Data
public class LinksWrapper {

    private BatchDto batch;

    private Map<String, String> links = new HashMap<>();

    public LinksWrapper(BatchDto batch, UriInfo uriInfo) {
        this.batch = batch;
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        links.put("self", builder.toString());
        builder.path("/price");
        links.put("price", builder.toString());
    }

}
