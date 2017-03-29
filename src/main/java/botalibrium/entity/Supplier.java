package botalibrium.entity;

import botalibrium.entity.base.BaseEntity;
import org.mongodb.morphia.annotations.Entity;

/**
 * If null means this facility is the producer.
 */
@Entity("suppliers")
public class Supplier extends BaseEntity {

    private String name;

    public Supplier() {
        //
    }

    public Supplier(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
