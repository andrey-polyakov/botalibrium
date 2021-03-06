package botalibrium.entity.embedded;

import botalibrium.entity.embedded.catalog.ProductionDifficulty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

/**
 * Represents original material plants were produced toEntity.
 * This could be seeds, cuttings or tissue cultured explants.
 */
@Entity
@Embedded
public class PlantMaterial {
    private String taxon;
    private Date collected;
    private String supplier;
    private String materialType;
    private Long originalQuantity;
    private Long buyPrice;
    private ProductionDifficulty productionDifficulty = ProductionDifficulty.NORMAL;
    private Map<ProductionDifficulty, Double> adjustedCoefficient;

    public PlantMaterial() {
        //
    }

    public PlantMaterial(String taxon) {
        this.taxon = taxon;
    }

    public PlantMaterial(String supplier, String materialType, Long originalQuantity) {
        this.supplier = supplier;
        this.materialType = materialType;
        this.originalQuantity = originalQuantity;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    @NotBlank(message = "MaterialType name blank")
    @NotEmpty(message = "MaterialType name empty")
    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public Long getOriginalQuantity() {
        return originalQuantity;
    }

    public void setOriginalQuantity(Long originalQuantity) {
        this.originalQuantity = originalQuantity;
    }

    public Long getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Long buyPrice) {
        this.buyPrice = buyPrice;
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

    public Map<ProductionDifficulty, Double> getAdjustedCoefficient() {
        return adjustedCoefficient;
    }

    public void setAdjustedCoefficient(Map<ProductionDifficulty, Double> adjustedCoefficient) {
        this.adjustedCoefficient = adjustedCoefficient;
    }

    public Date getCollected() {
        return collected;
    }

    public void setCollected(Timestamp collected) {
        this.collected = collected;
    }
}
