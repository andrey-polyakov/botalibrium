package botalibrium.entity.embedded.containers;


import botalibrium.dta.output.BatchDto;
import botalibrium.entity.embedded.records.Record;
import botalibrium.references.SizeChart;
import lombok.Data;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.utils.IndexDirection;

import java.util.*;

@Data
@Entity
@Embedded
public class EmptyContainer {
    @Indexed(value = IndexDirection.ASC, name = "tag_index", unique = true, dropDups = true)
    protected String tag;
    protected String description = "";
    @Embedded
    protected List<Record> records = new LinkedList<>();
    @Embedded
    protected Set<String> media = new HashSet<>();
    protected SizeChart plantSize = SizeChart.NA;
    protected int removed = 0;
    protected int died = 0;

    public int getInitialPopulation() {
        return 0;
    }

    @Data
    public static class PopulationLog implements Comparable<PopulationLog> {
        protected int died = 0;
        protected int removed = 0;
        protected Date date = new Date();

        @Override
        public int compareTo(PopulationLog plantsCountLog) {
            return date.compareTo(plantsCountLog.date);
        }

        public BatchDto.PopulationLogDto toDto() {
            BatchDto.PopulationLogDto dto = new BatchDto.PopulationLogDto();
            dto.setTimestamp(date);
            dto.setDied(died);
            dto.setRemoved(removed);
            return dto;
        }
    }

    public BatchDto.EmptyContainerDto toDto(boolean showOnlyData) {
        BatchDto.EmptyContainerDto dto = new BatchDto.EmptyContainerDto();
        populateDtoFields(dto);
        return dto;
    }

    public BatchDto.EmptyContainerDto toCompleteDto() {
        return toDto(true);
    }

    protected void populateDtoFields(BatchDto.EmptyContainerDto dto) {
        dto.setRecords(records);
        dto.setDescription(description);
        dto.setTag(tag);
        dto.setMedia(media);
        dto.setPlantSize(plantSize);
        dto.setRecords(records);
    }

}