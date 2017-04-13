package botalibrium.entity;


import botalibrium.entity.base.BaseEntity;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

import java.util.Set;
import java.util.TreeSet;


@Entity("taxa")
@Indexes({
        @Index(fields = @Field("names"), unique = true),
})public class Taxon extends BaseEntity {

    /**
     * The first species listed is always the mother (seed carrier)
     * and the second is always the father (pollen donor).
     *
     * e.g. N. raffalesiana X burkei
     */
    private Set<String> names = new TreeSet<>();

    public Taxon() {
        //
    }

    public Taxon(Set<String> s) {
        this.names = s;
    }

    public Set<String> getNames() {
        return names;
    }

}
