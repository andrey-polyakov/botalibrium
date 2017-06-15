package botalibrium.entity;

import java.sql.Timestamp;
import java.util.*;

import botalibrium.entity.embedded.PlantMaterial;
import botalibrium.entity.embedded.records.Record;
import botalibrium.entity.embedded.containers.Container;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.mongodb.morphia.annotations.*;

import botalibrium.entity.base.BaseEntity;

import javax.validation.constraints.NotNull;

/**
 * Created by apolyakov on 3/24/2017.
 */
@Entity
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Batch extends BaseEntity {
    @Embedded
    private PlantMaterial material;
    @Embedded
    private List<? extends Container> containers = new ArrayList<>();
    @Embedded
    private List<Record> records = new LinkedList<>();
    private Timestamp started = new Timestamp(new Date().getTime());

    public Batch() {
        //
    }

    @NotNull(message = "PlantMaterial is compulsory")
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
        int count = 0;
        for (Container container : containers) {
            count += container.getAliveCount();
        }
        return count;
    }

    public List<? extends Container> getContainers() {
        return containers;
    }

    public void setContainers(List<? extends Container> containers) {
        this.containers = containers;
    }
}
