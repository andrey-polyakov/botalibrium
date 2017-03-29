package botalibrium;

import botalibrium.entity.*;
import botalibrium.persistence.repository.contract.Repository;
import botalibrium.persistence.repository.implementation.AbstractRepository;
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
			if(url == null) {
				throw new FileNotFoundException(fileName + " was not found in classpath");
			}
		}

		new SpringApplication(Application.class).run(args);
	}

	@Bean
	public AbstractRepository<PlantFile> plantFileRepository() {
		return new AbstractRepository<>();
	}

	@Bean
	public AbstractRepository<Taxon> taxonRepository() {
		return new AbstractRepository<>();
	}

	@Bean
	public AbstractRepository<PlantMaterial> plantMaterialRepository() {
		return new AbstractRepository<>();
	}

	@Bean
	public AbstractRepository<Supplier> supplierRepository() {
		return new AbstractRepository<>();
	}

	@Bean
	public CommandLineRunner init(Repository<PlantFile> fr, Repository<PlantMaterial> pm, Repository<Supplier> sup) {
		return (args) -> {
			Supplier supplier = new Supplier("eBay");
			sup.save(supplier);
			PlantMaterial pmi = new PlantMaterial(supplier, "Seeds", (long) 100);
			pm.save(pmi);
			PlantFile ci = new PlantFile("TAG1", new Taxon("N.Foo"), pmi);
			ci.getRecords().add(new Record());
			fr.save(ci);


		};
	}
}
