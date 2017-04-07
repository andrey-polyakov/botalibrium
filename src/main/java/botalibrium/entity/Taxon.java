package botalibrium.entity;


import botalibrium.entity.base.BaseEntity;
import org.mongodb.morphia.annotations.Entity;

@Entity("taxa")
public class Taxon extends BaseEntity {

    /**
     * The first species listed is always the mother (seed carrier)
     * and the second is always the father (pollen donor).
     *
     * e.g. N. raffalesiana X burkei
     */
    private String name;

    public Taxon() {
        //
    }

    public Taxon(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
