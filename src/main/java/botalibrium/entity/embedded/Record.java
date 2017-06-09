package botalibrium.entity.embedded;

import botalibrium.entity.base.CustomFieldGroup;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Observation or treatment record.
 */
@Entity
@Embedded
public class Record {

    private Timestamp timestamp = new Timestamp(new Date().getTime());
    private String type = "Observation";
    private String series = "default";
    private String message = "";
    @Embedded
    private List<CustomFieldGroup> customFields = new ArrayList<>();

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public List<CustomFieldGroup> getCustomFields() {
        return customFields;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
