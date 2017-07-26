package botalibrium.dta.output;

import botalibrium.entity.Batch;
import botalibrium.entity.embedded.PlantMaterial;
import botalibrium.entity.embedded.containers.EmptyContainer;
import botalibrium.entity.embedded.containers.PlantsContainer;
import botalibrium.entity.embedded.containers.SeedsContainer;
import botalibrium.entity.embedded.records.Record;
import botalibrium.references.SizeChart;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.*;

@Data
public class BatchDto {
    @NotNull(message = "PlantMaterial is compulsory")
    private PlantMaterial material;
    private List<EmptyContainerDto> containers = new ArrayList<>();
    private List<Record> records = new ArrayList<>();
    private Timestamp started;

    public BatchDto() {
        //
    }

    public BatchDto(Batch c, boolean showOnlyData) {
        material = c.getMaterial();
        for (EmptyContainer container : c.getContainers()) {
            containers.add(container.toDto(showOnlyData));
        }
        records = c.getRecords();
        started = c.getStarted();
    }

    public Batch toEntity() {
        Batch batch = new Batch();
        batch.setMaterial(material);
        batch.setRecords(records);
        batch.setStarted(started);
        for (EmptyContainerDto container : containers) {
            batch.getContainers().add(container.toEntity());
        }
        return batch;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
    @JsonSubTypes({
            @JsonSubTypes.Type(value = PlantsContainerDto.class, name = "PlantsContainerDto"),
            @JsonSubTypes.Type(value = SeedsContainerDto.class, name = "SeedsContainerDto") }
    )
    public static class EmptyContainerDto {
        protected String tag;
        protected String description;
        protected List<Record> records = new ArrayList<>();
        protected Set<String> media = new HashSet<>();
        protected SizeChart plantSize = SizeChart.NA;

        public EmptyContainer toEntity() {
            EmptyContainer ec = new EmptyContainer();
            ec.setDescription(description);
            ec.setMedia(media);
            ec.setTag(tag);
            ec.setPlantSize(plantSize);
            ec.setRecords(records);
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
            ec.setDescription(description);
            ec.setMedia(media);
            ec.setTag(tag);
            ec.setPlantSize(plantSize);
            ec.setRecords(records);
            ec.setPretreatment(pretreatment);
            ec.setSown(sown);
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
    public static class PlantsContainerDto extends EmptyContainerDto{
        private Map<String, Object> calculated = new HashMap<>();
        private List<PopulationLogDto> populationLogs = new ArrayList<>();
        protected int population = 0;
    }

    @Data
    public static class PopulationLogDto {
        protected int died = 0;
        protected int removed = 0;
        protected Date timestamp = new Date();

        public EmptyContainer.PopulationLog toEntity() {
            EmptyContainer.PopulationLog log = new EmptyContainer.PopulationLog();
            log.setDate(timestamp);
            log.setDied(died);
            log.setRemoved(removed);
            return log;
        }
    }

    @Data
    public static class SeedlingsPopulationLogDto extends PopulationLogDto {
        private int germinated = 0;

        @Override
        public SeedsContainer.SeedlingsPopulationLog toEntity() {
            SeedsContainer.SeedlingsPopulationLog log = new SeedsContainer.SeedlingsPopulationLog();
            log.setDate(timestamp);
            log.setDied(died);
            log.setRemoved(removed);
            log.setGerminated(germinated);
            return log;
        }
    }

}
