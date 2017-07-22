package botalibrium.entity.embedded.containers;


import botalibrium.dta.input.bulk.PopulationLogDto;
import botalibrium.entity.embedded.records.Record;
import botalibrium.references.SizeChart;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.utils.IndexDirection;

import java.util.*;

@Data
@Entity
@Embedded
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PlantsContainer.class, name = "PlantsContainer"),
        @JsonSubTypes.Type(value = SeedsContainer.class, name = "SeedsContainer") }
)
public class EmptyContainer {
    @Indexed(value = IndexDirection.ASC, name = "tag_index", unique = true, dropDups = true)
    protected String tag;
    protected String description = "";
    @Embedded
    protected List<Record> records = new LinkedList<>();
    @Embedded
    private Set<String> media = new HashSet<>();
    protected SizeChart plantSize = SizeChart.NA;
    protected int removed = 0;
    protected int died = 0;

    public int getPopulation() {
        return 0;
    }

    @Data
    public static class PopulationLog implements Comparable<PopulationLog> {
        protected int died = 0;
        protected int removed = 0;
        private Date date = new Date();

        @Override
        public int compareTo(PopulationLog plantsCountLog) {
            return date.compareTo(plantsCountLog.date);
        }

        public PopulationLogDto toDto() {
            PopulationLogDto dto = new PopulationLogDto();
            dto.setDate(date);
            dto.setDied(died);
            dto.setRemoved(removed);
            return dto;
        }
    }
}
