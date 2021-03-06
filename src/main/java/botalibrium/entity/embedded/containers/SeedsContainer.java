package botalibrium.entity.embedded.containers;

import botalibrium.dto.output.BatchDto;
import lombok.Data;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import java.util.LinkedList;

@Data
@Entity
@Embedded
public class SeedsContainer extends EmptyContainer {
    private LinkedList<SeedlingsPopulationLog> populationLogs = new LinkedList<>();
    private String pretreatment;
    private int germinated = 0;
    private int sown = 0;

    public static SeedlingsPopulationLog fromDto(BatchDto.PopulationLogDto dto) {
        SeedlingsPopulationLog log = new SeedlingsPopulationLog();
        log.setDate(dto.getDate());
        log.setDied(dto.getDied());
        log.setRemoved(dto.getRemoved());
        return log;
    }

    @Override
    public int getPopulation() {
        return super.getPopulation() + germinated;
    }

    public void addCountLog(SeedlingsPopulationLog log) {
        if (sown < 1) {
            throw new IllegalStateException("Invalid count of sown seeds which means container is not populated");
        }// TODO May be more checks here
        populationLogs.add(log);
        germinated += log.germinated;
        removed += log.removed;
        died += log.died;
    }

    @Data
    public static class SeedlingsPopulationLog extends PopulationLog {
        private int germinated = 0;
        private int sown = 0;

        public BatchDto.PopulationLogDto toDto() {
            BatchDto.SeedlingsPopulationLogDto dto = new BatchDto.SeedlingsPopulationLogDto();
            dto.setGerminated(germinated);
            dto.setRemoved(removed);
            dto.setAdded(added);
            dto.setDate(date);
            dto.setSown(sown);
            dto.setDied(died);
            return dto;
        }
    }

    public double getGerminationRate() {
        if (sown == 0) {
            return 0;
        }
        return (double) germinated / sown;
    }

    @Override
    public void recalculateCounts() {
        germinated = 0;
        removed = 0;
        added = 0;
        died = 0;
        for (SeedlingsPopulationLog log : populationLogs) {
            germinated += log.germinated;
            removed += log.removed;
            added += log.added;
            died += log.died;
        }
    }

    public BatchDto.SeedsContainerDto toDto(boolean showOnlyData) {
        if (!showOnlyData) {
            return toCompleteDto();
        }
        BatchDto.SeedsContainerDto dto = new BatchDto.SeedsContainerDto();
        populateDtoFields(dto);
        return dto;
    }

    public BatchDto.SeedsContainerDto toCompleteDto() {
        BatchDto.SeedsContainerCompleteDto dto = new BatchDto.SeedsContainerCompleteDto();
        populateDtoFields(dto);
        dto.getCalculated().put("removed", removed);
        dto.getCalculated().put("germinated", germinated);
        dto.getCalculated().put("died", died);
        dto.getCalculated().put("germinationRate", getGerminationRate());
        dto.getCalculated().put("deathRate", getDeathRate());
        dto.getCalculated().put("population", getPopulation());
        return dto;
    }

    private void populateDtoFields(BatchDto.SeedsContainerDto dto) {
        dto.setRecords(records);
        dto.setDescription(description);
        dto.setTag(tag);
        dto.setMedia(media);
        dto.setPlantSize(plantSize);
        dto.setRecords(records);
        dto.setSown(sown);
        for (SeedlingsPopulationLog populationLog : populationLogs) {
            dto.getPopulationLogs().add((BatchDto.SeedlingsPopulationLogDto) populationLog.toDto());
        }
    }

}
