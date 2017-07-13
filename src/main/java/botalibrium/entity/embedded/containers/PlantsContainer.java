package botalibrium.entity.embedded.containers;

import botalibrium.dta.input.bulk.CountLogDto;
import botalibrium.entity.base.CustomFieldGroup;
import botalibrium.entity.embedded.records.Record;
import botalibrium.references.SizeChart;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.utils.IndexDirection;

import java.util.*;

@Entity
@Embedded
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PlantsContainer.class, name = "PlantsContainer"),
        @JsonSubTypes.Type(value = SeedsCommunityContainer.class, name = "SeedsCommunityContainer") }
)
public class PlantsContainer {
    protected SizeChart plantSize = SizeChart.NA;
    @Indexed(value = IndexDirection.ASC, name = "user_login_indx",
            background = false, unique = true,
            dropDups = true,
            expireAfterSeconds = -1)
    protected String tag;
    protected String description = "";
    @Embedded
    protected List<CustomFieldGroup> customFields = new LinkedList<>();
    @Embedded
    protected List<Record> records = new LinkedList<>();
    @Embedded
    private Set<String> media = new HashSet<>();
    private LinkedList<CountLog> countLogs = new LinkedList<>();
    private int initialCount = 0;
    protected int calculatedDeadCount = 0;
    protected int calculatedSoldOrTransplantedCount = 0;


    public PlantsContainer(int initialCount) {
        this.initialCount = initialCount;
    }

    public PlantsContainer() {
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

    public int getAliveCount() {
        return initialCount - calculatedDeadCount - calculatedSoldOrTransplantedCount;
    }

    public SizeChart getPlantSize() {
        return plantSize;
    }

    public void setPlantSize(SizeChart plantSize) {
        this.plantSize = plantSize;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CustomFieldGroup> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(List<CustomFieldGroup> customFields) {
        this.customFields = customFields;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public Set<String> getMedia() {
        return media;
    }

    public void setMedia(Set<String> media) {
        this.media = media;
    }

    public void setCountLogs(LinkedList<CountLog> countLogs) {
        this.countLogs = countLogs;
    }

    public int getInitialCount() {
        return initialCount;
    }

    public void setInitialCount(int initialCount) {
        this.initialCount = initialCount;
    }

    public int getCalculatedDeadCount() {
        return calculatedDeadCount;
    }

    public void setCalculatedDeadCount(int calculatedDeadCount) {
        this.calculatedDeadCount = calculatedDeadCount;
    }

    public int getCalculatedSoldOrTransplantedCount() {
        return calculatedSoldOrTransplantedCount;
    }

    public void setCalculatedSoldOrTransplantedCount(int calculatedSoldOrTransplantedCount) {
        this.calculatedSoldOrTransplantedCount = calculatedSoldOrTransplantedCount;
    }
}
