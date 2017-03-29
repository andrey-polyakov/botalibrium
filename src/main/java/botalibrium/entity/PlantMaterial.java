package botalibrium.entity;

import botalibrium.entity.base.BaseEntity;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

/**
 * Represents original material (ex)plants were produced from.
 * This could be from seeds, cutting or tissue culture.
 */
@Entity("plantMaterial")
public class PlantMaterial extends BaseEntity {
    @Reference
    private Supplier supplier;
    private String type;
    private Long quantity;

    public PlantMaterial() {
        //
    }

    public PlantMaterial(Supplier supplier, String type, Long quantity) {
        this.supplier = supplier;
        this.type = type;
        this.quantity = quantity;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
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
}
