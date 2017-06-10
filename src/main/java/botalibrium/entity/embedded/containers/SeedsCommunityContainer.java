package botalibrium.entity.embedded.containers;

import java.util.TreeSet;

/**
 * Extends plant container with count od seeds sown.
 */
public class SeedsCommunityContainer extends CommunityContainer {

    private int sownSeedsCount;

    public int getSownSeedsCount() {
        return sownSeedsCount;
    }

    public void setSownSeedsCount(int sownSeedsCount) {
        this.sownSeedsCount = sownSeedsCount;
    }

    public double getGerminationRate() {
        return (new TreeSet<CountLog>(countLogs).last().getDeadCount() + new TreeSet<CountLog>((countLogs)).last().getCurrentCount() + new TreeSet<CountLog>(countLogs).last().getSoldCount()) / (sownSeedsCount / 100.0);
    }
}
