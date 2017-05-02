package botalibrium;

import botalibrium.entity.Container;
import botalibrium.entity.PlantMaterial;
import botalibrium.entity.Taxon;
import botalibrium.entity.base.CustomFieldGroupDefinition;
import botalibrium.service.ContainersService;
import botalibrium.utilities.YamlImportUtility;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Context {

    @Autowired
    private Datastore datastore;

    @Bean
    public BasicDAO<CustomFieldGroupDefinition, ObjectId> CustomFieldGroupDefinitionsRepository() {
        return new BasicDAO<>(CustomFieldGroupDefinition.class, datastore);
    }

    @Bean
    public BasicDAO<Container, ObjectId> plantFilesRepository() {
        return new BasicDAO<>(Container.class, datastore);
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
    public CommandLineRunner init(YamlImportUtility importUtility, ContainersService rs) {
        return (args) -> {
            if (args.length == 1) {
                importUtility.importFromDirectory(args[0]);
            }
            List<Container> tags = rs.search("TAG");
            for (Container tag : tags) {
                System.out.println(tag.getId() + " " + tag);
            }
        };
    }
}
