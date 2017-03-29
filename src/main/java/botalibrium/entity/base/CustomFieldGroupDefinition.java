package botalibrium.entity.base;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import java.util.List;

/**
 * Created by apolyakov on 3/24/2017.
 */
@Entity("customProperties")
public class CustomFieldGroupDefinition {

    private String name;
    @Embedded
    private List<CustomFieldDefinition> properties;
    private List<String> applicableEntities;


    public List<CustomFieldDefinition> getProperties() {
        return properties;
    }

    public void setProperties(List<CustomFieldDefinition> properties) {
        this.properties = properties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getApplicableEntities() {
        return applicableEntities;
    }

    public void setApplicableEntities(List<String> applicableEntities) {
        this.applicableEntities = applicableEntities;
    }
}
