package botalibrium.entity;


import botalibrium.entity.base.BaseEntity;
import org.mongodb.morphia.annotations.Entity;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;


@Entity("taxa")
public class TaxonDetails extends BaseEntity {

    public static final String NAME = "names";
    /**
     * The first species listed is always the mother (seed carrier)
     * and the second is always the father (pollen donor).
     *
     * e.g. N. raffalesiana X burkei
     *
     * This is a Set to accommodate synonyms.
     */
    private Set<String> names = new TreeSet<>();
    private String variety = "";
    /**
     * Language code to description mapping.
     */
    private Map<String, String> multilingualDescription = new TreeMap<>();
    /**
     * Header to paragraph contents mapping.
     */
    private Map<String, String> cultivationTips = new TreeMap<>();

    public TaxonDetails() {
        //
    }

    public TaxonDetails(Set<String> s) {
        this.names = s;
    }

    public Set<String> getNames() {
        return names;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }
}
