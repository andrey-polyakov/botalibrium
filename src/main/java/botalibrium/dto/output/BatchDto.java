package botalibrium.dto.output;

import botalibrium.entity.Batch;
import botalibrium.entity.embedded.PlantMaterial;
import botalibrium.entity.embedded.containers.EmptyContainer;
import botalibrium.entity.embedded.containers.TemporalTuple;
import botalibrium.entity.embedded.containers.PlantsContainer;
import botalibrium.entity.embedded.containers.SeedsContainer;
import botalibrium.entity.embedded.records.Record;
import botalibrium.references.SizeChart;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.*;

@Data
public class BatchDto {
    private List<EmptyContainerDto> containers = new ArrayList<>();
    private Map<String, Object> calculated = new HashMap<>();
    private List<Record> records = new ArrayList<>();
    private Set<String> labels = new HashSet<>();
    @NotNull(message = "PlantMaterial is compulsory")
    private PlantMaterial material;
    private Timestamp started;

    public BatchDto() {
        //
    }

    public Batch toEntity() {
        Batch batch = new Batch();
        batch.setMaterial(material);
        batch.setRecords(records);
        batch.setStarted(started);
        batch.setLabels(labels);
        for (EmptyContainerDto container : containers) {
            EmptyContainer entityContainer = container.toEntity();
            entityContainer.recalculateCounts();
            batch.getContainers().add(entityContainer);
        }
        return batch;
    }

    @Data
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
    @JsonSubTypes({
            @JsonSubTypes.Type(value = PlantsContainerDto.class, name = "PlantsContainerDto"),
            @JsonSubTypes.Type(value = SeedsContainerDto.class, name = "SeedsContainerDto") }
    )
    public static class EmptyContainerDto {
        protected List<EmptyContainer.ScheduleItem> schedule;
        protected List<TemporalTuple<String>> media = new ArrayList<>();
        protected List<Record> records = new ArrayList<>();
        protected Set<String> labels = new HashSet<>();
        protected SizeChart plantSize = SizeChart.NA;
        protected String description;
        protected String tag;
        private ObjectId id;

        public EmptyContainer toEntity() {
            EmptyContainer ec = new EmptyContainer();
            ec.setDescription(description);
            ec.setPlantSize(plantSize);
            ec.setSchedule(schedule);
            ec.setRecords(records);
            ec.setLabels(labels);
            ec.setMedia(media);
            ec.setTag(tag);
            return ec;
        }
    }

    @Data
    public static class SeedsContainerDto extends EmptyContainerDto {
        private List<SeedlingsPopulationLogDto> populationLogs = new ArrayList<>();
        private String pretreatment;
        private int sown = 0;

        @Override
        public EmptyContainer toEntity() {
            SeedsContainer ec = new SeedsContainer();
            ec.setPretreatment(pretreatment);
            ec.setDescription(description);
            ec.setPlantSize(plantSize);
            ec.setSchedule(schedule);
            ec.setRecords(records);
            ec.setMedia(media);
            ec.setSown(sown);
            ec.setTag(tag);
            for (SeedlingsPopulationLogDto populationLog : populationLogs) {
                ec.getPopulationLogs().add(populationLog.toEntity());
            }
            return ec;
        }
    }

    @Data
    public static class SeedsContainerCompleteDto extends SeedsContainerDto {
        private Map<String, Object> calculated = new HashMap<>();
    }

    @Data
    public static class PlantsContainerDto extends EmptyContainerDto {
        private List<PopulationLogDto> populationLogs = new ArrayList<>();
        private Map<String, Object> calculated = new HashMap<>();

        @Override
        public EmptyContainer toEntity() {
            PlantsContainer ec = new PlantsContainer();
            ec.setDescription(description);
            ec.setPlantSize(plantSize);
            ec.setSchedule(schedule);
            ec.setRecords(records);
            ec.setMedia(media);
            ec.setTag(tag);
            for (PopulationLogDto populationLog : populationLogs) {
                ec.getPopulationLogs().add(populationLog.toEntity());
            }
            return ec;
        }
    }

    @Data
    public static class PopulationLogDto {
        protected Date date = new Date();
        protected int removed = 0;
        protected int added = 0;
        protected int died = 0;

        public EmptyContainer.PopulationLog toEntity() {
            EmptyContainer.PopulationLog log = new EmptyContainer.PopulationLog();
            log.setRemoved(removed);
            log.setAdded(added);
            log.setDate(date);
            log.setDied(died);
            return log;
        }
    }

    @Data
    public static class SeedlingsPopulationLogDto extends PopulationLogDto {
        private int germinated = 0;
        private int sown = 0;

        @Override
        public SeedsContainer.SeedlingsPopulationLog toEntity() {
            SeedsContainer.SeedlingsPopulationLog log = new SeedsContainer.SeedlingsPopulationLog();
            log.setGerminated(germinated);
            log.setRemoved(removed);
            log.setAdded(added);
            log.setDate(date);
            log.setSown(sown);
            log.setDied(died);
            return log;
        }
    }

    @Data
    public static class ScheduleItemDto {
        protected EmptyContainer.ScheduleItem.Priority priority;
        protected Date date = new Date();
        protected String task;
    }
}
