package botalibrium.dta.output;

import botalibrium.dta.input.bulk.PopulationLogDto;
import botalibrium.dta.input.bulk.SeedlingsPopulationLogDto;
import botalibrium.entity.embedded.PlantMaterial;
import botalibrium.entity.embedded.containers.EmptyContainer;
import botalibrium.entity.embedded.records.Record;
import botalibrium.references.SizeChart;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class BatchDto {

    private PlantMaterial material;
    private List<EmptyContainer> containers = new ArrayList<>();
    private List<Record> records = new ArrayList<>();
    private Timestamp started;

    @Data
    class EmptyContainerDto {
        protected String tag;
        protected String description;
        protected List<PopulationLogDto> records = new ArrayList<>();
        protected Set<String> media = new HashSet<>();
        protected SizeChart plantSize = SizeChart.NA;
    }

    @Data
    class SeedsContainerDto extends EmptyContainerDto{
        private List<SeedlingsPopulationLogDto> populationLogs = new ArrayList<>();
        private String pretreatment;
        private int germinated = 0;
        private int sown = 0;
    }

    @Data
    class PlantsContainerDto extends EmptyContainerDto{
        private List<PopulationLogDto> populationLogs = new ArrayList<>();
        protected int population = 0;
    }

}
