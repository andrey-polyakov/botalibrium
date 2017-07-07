package botalibrium.entity.embedded.containers;

import botalibrium.dta.input.bulk.CountLogDto;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import java.util.*;

@Entity
@Embedded
public class CommunityContainer extends Container {

    private LinkedList<CountLog> countLogs = new LinkedList<>();
    private int initialCount = 0;
    protected int calculatedDeadCount = 0;
    protected int calculatedSoldOrTransplantedCount = 0;


    public CommunityContainer(int initialCount) {
        this.initialCount = initialCount;
    }

    public CommunityContainer() {
        //
    }

    public static CountLog fromDto(CountLogDto count) {
        CountLog countLog = new CountLog();
        countLog.setDate(count.getDate());
        countLog.setDied(count.getDied());
        countLog.setSold(count.getSold());
        return countLog;
    }

    public static class CountLog implements Comparable<CountLog> {
        protected int died = 0;
        protected int removedCount = 0;
        protected Date date = new Date();

        public CountLog(int deadCount, int soldCount, Date date) {
            this.died = deadCount;
            this.removedCount = soldCount;
            this.date = date;
        }

        public CountLog() {
            //
        }

        public int getDied() {
            return died;
        }

        public void setDied(int died) {
            this.died = died;
        }

        public int getSold() {
            return removedCount;
        }

        public void setSold(int sold) {
            this.removedCount = sold;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        @Override
        public int compareTo(CountLog countLog) {
            return date.compareTo(countLog.date);
        }

        public CountLogDto toDto() {
            CountLogDto dto = new CountLogDto();
            dto.setDate(date);
            dto.setDied(died);
            dto.setSold(removedCount);
            return dto;
        }
    }

    public LinkedList<CountLog> getCountLogs() {
        return countLogs;
    }

    public void addCountLog(CountLog log) {
        if (initialCount < 1) {
            throw new IllegalStateException("Invalid initial count: container is not populated");
        }
        countLogs.add(log);
        calculatedDeadCount += log.died;
        calculatedSoldOrTransplantedCount += log.removedCount;
    }

    public double getDeathRate() {
        return calculatedDeadCount / (initialCount / 100.0);
    }

    public void recalculateCounts() {
        calculatedDeadCount = 0;
        calculatedSoldOrTransplantedCount = 0;
        for (CountLog log : countLogs) {
            calculatedSoldOrTransplantedCount += log.removedCount;
            calculatedDeadCount += log.died;
        }
    }

    @Override
    public int getAliveCount() {
        return initialCount - calculatedDeadCount - calculatedSoldOrTransplantedCount;
    }
}
