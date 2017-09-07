package botalibrium;

import botalibrium.entity.Batch;
import botalibrium.entity.embedded.PlantMaterial;
import botalibrium.entity.TaxonDetails;
import botalibrium.entity.base.CustomFieldGroupDefinition;
import botalibrium.service.BatchesService;
import botalibrium.utilities.YamlImportUtility;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Context {

    @Autowired
    private Datastore datastore;

    @Bean
    public BasicDAO<CustomFieldGroupDefinition, ObjectId> CustomFieldGroupDefinitionsRepository() {
        return new BasicDAO<>(CustomFieldGroupDefinition.class, datastore);
    }

    @Bean
    public BasicDAO<Batch, ObjectId> plantFilesRepository() {
        return new BasicDAO<>(Batch.class, datastore);
    }

    @Bean
    public BasicDAO<TaxonDetails, ObjectId> taxaRepository() {
        return new BasicDAO<>(TaxonDetails.class, datastore);
    }

    @Bean
    public BasicDAO<PlantMaterial, ObjectId> plantsMaterialsRepository() {
        return new BasicDAO<>(PlantMaterial.class, datastore);
    }

}
