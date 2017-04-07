package botalibrium;

import botalibrium.entity.PlantFile;
import botalibrium.entity.PlantMaterial;
import botalibrium.entity.Supplier;
import botalibrium.entity.Taxon;
import botalibrium.entity.base.CustomFieldGroupDefinition;
import botalibrium.entity.embedded.Record;
import botalibrium.service.RecordsService;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.FileNotFoundException;
import java.net.URL;

@SpringBootApplication
public class Application {
    private static final String[] REQUIRED_PROPERTIES_FILES = {"application.yaml"};

    public static void main(String[] args) throws FileNotFoundException {
        String classpath = System.getProperty("java.class.path");
        System.out.println(classpath);
        for (String fileName : REQUIRED_PROPERTIES_FILES) {
            URL url = Application.class.getClassLoader().getResource(fileName);
            if (url == null) {
                throw new FileNotFoundException(fileName + " was not found in classpath");
            }
        }

        new SpringApplication(Application.class).run(args);
    }

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
    public CommandLineRunner init(RecordsService service) {
        return (args) -> {
            Supplier supplier = new Supplier("eBay");
            PlantMaterial pmi = new PlantMaterial(supplier, "Seeds", (long) 100);
            PlantFile ci = new PlantFile("TAG1", new Taxon("N.Foo"), pmi);
            for (int ii = 0; ii < 2; ii++) {
                ci.getRecords().add(new Record());
            }
            service.create(ci);

            PlantMaterial pmi1 = new PlantMaterial(supplier, "Explant", (long) 20);
            PlantFile ci1 = new PlantFile("TAG2", new Taxon("N.Bar"), pmi1);

            service.create(ci1);

        };
    }
}
