package botalibrium.references;

/**
 * Plant size in terms of space. Used for calculation of price,
 * shipping cost, etc.
 */
public enum SizeChart {
    XS("1.5-3cm"),
    S("3-5cm"),
    M("5-8cm"),
    L("8-12"),
    XL("15-20");

    private String description;

    SizeChart(String explanation) {
        description = explanation;
    }
}
