package botalibrium.dta.output;

import botalibrium.entity.embedded.containers.EmptyContainer;
import botalibrium.rest.BatchesEndpoint;
import lombok.Data;
import org.glassfish.jersey.uri.internal.JerseyUriBuilder;

import javax.ws.rs.core.UriBuilder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by apolyakov on 6/16/2017.
 */
@Data
public class ContainerWrapper {

    private BatchDto.EmptyContainerDto container;

    private Map<String, String> links = new HashMap<>();

    public ContainerWrapper(EmptyContainer container, boolean showOnlyData) {
        this.container = container.toDto(showOnlyData);
        UriBuilder builder = new JerseyUriBuilder();
        builder.path(BatchesEndpoint.BASE_URI);
        builder.path(container.getId().toString());
        links.put("batch", builder.toString());
        builder.path("containers");
        builder.path(container.getTag());
        links.put("self", builder.toString());
    }

}
