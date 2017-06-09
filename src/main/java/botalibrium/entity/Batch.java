package botalibrium.entity;

import java.sql.Timestamp;
import java.util.*;

import botalibrium.entity.embedded.PlantMaterial;
import botalibrium.entity.embedded.Record;
import botalibrium.entity.embedded.containers.Container;
import org.mongodb.morphia.annotations.*;

import botalibrium.entity.base.BaseEntity;

/**
 * Created by apolyakov on 3/24/2017.
 */
@Indexes(@Index(fields = { @Field("tags") }, options = @IndexOptions(unique = true)))
@Entity
public class Batch extends BaseEntity {
    public static final String TAGS_FIELD = "tags";
    private Set<String> tags = new HashSet<>();
    @Embedded
    private PlantMaterial material;
    @Embedded
    private List<Container> containers = new ArrayList<>();
    @Embedded
    private List<Record> records = new LinkedList<>();
    private Timestamp started = new Timestamp(new Date().getTime());
    private String taxon;

    public Batch() {
        //
    }

    public String getTaxon() {
        return taxon;
    }

    public void setTaxon(String taxon) {
        this.taxon = taxon;
    }


    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public PlantMaterial getMaterial() {
        return material;
    }

    public void setMaterial(PlantMaterial material) {
        this.material = material;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public Timestamp getStarted() {
        return started;
    }

    public void setStarted(Timestamp started) {
        this.started = started;
    }

    public Integer getCount() {
        return 0;
    }

    public List<Container> getContainers() {
        return containers;
    }

    public void setContainers(List<Container> containers) {
        this.containers = containers;
    }
}
