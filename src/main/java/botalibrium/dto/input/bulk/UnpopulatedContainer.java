package botalibrium.dto.input.bulk;

import botalibrium.entity.embedded.containers.TemporalTuple;
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
    private List<TemporalTuple<String>> media = new ArrayList<>();
    private int containersCount = 1;

}
