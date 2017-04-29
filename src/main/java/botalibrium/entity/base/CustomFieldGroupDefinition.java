package botalibrium.entity.base;

import botalibrium.entity.embedded.SelectionNode;
import botalibrium.entity.serializers.ObjectIdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

@Entity("customProperties")
public class CustomFieldGroupDefinition {
    @Id
    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId id = new ObjectId();
    private String description;
    private Set<String> applicableEntities = new TreeSet<>();
    private Map<String, SelectionNode> selectionNodes = new TreeMap<>();

    public Map<String, SelectionNode> getSelectionNodes() {
        return selectionNodes;
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
