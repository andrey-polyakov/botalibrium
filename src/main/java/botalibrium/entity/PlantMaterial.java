package botalibrium.entity;

import botalibrium.entity.base.BaseEntity;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

/**
 * Represents original material (ex)plants were produced from.
 * This could be from seeds, cutting or tissue culture.
 */
@Entity
public class PlantMaterial extends BaseEntity {
    private String supplier;
    @Reference
    private Taxon taxon;
    private String type;
    private Long quantity;

    public PlantMaterial() {
        //
    }

    public PlantMaterial(String supplier, String type, Long quantity) {
        this.supplier = supplier;
        this.type = type;
        this.quantity = quantity;
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

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Taxon getTaxon() {
        return taxon;
    }

    public void setTaxon(Taxon taxon) {
        this.taxon = taxon;
    }
}
