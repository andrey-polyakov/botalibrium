package botalibrium.dta.output.pricing;

import botalibrium.entity.Batch;
import botalibrium.entity.embedded.catalog.ProductionDifficulty;
import botalibrium.references.SizeChart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by apolyakov on 6/12/2017.
 */
public class BatchPriceEstimation {
    private String taxon;
    private ProductionDifficulty productionDifficulty;
    private double adjustedCoefficient;
    private Map<SizeChart, SellPriceEstimation> containers = new HashMap<>();

    public BatchPriceEstimation(Batch b) {
        this.taxon = b.getMaterial().getTaxon();
        this.productionDifficulty = b.getMaterial().getProductionDifficulty();
        this.adjustedCoefficient = 1.0;
    }

    public BatchPriceEstimation() {
        //
    }

    public Map<SizeChart, SellPriceEstimation> getContainers() {
        return containers;
    }

    public String getTaxon() {
        return taxon;
    }

    public void setTaxon(String taxon) {
        this.taxon = taxon;
    }

    public ProductionDifficulty getProductionDifficulty() {
        return productionDifficulty;
    }

    public void setProductionDifficulty(ProductionDifficulty productionDifficulty) {
        this.productionDifficulty = productionDifficulty;
    }

    public double getAdjustedCoefficient() {
        return adjustedCoefficient;
    }

    public void setAdjustedCoefficient(double adjustedCoefficient) {
        this.adjustedCoefficient = adjustedCoefficient;
    }

    public void setContainers(Map<SizeChart, SellPriceEstimation> containers) {
        this.containers = containers;
    }
}
