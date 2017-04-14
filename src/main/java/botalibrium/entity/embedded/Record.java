package botalibrium.entity.embedded;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Observation or treatment record.
 */
@Entity
@Embedded
public class Record {

    private Timestamp timestamp = new Timestamp(new Date().getTime());
    private String type = "Observation";
    private String seriesName = "default";

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
}
