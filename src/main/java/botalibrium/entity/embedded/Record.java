package botalibrium.entity.embedded;

import botalibrium.entity.base.BaseEntity;
import org.mongodb.morphia.annotations.Entity;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Observation or treatment record.
 */
@Entity
public class Record extends BaseEntity {

    private Timestamp timestamp = new Timestamp(new Date().getTime());
    private String type = "Observation";

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
