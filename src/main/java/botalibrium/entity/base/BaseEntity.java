package botalibrium.entity.base;

import botalibrium.entity.serializers.ObjectIdDeserializer;
import botalibrium.entity.serializers.ObjectIdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Base class that carries custom fields and id.
 */
@Entity
public class BaseEntity {
    @Id
    @JsonSerialize(using = ObjectIdSerializer.class)
    @JsonDeserialize(using = ObjectIdDeserializer.class)
    private ObjectId id;
    @Embedded
    private List<CustomFieldGroup> customFields = new LinkedList<>();

    public BaseEntity() {
        //
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public List<CustomFieldGroup> getCustomFieldGroups() {
        return customFields;
    }

}
