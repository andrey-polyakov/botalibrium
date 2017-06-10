package botalibrium.service;

import botalibrium.dta.Page;
import botalibrium.entity.Batch;
import botalibrium.entity.base.CustomFieldGroup;
import botalibrium.entity.embedded.Record;
import botalibrium.entity.embedded.containers.Container;
import botalibrium.service.exception.ServiceException;
import botalibrium.service.exception.ValidationException;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Component
public class ContainersService {
    @Autowired
    private BasicDAO<Batch, ObjectId> batches;
    @Autowired
    private CustomFieldsService customFieldsService;


    public Key<Batch> save(Batch batch) throws ServiceException {
        for (CustomFieldGroup cfg : batch.getCustomFieldGroups()) {
            customFieldsService.validate(cfg, Batch.class.getSimpleName());
        }
        for (Container pg : batch.getContainers()) {
            for (CustomFieldGroup cfg : pg.getCustomFields()) {
                customFieldsService.validate(cfg, Batch.class.getSimpleName());
            }
            for (Record r : pg.getRecords()) {
                for (CustomFieldGroup cfg : r.getCustomFields()) {
                    customFieldsService.validate(cfg, Batch.class.getSimpleName());
                }
            }
        }
        return batches.save(batch);
    }

    public void addRecord(ObjectId id, Record r) throws ServiceException {
        Batch c = batches.get(id);
        if (c == null) {
            throw new ServiceException("Record not found");
        }
        for (CustomFieldGroup field : r.getCustomFields()) {
            customFieldsService.validate(field, Record.class.getSimpleName());
        }
        c.getRecords().add(r);
    }

    public void delete(Batch c) throws ServiceException {
        batches.delete(c);
    }

    public Page basicSearch(String query, int page, int limit) throws ValidationException {
        int defaultLimit = 25;
        if (limit > 0) {
            defaultLimit = limit;
        }
        Query<Batch> q = batches.createQuery();
        List<String> tags = new ArrayList<>();
        tags.add(query);
        if (query != null && !query.isEmpty()) {
            q.or(
                    q.criteria("material.taxon").containsIgnoreCase(query),
                    q.criteria("containers.tag").containsIgnoreCase(query));
        }
        List<Batch> list = q.order().asList(new FindOptions().skip(page - 1).limit(defaultLimit));
        return new Page(list, page);
    }

    public Batch getContainer(ObjectId id) throws ServiceException {
        Batch c = batches.get(id);
        if (c == null) {
            throw new ServiceException("Record not found");
        }
        return c;
    }


}
