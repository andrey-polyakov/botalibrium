package botalibrium.entity.embedded.containers;

import botalibrium.dta.output.BatchDto;
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
        log.setDate(dto.getTimestamp());
        log.setDied(dto.getDied());
        log.setRemoved(dto.getRemoved());
        return log;
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

        public BatchDto.PopulationLogDto toDto() {
            BatchDto.SeedlingsPopulationLogDto dto = new BatchDto.SeedlingsPopulationLogDto();
            dto.setTimestamp(date);
            dto.setDied(died);
            dto.setRemoved(removed);
            dto.setGerminated(germinated);
            return dto;
        }
    }

    @Override
    public int getInitialPopulation() {
        return germinated - died - removed;
    }

    public double getDeathRate() {
        if (getInitialPopulation() == 0) {
            return 0;
        }
        return died / (getInitialPopulation() / 100.0);
    }

    public double getGerminationRate() {
        if (sown == 0) {
            return 0;
        }
        return germinated / (sown / 100.0);
    }

    public void recalculate() {
        died = 0;
        removed = 0;
        for (SeedlingsPopulationLog log : populationLogs) {
            removed += log.removed;
            germinated += log.germinated;
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
        dto.getCalculated().put("initialPopulation", getInitialPopulation());
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
