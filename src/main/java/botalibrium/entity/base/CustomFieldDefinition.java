package botalibrium.entity.base;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by apolyakov on 3/24/2017.
 */
@Embedded
@Entity
public class CustomFieldDefinition {
    private String type;
    /**
     * Description for GUI
     */
    private String description;
    private Boolean mandatory = false;
    private Set<String> options = new HashSet<>();

    public CustomFieldDefinition() {
        //
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    public Set<String> getOptions() {
        return options;
    }

}
