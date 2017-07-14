package botalibrium.entity.embedded.containers;

import botalibrium.dta.input.bulk.PopulationLogDto;
import botalibrium.references.SizeChart;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import java.util.*;

@Data
@Entity
@Embedded
public class PlantsContainer extends EmptyContainer {
    private LinkedList<PopulationLog> populationLogs = new LinkedList<>();
    protected int population = 0;

    public static PopulationLog fromDto(PopulationLogDto count) {
        PopulationLog plantsCountLog = new PopulationLog();
        plantsCountLog.setDate(count.getDate());
        plantsCountLog.setDied(count.getDied());
        return plantsCountLog;
    }

    public void addCountLog(PopulationLog log) {
        if (population < 1) {
            throw new IllegalStateException("Invalid initial count: container is not populated");
        }// TODO May be more checks here
        populationLogs.add(log);
        removed += log.removed;
        died += log.died;
    }

    public double getDeathRate() {
        return died / (population / 100.0);
    }

    public void recalculateCounts() {
        died = 0;
        removed = 0;
        for (PopulationLog log : populationLogs) {
            removed += log.removed;
            died += log.died;
        }
    }

    public int getPopulation() {
        return population;
    }

}
