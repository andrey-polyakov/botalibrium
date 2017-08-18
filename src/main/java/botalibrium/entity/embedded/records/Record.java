package botalibrium.entity.embedded.records;

import botalibrium.entity.base.CustomFieldGroup;
import lombok.Data;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Observation or treatment record.
 */
@Entity
@Embedded
@Data
public class Record implements Comparable<Record> {

    private Timestamp date = new Timestamp(new Date().getTime());
    private String type = "Care";
    private String series = "default";
    private String message = "";
    @Embedded
    private List<CustomFieldGroup> customFields = new ArrayList<>();

    @Override
    public int compareTo(Record record) {
        if (record == null || record.date == null) {
            return -1;
        }
        return record.date.compareTo(date);
    }
}
