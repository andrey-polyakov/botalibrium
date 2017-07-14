package botalibrium.dta.input.bulk;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * Pattern used to populate batch.
 */
@Data
public class UnpopulatedContainer {
    private String tag = "";
    private String type = "";
    private String description = "";
    private Set<String> media = new HashSet<>();
    private int containersCount = 1;

}
