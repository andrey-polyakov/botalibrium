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

    public void addCountLog(PopulationLog log) {
        populationLogs.add(log);
        removed += log.removed;
        added += log.added;
        died += log.died;
    }

    public double getDeathRate() {
        return died / ((added - removed) / 100.0);
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
        dto.getCalculated().put("population", added + removed - died);
        dto.getCalculated().put("removed", removed);
        dto.getCalculated().put("added", added);
        dto.getCalculated().put("died", died);
        dto.getCalculated().put("deathRate", getDeathRate());
        return dto;
    }

    private void populateDtoFields(BatchDto.PlantsContainerDto dto) {
        dto.setDescription(description);
        dto.setPlantSize(plantSize);
        dto.setRecords(records);
        dto.setMedia(media);
        dto.setTag(tag);
        for (PopulationLog populationLog : populationLogs) {
            dto.getPopulationLogs().add(populationLog.toDto());
        }
    }

}
