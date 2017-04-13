package botalibrium;

import botalibrium.entity.PlantFile;
import botalibrium.entity.PlantMaterial;
import botalibrium.entity.Supplier;
import botalibrium.entity.Taxon;
import botalibrium.entity.base.CustomFieldGroupDefinition;
import botalibrium.service.CustomFieldsService;
import botalibrium.service.exception.TaxaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.util.Collection;

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
    public CommandLineRunner init(ObjectMapper mapper, CustomFieldsService cfs, TaxaService ts) {
        return (args) -> {
            if (args.length != 1) {
                return;
            }
            Collection<File> files = org.apache.commons.io.FileUtils.listFiles(new File(args[0]), new String[]{"txt"}, false);
            for (File file : files) {
                if (!file.isFile()) {
                    continue;
                }
                if (file.getName().startsWith(CustomFieldGroupDefinition.class.getSimpleName())) {
                    cfs.save(mapper.readValue(file, CustomFieldGroupDefinition.class));
                }
                if (file.getName().startsWith(Taxon.class.getSimpleName())) {
                    ts.save(mapper.readValue(file, Taxon.class));
                }
            }
        };
    }
}
