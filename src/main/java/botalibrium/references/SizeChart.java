package botalibrium.references;

/**
 * Plant size in terms of space. Used for calculation of price,
 * shipping cost, etc.
 */
public enum SizeChart {
    NA("N/A", 0),
    XXS("0-1cm", 500),
    XS("1-3cm", 900),
    S("3-6cm", 1200),
    M("6-11cm", 1500),
    L("11-15", 2000),
    XL("15-20", 2500);

    private String description;
    private long genericPrice;

    SizeChart(String description, long genericPrice) {
        this.description = description;
        this.genericPrice = genericPrice;
    }

    public String getDescription() {
        return description;
    }

    public long getGenericPrice() {
        return genericPrice;
    }
}
