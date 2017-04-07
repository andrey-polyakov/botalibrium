package botalibrium.entity.base;


import org.mongodb.morphia.annotations.Reference;

import java.util.Map;
import java.util.TreeMap;

public class CustomFieldGroup {
    @Reference
    private CustomFieldGroupDefinition definition;
    private Map<String, String> fields = new TreeMap<>();

    public Map<String, String> getFields() {
        return fields;
    }

    public CustomFieldGroupDefinition getDefinition() {
        return definition;
    }

    public void setDefinition(CustomFieldGroupDefinition definition) {
        this.definition = definition;
    }
}
