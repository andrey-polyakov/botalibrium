package botalibrium.entity.embedded.containers;

import botalibrium.entity.base.CustomFieldGroup;
import botalibrium.entity.embedded.records.Record;
import botalibrium.references.SizeChart;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import java.util.LinkedList;
import java.util.List;

/**
 * Individual container.
 */
@Entity
@Embedded
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CommunityContainer.class, name = "CommunityContainer"),

        @JsonSubTypes.Type(value = SeedsCommunityContainer.class, name = "SeedsCommunityContainer") }
)
public class Container {
    protected SizeChart plantSize = SizeChart.NA;
    protected String tag;
    protected String description = "";
    @Embedded
    protected List<CustomFieldGroup> customFields = new LinkedList<>();
    @Embedded
    protected List<Record> records = new LinkedList<>();
    @Embedded
    protected List<String> media = new LinkedList<>();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CustomFieldGroup> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(List<CustomFieldGroup> customFields) {
        this.customFields = customFields;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public int getAliveCount() {
        return 1;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public SizeChart getPlantSize() {
        return plantSize;
    }

    public void setPlantSize(SizeChart plantSize) {
        this.plantSize = plantSize;
    }

    public List<String> getMedia() {
        return media;
    }

    public void setMedia(List<String> media) {
        this.media = media;
    }
}
