package botalibrium.references;

/**
 * Plant classification in terms of life-cycle.
 */
public enum MaturityChart {

    FLASK("1.5-3cm"),
    COMPOT("3-5cm"),
    SEEDLING("5-8cm"),
    ADOLESCENT("8-12"),
    ADULT("15-20"),
    FULLY_MATURED("");

    private String description;

    MaturityChart(String explanation) {
        description = explanation;
    }
}
