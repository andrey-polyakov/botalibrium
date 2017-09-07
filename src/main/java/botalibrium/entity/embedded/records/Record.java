package botalibrium.entity.embedded.records;

import lombok.Data;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Observation or treatment record.
 */
@Entity
@Embedded
@Data
public class Record implements Comparable<Record> {

    private Timestamp date = new Timestamp(new Date().getTime());
    private String type = "Care";
    private String message = "";

    @Override
    public int compareTo(Record record) {
        if (record == null || record.date == null) {
            return -1;
        }
        return record.date.compareTo(date);
    }
}
