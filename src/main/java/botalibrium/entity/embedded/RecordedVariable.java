package botalibrium.entity.embedded;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import java.util.Set;
import java.util.TreeSet;

@Entity
@Embedded
public class RecordedVariable implements Comparable<RecordedVariable> {
    private String name = "Variable";
    private Set<String> values = new TreeSet<>();
    private String type;
    private SelectionNode parent;
    private boolean required = false;

    public RecordedVariable(String name, Set<String> values, String type) {
        this.name = name;
        this.values = values;
        this.type = type;
    }

    public RecordedVariable() {
        //
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getValues() {
        return values;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecordedVariable that = (RecordedVariable) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public int compareTo(RecordedVariable recordedVariable) {
        return 0;
    }

    public SelectionNode getParent() {
        return parent;
    }

    public void setParent(SelectionNode parent) {
        this.parent = parent;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}
