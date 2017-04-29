package botalibrium.entity.embedded;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import java.util.Map;
import java.util.TreeMap;

/**
 * Selection node with variables where variables may have branches.
 */
@Entity
@Embedded
public class  SelectionNode {
    private String name;
    private boolean mutuallyExclusiveNodes = false;
    private boolean nodeChoiceRequired = false;
    @Embedded
    private Map<String, RecordedVariable> variables = new TreeMap<>();
    @Embedded
    private Map<String, SelectionNode> nodes = new TreeMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMutuallyExclusiveNodes() {
        return mutuallyExclusiveNodes;
    }

    public void setMutuallyExclusiveNodes(boolean mutuallyExclusiveNodes) {
        this.mutuallyExclusiveNodes = mutuallyExclusiveNodes;
    }

    public boolean isNodeChoiceRequired() {
        return nodeChoiceRequired;
    }

    public void setNodeChoiceRequired(boolean nodeChoiceRequired) {
        this.nodeChoiceRequired = nodeChoiceRequired;
    }

    public Map<String, RecordedVariable> getVariables() {
        return variables;
    }

    public Map<String, SelectionNode> getNodes() {
        return nodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SelectionNode that = (SelectionNode) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "SelectionNode{" +
                "name='" + name + '\'' +
                ", mutuallyExclusiveNodes=" + mutuallyExclusiveNodes +
                ", nodeChoiceRequired=" + nodeChoiceRequired +
                ", variables=" + variables +
                ", nodes=" + nodes +
                '}';
    }
}
