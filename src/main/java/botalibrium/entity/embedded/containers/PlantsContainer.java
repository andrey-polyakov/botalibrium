package botalibrium.entity.embedded.containers;


import botalibrium.dta.output.BatchDto;
import lombok.Data;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import java.util.LinkedList;

@Data
@Entity
@Embedded
public class PlantsContainer extends EmptyContainer {
    private LinkedList<PopulationLog> populationLogs = new LinkedList<>();
    protected int initialPopulation = 0;

    public void addCountLog(PopulationLog log) {
        if (initialPopulation < 1) {
            throw new IllegalStateException("Invalid initial count: container is not populated");
        }// TODO May be more checks here
        populationLogs.add(log);
        removed += log.removed;
        died += log.died;
    }

    public double getDeathRate() {
        return died / (initialPopulation / 100.0);
    }

    public void recalculateCounts() {
        died = 0;
        removed = 0;
        for (PopulationLog log : populationLogs) {
            removed += log.removed;
            died += log.died;
        }
    }

    @Override
    public int getInitialPopulation() {
        return initialPopulation;
    }

    public BatchDto.PlantsContainerDto toDto(boolean showOnlyData) {
        if (!showOnlyData) {
            return toCompleteDto();
        }
        BatchDto.PlantsContainerDto dto = new BatchDto.PlantsContainerDto();
        populateDtoFields(dto);
        return dto;
    }

    public BatchDto.PlantsContainerDto toCompleteDto() {
        BatchDto.PlantsContainerDto dto = new BatchDto.PlantsContainerDto();
        populateDtoFields(dto);
        dto.getCalculated().put("removed", removed);
        dto.getCalculated().put("died", died);
        dto.getCalculated().put("deathRate", getDeathRate());
        dto.getCalculated().put("initialPopulation", this.getInitialPopulation());
        return dto;
    }

    private void populateDtoFields(BatchDto.PlantsContainerDto dto) {
        dto.setRecords(records);
        dto.setDescription(description);
        dto.setTag(tag);
        dto.setMedia(media);
        dto.setPlantSize(plantSize);
        dto.setRecords(records);
        for (PopulationLog populationLog : populationLogs) {
            dto.getPopulationLogs().add(populationLog.toDto());
        }
    }

}
