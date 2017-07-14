package botalibrium.entity.embedded.containers;

import botalibrium.dta.input.bulk.PopulationLogDto;
import lombok.Data;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import java.util.LinkedList;

/**
 * Extends plant container with  of seeds sown.
 */
@Data
@Entity
@Embedded
public class SeedsContainer extends EmptyContainer {
    private LinkedList<SeedlingsPopulationLog> populationLogs = new LinkedList<>();
    private String pretreatment;
    private int germinated = 0;
    private int sown = 0;

    public static SeedlingsPopulationLog fromDto(PopulationLogDto dto) {
        SeedlingsPopulationLog log = new SeedlingsPopulationLog();
        log.setDate(dto.getDate());
        log.setDied(dto.getDied());
        log.setRemoved(dto.getRemoved());
        log.setGerminated(dto.getGerminated());
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

        public PopulationLogDto toDto() {
            PopulationLogDto dto = super.toDto();
            dto.setGerminated(germinated);
            return dto;
        }
    }

    @Override
    public int getPopulation() {
        return germinated - died - removed;
    }

    public double getDeathRate() {
        if (getPopulation() == 0) {
            return 0;
        }
        return died / (getPopulation() / 100.0);
    }

    public double getGerminationRate() {
        if (sown == 0) {
            return 0;
        }
        return germinated / (sown / 100.0);
    }

    public void recalculates() {
        died = 0;
        removed = 0;
        for (SeedlingsPopulationLog log : populationLogs) {
            removed += log.removed;
            germinated += log.germinated;
            died += log.died;
        }
    }

}
