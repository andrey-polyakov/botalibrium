package botalibrium.entity.embedded;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

/**
 * Represents original material plants were produced from.
 * This could be from seeds, cuttings or tissue cultured explants.
 */
@Entity
@Embedded
public class PlantMaterial {
    enum ProductionDifficulty {
        VERY_EASY,
        EASY,
        NORMAL,
        HARD,
        VERY_HARD
    }
    private String taxon;
    private String supplier;
    private String type;
    private Long originalQuantity;
    private Long price;
    private ProductionDifficulty productionDifficulty = ProductionDifficulty.NORMAL;

    public PlantMaterial() {
        //
    }

    public PlantMaterial(String supplier, String type, Long originalQuantity) {
        this.supplier = supplier;
        this.type = type;
        this.originalQuantity = originalQuantity;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getOriginalQuantity() {
        return originalQuantity;
    }

    public void setOriginalQuantity(Long originalQuantity) {
        this.originalQuantity = originalQuantity;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public ProductionDifficulty getProductionDifficulty() {
        return productionDifficulty;
    }

    public void setProductionDifficulty(ProductionDifficulty productionDifficulty) {
        this.productionDifficulty = productionDifficulty;
    }

    public String getTaxon() {
        return taxon;
    }

    public void setTaxon(String taxon) {
        this.taxon = taxon;
    }
}
