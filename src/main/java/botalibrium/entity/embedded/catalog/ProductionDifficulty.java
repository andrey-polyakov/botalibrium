package botalibrium.entity.embedded.catalog;

/**
 * Created by apolyakov on 6/12/2017.
 */
public enum ProductionDifficulty {
    VERY_EASY(0.5),
    EASY(0.7),
    NORMAL(1.0),
    HARD(1.8),
    VERY_HARD(2.5);

    private double coefficient = 1;


    ProductionDifficulty(double v) {
        coefficient = v;
    }

    public double getCoefficient() {
        return coefficient;
    }
}
