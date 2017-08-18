package botalibrium.embedded;

import botalibrium.entity.embedded.containers.EmptyContainer;
import botalibrium.entity.embedded.containers.PlantsContainer;
import botalibrium.entity.embedded.containers.SeedsContainer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CountsTest {

    @Test
    public void PlantsContainerCounts() {
        PlantsContainer pc = new PlantsContainer();
        EmptyContainer.PopulationLog pl = new EmptyContainer.PopulationLog();
        pl.setAdded(10);
        pl.setRemoved(1);
        pl.setDied(1);
        EmptyContainer.PopulationLog pl1 = new EmptyContainer.PopulationLog();
        pl1.setRemoved(1);
        EmptyContainer.PopulationLog pl2 = new EmptyContainer.PopulationLog();
        pl2.setRemoved(2);
        pc.getPopulationLogs().add(pl);
        pc.getPopulationLogs().add(pl1);
        pc.getPopulationLogs().add(pl2);
        pc.recalculateCounts();
        assertEquals(5, pc.getPopulation());
    }

    @Test
    public void SeedsContainerCounts() {
        SeedsContainer pc = new SeedsContainer();
        SeedsContainer.SeedlingsPopulationLog pl = new SeedsContainer.SeedlingsPopulationLog();
        pl.setAdded(10);
        pl.setRemoved(1);
        pl.setDied(1);
        pl.setGerminated(1);
        SeedsContainer.SeedlingsPopulationLog pl1 = new SeedsContainer.SeedlingsPopulationLog();
        pl1.setRemoved(1);
        SeedsContainer.SeedlingsPopulationLog pl2 = new SeedsContainer.SeedlingsPopulationLog();
        pl2.setRemoved(2);
        pc.getPopulationLogs().add(pl);
        pc.getPopulationLogs().add(pl1);
        pc.getPopulationLogs().add(pl2);
        pc.recalculateCounts();
        assertEquals(6, pc.getPopulation());
    }

    @Test
    public void EmptyContainerCounts() {
        EmptyContainer pc = new EmptyContainer();
        pc.recalculateCounts();
        assertEquals(0, pc.getPopulation());
    }
}
