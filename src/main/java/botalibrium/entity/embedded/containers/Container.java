package botalibrium.entity.embedded.containers;

import botalibrium.entity.base.CustomFieldGroup;
import botalibrium.entity.embedded.Record;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import java.util.LinkedList;
import java.util.List;

/**
 * Individual container.
 */
@Entity
@Embedded
public class Container {
    protected String tag;
    protected String description = "";
    @Embedded
    protected List<CustomFieldGroup> customFields = new LinkedList<>();
    @Embedded
    protected List<Record> records = new LinkedList<>();

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
}
