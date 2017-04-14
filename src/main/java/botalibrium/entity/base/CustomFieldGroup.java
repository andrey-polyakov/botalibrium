package botalibrium.entity.base;


import botalibrium.entity.embedded.SelectionNode;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

import java.util.*;

@Embedded
@Entity
public class CustomFieldGroup {
    @Reference
    private CustomFieldGroupDefinition definition;
    private Set<SelectionNode> selectionNodes = new HashSet<>();

    public Set<SelectionNode> getSelectionNodes() {
        return selectionNodes;
    }

    public CustomFieldGroupDefinition getDefinition() {
        return definition;
    }

    public void setDefinition(CustomFieldGroupDefinition definition) {
        this.definition = definition;
    }
}
