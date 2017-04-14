package botalibrium.entity.embedded;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import java.util.Set;

@Entity
@Embedded
public class RecordedVariable implements Comparable<RecordedVariable> {
    private String name;
    private String value;
    private String type;
    private Set<SelectionNode> options;

    public RecordedVariable(String name, String value, String type, Set<SelectionNode> options) {
        this.name = name;
        this.value = value;
        this.type = type;
        this.options = options;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<SelectionNode> getOptions() {
        return options;
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
}
