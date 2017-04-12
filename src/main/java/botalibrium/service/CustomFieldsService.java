package botalibrium.service;

import botalibrium.entity.base.CustomFieldDefinition;
import botalibrium.entity.base.CustomFieldGroup;
import botalibrium.entity.base.CustomFieldGroupDefinition;
import botalibrium.service.contract.CustomFieldsServiceContract;
import botalibrium.service.exception.ServiceException;
import botalibrium.service.exception.ValidationException;
import org.bson.types.ObjectId;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeSet;

@Component
public class CustomFieldsService implements CustomFieldsServiceContract {

    private final BasicDAO<CustomFieldGroupDefinition, ObjectId> customFields;

    @Autowired
    public CustomFieldsService(BasicDAO<CustomFieldGroupDefinition, ObjectId> dao) {
        this.customFields = dao;
    }

    @Override
    public void create(CustomFieldGroupDefinition customFieldGroupDefinition) throws ValidationException {
        //TODO more checks
        if (customFieldGroupDefinition.getApplicableEntities().isEmpty()) {
            throw new ValidationException("Field Definition must be applicable to at least one entity");
        }
        if (customFieldGroupDefinition.getCustomFieldDefinitions().isEmpty()) {
            throw new ValidationException("At least one Field Definition is required for every group");
        }
        customFields.save(customFieldGroupDefinition);
    }

    @Override
    public void validate(CustomFieldGroup group, String entityClass) throws ServiceException {
        if (!group.getDefinition().getApplicableEntities().contains(entityClass)) {
            throw new ValidationException("Given Field Group is not compatible with given entity");
        }
        if (group.getFields().isEmpty()) {
            throw new ValidationException("At least one field is required for every group");
        }
        TreeSet<String> unrecognizedFields = new TreeSet<>(group.getFields().keySet());
        unrecognizedFields.removeAll(group.getDefinition().getCustomFieldDefinitions().keySet());
        if (!unrecognizedFields.isEmpty()) {
            throw new ValidationException("Some fields are not present in Field Group and thus not allowed").
                    set("UNRECOGNIZED_FIELDS", unrecognizedFields);
        }
        for (Map.Entry<String, CustomFieldDefinition> entry : group.getDefinition().getCustomFieldDefinitions().entrySet()) {
            if (entry.getValue().isMandatory() && !group.getFields().containsKey(entry.getKey())) {
                throw new ValidationException("Missing a mandatory field")
                        .set("FIELD", entry.getKey());
            }
        }
        for (Map.Entry<String, String> entry : group.getFields().entrySet()) {
            if (!group.getDefinition().getCustomFieldDefinitions().containsKey(entry.getKey())) {
                throw new ValidationException("Field is not present in the group")
                        .set("GROUP", group.getDefinition())
                        .set("FIELD", entry.getKey());
            }
            CustomFieldDefinition cfd = group.getDefinition().getCustomFieldDefinitions().get(entry.getKey());
            if (!cfd.getOptions().isEmpty()) {
                if (!cfd.getOptions().contains(entry.getValue())) {
                    throw new ValidationException("Field value must be from the set options provided")
                            .set("GROUP", group.getDefinition())
                            .set("FIELD", entry.getKey())
                            .set("VALUE", entry.getValue());
                }
            }
        }
    }
}
