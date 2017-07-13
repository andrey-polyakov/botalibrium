package botalibrium.entity.embedded.containers;

import botalibrium.dta.input.bulk.CountLogDto;
import lombok.Data;

import java.util.Date;
import java.util.LinkedList;

/**
 * Extends plant container with count of seeds sown.
 */
@Data
public class SeedsCommunityContainer extends PlantsContainer {

    private int sownSeedsCount = 0;
    private int germinatedCount = 0;
    protected int deadCount = 0;
    /**
     * Sold or transplanted
     **/
    protected int removedCount = 0;
    protected LinkedList<CountLog> countLogs = new LinkedList<>();
    private String seedsPretreatment;


    public static SeedsCommunityContainer.CountLog fromDto(CountLogDto count) {
        SeedsCommunityContainer.CountLog countLog = new SeedsCommunityContainer.CountLog();
        countLog.setDate(count.getDate());
        countLog.setDied(count.getDied());
        countLog.setSold(count.getSold());
        countLog.setGerminated(count.getGerminated());
        return countLog;
    }

    @Data
    public static class CountLog extends PlantsContainer.CountLog implements Comparable<PlantsContainer.CountLog> {
        private int germinated = 0;

        public int getGerminated() {
            return germinated;
        }

        public void setGerminated(int germinated) {
            this.germinated = germinated;
        }

        @Override
        public CountLogDto toDto() {
            CountLogDto dto = super.toDto();
            dto.setGerminated(germinated);
            return dto;
        }
    }

    public void addCountLog(SeedsCommunityContainer.CountLog log) {
        if (sownSeedsCount < 1) {
            throw new IllegalStateException("Invalid initial count: container is not populated");
        }
        countLogs.add(log);
        deadCount += log.died;
        removedCount += log.removedCount;
        germinatedCount += log.germinated;
    }

    @Override
    public int getAliveCount() {
        return germinatedCount - deadCount - removedCount;
    }

    public double getDeathRate() {
        return deadCount / (getAliveCount() / 100.0);
    }

    public double getGerminationRate() {
        if (sownSeedsCount == 0) {
            return 0;
        }
        return germinatedCount / (sownSeedsCount / 100.0);
    }

    public void recalculateCounts() {
        deadCount = 0;
        removedCount = 0;
        for (SeedsCommunityContainer.CountLog log : countLogs) {
            removedCount += log.removedCount;
            germinatedCount += log.germinated;
            deadCount += log.died;
        }
    }
}
