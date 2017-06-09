package botalibrium.entity.embedded.containers;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
@Embedded
public class CommunityContainer extends Container {

    protected SortedSet<CountLog> countLogs = new TreeSet();

    public static class CountLog implements Comparable<CountLog> {
        private int deadCount = 1;
        private int currentCount = 1;
        private int soldCount = 1;
        private Date date;

        public CountLog(int deadCount, int currentCount, int soldCount, Date date) {
            this.deadCount = deadCount;
            this.currentCount = currentCount;
            this.soldCount = soldCount;
            this.date = date;
        }

        public CountLog() {
        }

        public int getDeadCount() {
            return deadCount;
        }

        public void setDeadCount(int deadCount) {
            this.deadCount = deadCount;
        }

        public int getCurrentCount() {
            return currentCount;
        }

        public void setCurrentCount(int currentCount) {
            this.currentCount = currentCount;
        }

        public int getSoldCount() {
            return soldCount;
        }

        public void setSoldCount(int soldCount) {
            this.soldCount = soldCount;
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

        public int getDeathRate() {
            return deadCount / (soldCount + currentCount) / 100;
        }

    }

    public SortedSet<CountLog> getCountLogs() {
        return countLogs;
    }

    public void setCountLogs(SortedSet<CountLog> countLogs) {
        this.countLogs = countLogs;
    }

    public int getDeathRate() {
        return countLogs.last().getDeathRate();
    }

    @Override
    public int getAliveCount() {
        return countLogs.last().getCurrentCount();
    }
}
