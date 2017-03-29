package botalibrium.entity.base;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Map;

/**
 * Base class that carries base base and id.
 */
@Entity
public class BaseEntity {
    @Id
    private ObjectId id = new ObjectId();
    @Embedded
    private Map<String,String> customFields;

    public BaseEntity() {
        //
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Map<String, String> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(Map<String, String> customFields) {
        this.customFields = customFields;
    }
}
