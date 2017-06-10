package botalibrium.dta;

import java.util.List;

/**
 * Page abstraction for convenience.
 */
public class Page {

    private int pageNumber = 1;
    private int itemsCount = 0;
    private List<?> items;

    public Page(List<?> list) {
        items = list;
        itemsCount = list.size();
    }

    public Page(List<?> list, int page) {
        this(list);
        pageNumber = page;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getItemsCount() {
        return itemsCount;
    }

    public List<?> getItems() {
        return items;
    }

}
