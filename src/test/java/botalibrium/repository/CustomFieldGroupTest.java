package botalibrium.repository;

import botalibrium.ApplicationTests;
import botalibrium.Context;
import botalibrium.entity.base.CustomFieldGroup;
import botalibrium.entity.base.CustomFieldGroupDefinition;
import botalibrium.entity.embedded.records.Record;
import botalibrium.service.contract.CustomFieldsServiceContract;
import botalibrium.service.exception.ServiceException;
import botalibrium.service.exception.ValidationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;

@ContextConfiguration(classes = Context.class)
public class CustomFieldGroupTest extends ApplicationTests {
/*
    @Autowired
	private CustomFieldsServiceContract customFieldsService;
    @Autowired
    private ObjectMapper mapper;

    private CustomFieldGroupDefinition definition1, definition2;

    @Before
    public void setup() throws IOException {
        definition1 = loadCustomFieldGroupDefinition("/CustomFieldGroupDefinition/RecordOfFertilization.yaml");
        definition2 = loadCustomFieldGroupDefinition("/CustomFieldGroupDefinition/RecordOfFertilization2.yaml");
    }

    @Test(expected = ServiceException.class)
    public void incompatibleEntityTest() throws ServiceException, IOException {
        CustomFieldGroup customFieldGroup = new CustomFieldGroup();
        customFieldGroup.setDefinition(definition1);
        customFieldsService.validate(customFieldGroup, String.class.getSimpleName());
    }

    @Test
	public void successfulTest() throws ServiceException, IOException {
        CustomFieldGroup customFieldGroup = loadCustomFieldGroup("/CustomFieldGroup/RecordOfFertilization-1.yaml");
        customFieldGroup.setDefinition(definition1);
        customFieldsService.validate(customFieldGroup, Record.class.getSimpleName());
	}

    @Test(expected = ServiceException.class)
    public void missingNodeTest() throws ServiceException, IOException {
        CustomFieldGroup customFieldGroup = loadCustomFieldGroup("/CustomFieldGroup/RecordOfFertilization-2.yaml");
        customFieldGroup.setDefinition(definition1);
        customFieldsService.validate(customFieldGroup, Record.class.getSimpleName());
    }

    @Test(expected = ServiceException.class)
    public void noNodes2Test() throws ServiceException, IOException {
        CustomFieldGroup customFieldGroup = loadCustomFieldGroup("/CustomFieldGroup/RecordOfFertilization-noNodes.yaml");
        customFieldGroup.setDefinition(definition1);
        customFieldsService.validate(customFieldGroup, Record.class.getSimpleName());
    }

    @Test(expected = ServiceException.class)
    public void noNodesTest() throws ServiceException, IOException {
        CustomFieldGroup customFieldGroup = loadCustomFieldGroup("/CustomFieldGroup/RecordOfFertilization-0.yaml");
        customFieldGroup.setDefinition(definition1);
        customFieldsService.validate(customFieldGroup, Record.class.getSimpleName());
    }

    @Test(expected = ServiceException.class)
    public void secondNoNodesTest() throws ServiceException, IOException {
        CustomFieldGroup customFieldGroup = new CustomFieldGroup();
        customFieldGroup.setDefinition(definition1);
        customFieldsService.validate(customFieldGroup, Record.class.getSimpleName());
    }

    @Test(expected = ServiceException.class)
    public void unrecognizedNodesTest() throws ServiceException, IOException {
        CustomFieldGroup customFieldGroup = loadCustomFieldGroup("/CustomFieldGroup/RecordOfFertilization-3.yaml");
        customFieldGroup.setDefinition(definition1);
        customFieldsService.validate(customFieldGroup, Record.class.getSimpleName());
    }

    @Test(expected = ServiceException.class)
    public void moreThanOneNodeInMutuallyExclusiveCaseTest() throws ServiceException, IOException {
        CustomFieldGroup customFieldGroup = loadCustomFieldGroup("/CustomFieldGroup/RecordOfFertilization-4.yaml");
        customFieldGroup.setDefinition(definition1);
        customFieldsService.validate(customFieldGroup, Record.class.getSimpleName());
    }

    @Test(expected = ValidationException.class)
    public void missingVariablesTest() throws ServiceException, IOException {
        CustomFieldGroup customFieldGroup = loadCustomFieldGroup("/CustomFieldGroup/RecordOfFertilization-variables-1.yaml");
        customFieldGroup.setDefinition(definition1);
        customFieldsService.validate(customFieldGroup, Record.class.getSimpleName());
    }

    @Test(expected = ValidationException.class)
    public void unrecognizedVariablesTest() throws ServiceException, IOException {
        CustomFieldGroup customFieldGroup = loadCustomFieldGroup("/CustomFieldGroup/RecordOfFertilization-variables-2.yaml");
        customFieldGroup.setDefinition(definition1);
        customFieldsService.validate(customFieldGroup, Record.class.getSimpleName());
    }

    @Test(expected = ValidationException.class)
    public void invalidVariableValueTest() throws ServiceException, IOException {
        CustomFieldGroup customFieldGroup = loadCustomFieldGroup("/CustomFieldGroup/RecordOfFertilization-wrong-value.yaml");
        customFieldGroup.setDefinition(definition1);
        customFieldsService.validate(customFieldGroup, Record.class.getSimpleName());
    }

    private CustomFieldGroupDefinition loadCustomFieldGroupDefinition(String name) throws IOException {
        return mapper.readValue(this.getClass().getResource(name), CustomFieldGroupDefinition.class);
    }

    private CustomFieldGroup loadCustomFieldGroup(String name) throws IOException {
        return mapper.readValue(this.getClass().getResource(name), CustomFieldGroup.class);
    }
*/
}
