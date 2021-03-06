package botalibrium.dto.input.bulk;

import botalibrium.entity.embedded.containers.TemporalStringTuple;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Pattern used to populate batch.
 */
@Data
public class UnpopulatedContainer {
    private String tag = "";
    private String type = "";
    private String description = "";
    private List<TemporalStringTuple> media = new ArrayList<>();
    private int containersCount = 1;

}
