package botalibrium.entity.base;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

@Entity("customProperties")
public class CustomFieldGroupDefinition {
    @Id
    private ObjectId id = new ObjectId();
    private String description;
    @Embedded
    private Map<String, CustomFieldDefinition> customFieldDefinitions = new TreeMap<>();
    private Set<String> applicableEntities = new TreeSet<>();

    public  Map<String, CustomFieldDefinition> getCustomFieldDefinitions() {
        return customFieldDefinitions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getApplicableEntities() {
        return applicableEntities;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
}
