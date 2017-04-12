package botalibrium.service.contract;

import botalibrium.entity.base.CustomFieldGroup;
import botalibrium.entity.base.CustomFieldGroupDefinition;
import botalibrium.service.exception.ServiceException;
import botalibrium.service.exception.ValidationException;

/**
 * Created by apolyakov on 4/5/2017.
 */
public interface CustomFieldsServiceContract {

    void create(CustomFieldGroupDefinition customFieldGroupDefinition) throws ValidationException;

    void validate(CustomFieldGroup group, String entityClass) throws ServiceException;
}
