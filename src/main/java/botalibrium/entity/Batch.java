package botalibrium.entity;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

import botalibrium.dto.output.BatchDto;
import botalibrium.entity.embedded.PlantMaterial;
import botalibrium.entity.embedded.containers.EmptyContainer;
import botalibrium.entity.embedded.records.Record;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import org.mongodb.morphia.annotations.*;

import botalibrium.entity.base.BaseEntity;

@Data
@Entity
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Batch extends BaseEntity {
    private Timestamp started = new Timestamp(new Date().getTime());
    @Embedded
    private List<EmptyContainer> containers = new ArrayList<>();
    @Embedded
    private List<Record> records = new ArrayList<>();
    @Embedded
    private Set<String> labels = new HashSet<>();
    @Embedded
    private PlantMaterial material;

    public Batch() {
        //
    }

    public BatchDto toDto(boolean showOnlyData) {
        BatchDto dto = new BatchDto();
        dto.setMaterial(material);
        long totalPopulation = 0;
        double totalDeath = 0;
        for (EmptyContainer container : containers) {
            totalPopulation += container.getPopulation();
            totalDeath += container.getDied();
            dto.getContainers().add(container.toDto(showOnlyData));
        }
        dto.setStarted(started);
        dto.setRecords(records);
        dto.setLabels(labels);
        dto.getCalculated().put("population", totalPopulation);
        dto.getCalculated().put("deathRate", totalDeath / totalPopulation);
        dto.getCalculated().put("forSale", 0);
        dto.getCalculated().put("estimatedProfit", 0.0);
        LocalDate firstDate = started.toLocalDateTime().toLocalDate();
        LocalDate secondDate = LocalDate.now();
        Period period = Period.between(firstDate, secondDate);
        int days = period.getDays();
        int months = period.getMonths();
        int years = period.getYears();
        StringBuilder keptFor = new StringBuilder();
        if (years > 0) {
            keptFor.append(years);
            keptFor.append(" years");
        }
        if (months > 0) {
            if (years > 0) {
                keptFor.append(", ");
            }
            keptFor.append(months);
            keptFor.append(" months");
        }
        if (days > 0) {
            if (months > 0 || years > 0) {
                keptFor.append(" and ");
            }
            keptFor.append(days);
            keptFor.append(" days");
        }
        dto.getCalculated().put("keptFor", keptFor.toString());
        return dto;
    }

    public Integer getCount() {
        int count = 0;
        for (EmptyContainer container : containers) {
            count += container.getPopulation();
        }
        return count;
    }

}
