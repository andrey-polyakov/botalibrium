package botalibrium.entity.base;

import org.mongodb.morphia.annotations.Entity;

/**
 * Created by apolyakov on 3/24/2017.
 */
@Entity
public class CustomFieldDefinition {

    private String name;
    private String type;
    private String description;
    private Boolean mandatory;

    public CustomFieldDefinition() {
        //
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getMandatory() {
        return mandatory;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }
}
