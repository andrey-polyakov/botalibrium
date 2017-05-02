package botalibrium.entity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import botalibrium.entity.embedded.Record;
import org.mongodb.morphia.annotations.*;

import botalibrium.entity.base.BaseEntity;

/**
 * Created by apolyakov on 3/24/2017.
 */
@Indexes(@Index(fields = { @Field("tag") }, options = @IndexOptions(unique = true)))
@Entity
public class Container extends BaseEntity {
    public static final String TAG_FIELD = "tag";
    private String tag = "Not assigned";
    @Reference
    private PlantMaterial material;
    @Embedded
    private List<Record> records = new LinkedList<>();
    private Timestamp started = new Timestamp(new Date().getTime());
    private Integer count;

    public Container() {
        //
    }

    public Container(String tag, PlantMaterial material) {
        this.tag = tag;
        this.material = material;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
