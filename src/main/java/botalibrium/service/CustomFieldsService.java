package botalibrium.service;

import botalibrium.entity.base.BaseEntity;
import botalibrium.entity.base.CustomFieldDefinition;
import botalibrium.entity.base.CustomFieldGroup;
import botalibrium.entity.base.CustomFieldGroupDefinition;
import botalibrium.service.exception.ServiceException;
import botalibrium.service.exception.ValidationException;
import org.bson.types.ObjectId;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
import java.util.TreeSet;

/**
 * Created by apolyakov on 4/5/2017.
 */
@Component
public class CustomFieldsService implements CustomFieldsServiceContract {

    private final BasicDAO<CustomFieldGroupDefinition, ObjectId> customFields;

    @Autowired
    public CustomFieldsService(BasicDAO<CustomFieldGroupDefinition, ObjectId> dao) {
        this.customFields = dao;
    }

    @Override
    public void create(CustomFieldGroupDefinition customFieldGroupDefinition) {
        customFields.save(customFieldGroupDefinition);
    }

    @Override
    public void validate(CustomFieldGroup group, Class<BaseEntity> entityClass) throws ServiceException {
        if (!group.getDefinition().getApplicableEntities().contains(entityClass.getCanonicalName().toString())) {
            throw new ValidationException("Given Field Group is not compatible with given entity");
        }
        TreeSet<String> unrecognizedFields = new TreeSet<>(group.getFields().keySet());
        unrecognizedFields.removeAll(group.getDefinition().getCustomFieldDefinitions().keySet());
        if (!unrecognizedFields.isEmpty()) {
            throw new ValidationException("Some fields are not present in Field Group and thus not allowed").
                    set("UNRECOGNIZED_FIELDS", unrecognizedFields);
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
        //customFields.get();
    }
}
