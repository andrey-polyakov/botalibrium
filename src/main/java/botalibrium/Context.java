package botalibrium;

import botalibrium.entity.PlantFile;
import botalibrium.entity.PlantMaterial;
import botalibrium.entity.Supplier;
import botalibrium.entity.Taxon;
import botalibrium.entity.base.BaseEntity;
import botalibrium.entity.base.CustomFieldGroupDefinition;
import botalibrium.entity.embedded.Record;
import botalibrium.service.RecordsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication
public class Context {

    @Autowired
    private Datastore datastore;

    @Bean
    public BasicDAO<CustomFieldGroupDefinition, ObjectId> CustomFieldGroupDefinitionsRepository() {
        return new BasicDAO<>(CustomFieldGroupDefinition.class, datastore);
    }

    @Bean
    public BasicDAO<PlantFile, ObjectId> plantFilesRepository() {
        return new BasicDAO<>(PlantFile.class, datastore);
    }

    @Bean
    public BasicDAO<Taxon, ObjectId> taxaRepository() {
        return new BasicDAO<>(Taxon.class, datastore);
    }

    @Bean
    public BasicDAO<PlantMaterial, ObjectId> plantsMaterialsRepository() {
        return new BasicDAO<>(PlantMaterial.class, datastore);
    }

    @Bean
    public BasicDAO<Supplier, ObjectId> suppliersRepository() {
        return new BasicDAO<>(Supplier.class, datastore);
    }

    @Bean
    public CommandLineRunner init(ObjectMapper mapper) {
        return (args) -> {

/*            BaseEntity reportConfig = null;
            try {
                reportConfig = mapper.readValue(report, ReportConfig.class);
            } catch (IOException e) {
                e.printStackTrace();

                return false;
            }*/

        };
    }
}
