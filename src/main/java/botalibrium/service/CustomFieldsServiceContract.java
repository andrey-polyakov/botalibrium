package botalibrium.service;

import botalibrium.entity.base.BaseEntity;
import botalibrium.entity.base.CustomFieldGroup;
import botalibrium.entity.base.CustomFieldGroupDefinition;
import botalibrium.service.exception.ServiceException;
import botalibrium.service.exception.ValidationException;

import java.util.Map;

/**
 * Created by apolyakov on 4/5/2017.
 */
public interface CustomFieldsServiceContract {

    void create(CustomFieldGroupDefinition customFieldGroupDefinition);

    void validate(CustomFieldGroup group, Class<BaseEntity> entityClass) throws ServiceException;
}
