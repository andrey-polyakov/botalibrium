package botalibrium.entity.embedded.containers;

import org.mongodb.morphia.annotations.Property;

/**
 * Extends plant container with count od seeds sown.
 */
public class SeedsCommunityContainer extends CommunityContainer {

    @Property("sownSeedsCount")
    private int sownSeedsCount;

    public int getSownSeedsCount() {
        return sownSeedsCount;
    }

    public void setSownSeedsCount(int sownSeedsCount) {
        this.sownSeedsCount = sownSeedsCount;
    }

    public double getGerminationRate() {
        return (countLogs.last().getDeadCount() + countLogs.last().getCurrentCount() + countLogs.last().getSoldCount()) / (sownSeedsCount / 100.0);
    }
}
