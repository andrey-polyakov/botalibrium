package botalibrium.entity.embedded.containers;


import botalibrium.dta.output.BatchDto;
import botalibrium.entity.embedded.records.Record;
import botalibrium.references.SizeChart;
import lombok.Data;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Transient;
import org.mongodb.morphia.utils.IndexDirection;

import java.util.*;

@Data
@Entity
@Embedded
public class EmptyContainer implements Comparable<EmptyContainer> {
    @Indexed(value = IndexDirection.ASC, name = "tag_index", unique = true, dropDups = true)
    protected String tag;
    @Embedded
    protected List<ScheduleItem> schedule = new LinkedList<>();
    @Embedded
    protected List<Record> records = new LinkedList<>();
    @Embedded
    protected Set<String> labels = new HashSet<>();
    @Embedded
    protected Set<String> media = new HashSet<>();
    protected SizeChart plantSize = SizeChart.NA;
    protected String description = "";
    protected int removed = 0;
    protected int added = 0;
    protected int died = 0;
    @Transient
    private ObjectId id;


    public int getPopulation() {
        return added - removed - died;
    }

    public void recalculateCounts() {
    }

    public double getDeathRate() {
        if (getPopulation() == 0) {
            return 0.0;
        }
        return (double) died / getPopulation();
    }

    @Override
    public int compareTo(EmptyContainer o) {
        return tag.compareTo(o.tag);
    }

    @Data
    public static class ScheduleItem implements Comparable<ScheduleItem> {
        public enum Priority {
            LOW, NORMAL, URGENT
        }

        protected Date date = new Date();
        protected Priority priority;
        protected String task;

        @Override
        public int compareTo(ScheduleItem plantsCountLog) {
            return date.compareTo(plantsCountLog.date);
        }

        public BatchDto.ScheduleItemDto toDto() {
            BatchDto.ScheduleItemDto dto = new BatchDto.ScheduleItemDto();
            dto.setPriority(priority);
            dto.setDate(date);
            dto.setTask(task);
            return dto;
        }
    }

    @Data
    public static class PopulationLog implements Comparable<PopulationLog> {
        protected Date date = new Date();
        protected int removed = 0;
        protected int added = 0;
        protected int died = 0;

        @Override
        public int compareTo(PopulationLog plantsCountLog) {
            return date.compareTo(plantsCountLog.date);
        }

        public BatchDto.PopulationLogDto toDto() {
            BatchDto.PopulationLogDto dto = new BatchDto.PopulationLogDto();
            dto.setRemoved(removed);
            dto.setAdded(added);
            dto.setDate(date);
            dto.setDied(died);
            return dto;
        }
    }

    public BatchDto.EmptyContainerDto toDto(boolean showOnlyData) {
        BatchDto.EmptyContainerDto dto = new BatchDto.EmptyContainerDto();
        populateDtoFields(dto);
        return dto;
    }

    public BatchDto.EmptyContainerDto toCompleteDto() {
        return toDto(true);
    }

    protected void populateDtoFields(BatchDto.EmptyContainerDto dto) {
        dto.setDescription(description);
        dto.setPlantSize(plantSize);
        dto.setSchedule(schedule);
        dto.setRecords(records);
        dto.setLabels(labels);
        dto.setMedia(media);
        dto.setTag(tag);
    }

}