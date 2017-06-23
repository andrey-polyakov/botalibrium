package botalibrium.dta.output;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.LinkedList;
import java.util.List;

/**
 * Page abstraction for convenience.
 */
public class Page {

    private long pageNumber = 0;
    private int itemsCount = 0;
    private int totalPages = 1;
    private String query;
    private List<?> items;
    private List<String> navigation = new LinkedList<>();


    public Page(String query, List<?> list, long page, long limit, long count, UriInfo uriInfo) {
        pageNumber = page;
        totalPages = (int) (count / limit + count % limit);
        int stopAtPage = 10;
        if (stopAtPage > totalPages) {
            stopAtPage = totalPages;
        }
        for (int counter = 0; counter < stopAtPage; counter++) {
            if (counter != page) {
                generateLink(query, counter, limit, uriInfo);
            }
        }
        items = list;
        itemsCount = list.size();
    }

    private void generateLink(String query, long page, long limit, UriInfo uriInfo) {
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

    public long getPageNumber() {
        return pageNumber;
    }

    public int getItemsCount() {
        return itemsCount;
    }

    public List<?> getItems() {
        return items;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setItemsCount(int itemsCount) {
        this.itemsCount = itemsCount;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setItems(List<?> items) {
        this.items = items;
    }

    public List<String> getNavigation() {
        return navigation;
    }

    public void setNavigation(List<String> navigation) {
        this.navigation = navigation;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
