package botalibrium.entity.embedded.containers;

import botalibrium.entity.base.CustomFieldGroup;
import botalibrium.entity.embedded.records.Record;
import botalibrium.references.SizeChart;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.utils.IndexDirection;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
    @Indexed(value = IndexDirection.ASC, name = "user_login_indx",
            background = false, unique = true,
            dropDups = true,
            expireAfterSeconds = -1)
    protected String tag;
    protected String description = "";
    @Embedded
    protected List<CustomFieldGroup> customFields = new LinkedList<>();
    @Embedded
    protected List<Record> records = new LinkedList<>();
    @Embedded
    private Set<String> media = new HashSet<>();

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

    public Set<String> getMedia() {
        return media;
    }

    public void setMedia(Set<String> media) {
        this.media = media;
    }
}
