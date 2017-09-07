package botalibrium.dto.input.bulk;

import botalibrium.entity.embedded.PlantMaterial;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Empty batch in preparation to flasking.
 */
@Data
public class UnpopulatedBatch {
    private PlantMaterial material;
    private List<UnpopulatedContainer> unpopulatedContainers = new ArrayList<>();

}
