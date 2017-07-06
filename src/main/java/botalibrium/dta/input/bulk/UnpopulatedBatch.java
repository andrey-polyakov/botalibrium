package botalibrium.dta.input.bulk;

import botalibrium.entity.embedded.PlantMaterial;

import java.util.ArrayList;
import java.util.List;

/**
 * Empty batch in preparation to flasking.
 */
public class UnpopulatedBatch {

    private PlantMaterial material;

    private List<UnpopulatedContainer> unpopulatedContainers = new ArrayList<>();

    public PlantMaterial getMaterial() {
        return material;
    }

    public void setMaterial(PlantMaterial material) {
        this.material = material;
    }

    public List<UnpopulatedContainer> getUnpopulatedContainers() {
        return unpopulatedContainers;
    }

    public void setUnpopulatedContainers(List<UnpopulatedContainer> unpopulatedContainers) {
        this.unpopulatedContainers = unpopulatedContainers;
    }
}
