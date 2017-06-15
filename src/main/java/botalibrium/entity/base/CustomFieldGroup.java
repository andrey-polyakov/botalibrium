package botalibrium.entity.base;


import botalibrium.entity.embedded.records.SelectionNode;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

import java.util.*;

@Embedded
@Entity
public class CustomFieldGroup {
    @Reference
    private CustomFieldGroupDefinition definition;
    private Map<String, SelectionNode> selectionNodes = new HashMap<>();

    public Map<String, SelectionNode> getSelectionNodes() {
        return selectionNodes;
    }

    public CustomFieldGroupDefinition getDefinition() {
        return definition;
    }

    public void setDefinition(CustomFieldGroupDefinition definition) {
        this.definition = definition;
    }
}
