package botalibrium.repository;

import botalibrium.Context;
import botalibrium.ApplicationTests;
import botalibrium.entity.PlantFile;
import botalibrium.entity.base.CustomFieldDefinition;
import botalibrium.entity.base.CustomFieldGroup;
import botalibrium.entity.base.CustomFieldGroupDefinition;
import botalibrium.service.contract.CustomFieldsServiceContract;
import botalibrium.service.exception.ServiceException;
import botalibrium.service.exception.ValidationException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

@ContextConfiguration(classes = Context.class)
public class CustomFieldGroupTest extends ApplicationTests {

    public static final String EXISTING_FIELD = "Foo";
    public static final String REQUIRED_FIELD = "Bar";

    public static final String UNKNOWN_FIELD = "Does not exist";
    public static final String EXISTING_OPTION = "Yes";
    public static final String UNKNOWN_OPTION = "Maybe";

    @Autowired
	private CustomFieldsServiceContract customFieldsService;


	public static final String PLANT_FILE_CLASS = PlantFile.class.getCanonicalName();

	@Test
	public void successfulTest() throws ServiceException {
        Map<String, String> fields = new TreeMap<>();
        fields.put(REQUIRED_FIELD, EXISTING_OPTION);
        testWithFieldValue(fields);
	}

    @Test(expected = ValidationException.class)
    public void unknownOptionTest() throws ServiceException {
        Map<String, String> fields = new TreeMap<>();
        fields.put(REQUIRED_FIELD, UNKNOWN_OPTION);
        testWithFieldValue(fields);
    }

    @Test(expected = ValidationException.class)
    public void noFieldsTest() throws ServiceException {
        testWithFieldValue(Collections.emptyMap());
	}

    @Test(expected = ValidationException.class)
    public void unknownFieldTest() throws ServiceException {
	    Map<String, String> fields = new TreeMap<>();
        fields.put(REQUIRED_FIELD, EXISTING_OPTION);
        fields.put(UNKNOWN_FIELD, EXISTING_OPTION);
        testWithFieldValue(fields);
    }

    @Test(expected = ValidationException.class)
    public void missingRequiredFieldTest() throws ServiceException {
        Map<String, String> fields = new TreeMap<>();
        fields.put(EXISTING_FIELD, EXISTING_OPTION);
        testWithFieldValue(fields);
	}


    private void testWithFieldValue(Map<String, String> fieldsMap) throws ServiceException {
        CustomFieldGroupDefinition newGroupDefinition = newCustomFieldGroupDefinition();
        CustomFieldGroup sfg = new CustomFieldGroup();
        sfg.setDefinition(newGroupDefinition);
        sfg.getFields().putAll(fieldsMap);
        customFieldsService.validate(sfg, PLANT_FILE_CLASS);
    }

    private CustomFieldGroupDefinition newCustomFieldGroupDefinition() {
        CustomFieldGroupDefinition newGroupDefinition = new CustomFieldGroupDefinition();
        newGroupDefinition.getApplicableEntities().add(PLANT_FILE_CLASS);
        newGroupDefinition.setDescription("A custom group of fields");
        CustomFieldDefinition fieldDefinition = new CustomFieldDefinition();
        fieldDefinition.setDescription("Not mandatory");
        fieldDefinition.setType("String");
        fieldDefinition.getOptions().add(EXISTING_OPTION);
        fieldDefinition.getOptions().add("No");
        fieldDefinition.setMandatory(false);
        newGroupDefinition.getCustomFieldDefinitions().put(EXISTING_FIELD, fieldDefinition);
        CustomFieldDefinition fieldDefinition2 = new CustomFieldDefinition();
        fieldDefinition2.setDescription("Particular field note");
        fieldDefinition2.setType("String");
        fieldDefinition2.getOptions().add(EXISTING_OPTION);
        fieldDefinition2.getOptions().add("No");
        fieldDefinition2.setMandatory(true);
        newGroupDefinition.getCustomFieldDefinitions().put(REQUIRED_FIELD, fieldDefinition2);
        return newGroupDefinition;
    }

}
