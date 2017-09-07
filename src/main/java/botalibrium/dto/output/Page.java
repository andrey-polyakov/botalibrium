package botalibrium.dto.output;

import lombok.Data;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.LinkedList;
import java.util.List;

/**
 * Page abstraction for convenience.
 */
@Data
public class Page {
    //https://www.snyxius.com/blog/21-best-practices-designing-launching-restful-api/#.WWAXDojyuUk
    private long pageNumber = 0;
    private int itemsCount = 0;
    private int totalPages = 1;
    private String query;
    private List<?> items;
    private List<String> navigation = new LinkedList<>();


    public Page(String query, List<?> list, long page, long pageSize, long count, UriInfo uriInfo) {
        pageNumber = page;
        totalPages = (int) Math.ceil(count / pageSize);
        long stopAtPage = page + 10;
        if (stopAtPage > totalPages) {
            stopAtPage = totalPages;
        }
        for (int counter = 0; counter < stopAtPage; counter++) {
            if (counter != page) {
                generateLink(query, counter, pageSize, uriInfo);
            }
        }
        items = list;
        itemsCount = list.size();
    }

    private void generateLink(String query, long page, long limit, UriInfo uriInfo) {
        if (uriInfo == null) {
            return;
        }
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        if (!query.isEmpty()) {
            builder = builder.queryParam("text", query);
        }
        if (limit > 0) {
            builder = builder.queryParam("limit", limit);
        }
        builder = builder.queryParam("page", page);
        navigation.add(builder.toString());
    }

}
