package botalibrium.entity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

import botalibrium.entity.base.BaseEntity;

/**
 * Created by apolyakov on 3/24/2017.
 */
@Entity("plantFiles")
public class PlantFile extends BaseEntity {

    private String tag;
    @Reference
    private Taxon taxon;
    @Reference
    private PlantMaterial material;
    @Embedded
    private List<Record> records = new LinkedList<>();
    private Timestamp started = new Timestamp(new Date().getTime());
    @Reference
    private PlantFile parent;

    public PlantFile() {
        //
    }

    public PlantFile(String tag, Taxon taxon, PlantMaterial material) {
        this.tag = tag;
        this.taxon = taxon;
        this.material = material;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Taxon getTaxon() {
        return taxon;
    }

    public void setTaxon(Taxon taxon) {
        this.taxon = taxon;
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

    public PlantFile getParent() {
        return parent;
    }

    public void setParent(PlantFile parent) {
        this.parent = parent;
    }
}
