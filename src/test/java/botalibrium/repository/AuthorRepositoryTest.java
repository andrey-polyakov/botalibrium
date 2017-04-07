package botalibrium.repository;

import botalibrium.Application;
import botalibrium.ApplicationTests;
import botalibrium.entity.PlantFile;
import botalibrium.entity.base.CustomFieldDefinition;
import botalibrium.entity.base.CustomFieldGroupDefinition;
import botalibrium.service.RecordsService;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = Application.class)
public class AuthorRepositoryTest extends ApplicationTests {

	@Autowired
	private RecordsService records;
	@Autowired
	private BasicDAO<CustomFieldGroupDefinition, ObjectId> customFieldGroupDefinitions;

	public static final String PLANT_FILE_CLASS = PlantFile.class.getCanonicalName();

	@Test
	public void testSaveCustomFieldForPlantFile() {
		CustomFieldGroupDefinition newGroupDefinition = new CustomFieldGroupDefinition();
		newGroupDefinition.getApplicableEntities().add(PLANT_FILE_CLASS);
		newGroupDefinition.setDescription("A custom group of fields");

		CustomFieldDefinition fieldDefinition = new CustomFieldDefinition();
		fieldDefinition.setDescription("Particular field note");
        fieldDefinition.setType("String");
        fieldDefinition.getOptions().add("Yes");
        fieldDefinition.getOptions().add("No");
		newGroupDefinition.getCustomFieldDefinitions().put("Foo", fieldDefinition);
        customFieldGroupDefinitions.save(newGroupDefinition);
        new String("");
	}

}
