package botalibrium.service;

import botalibrium.entity.Container;
import botalibrium.service.exception.ValidationException;
import org.bson.types.ObjectId;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.FindOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ContainersService {
    @Autowired
    private PlantMaterialService plantMaterials;
    @Autowired
    private BasicDAO<Container, ObjectId> containers;

    public void create(Container newFile) throws ValidationException {
        if (newFile.getMaterial() != null && newFile.getMaterial().getId() == null) {
            plantMaterials.save(newFile.getMaterial());
        }
        containers.save(newFile);
    }

    public List<Container> search(String text) throws ValidationException {
        return search(text, 1, 5);
    }

    public List<Container> search(String text, int page, int limit) throws ValidationException {
        return containers.createQuery().
                field(Container.TAG_FIELD).
                containsIgnoreCase(text).order().asList(new FindOptions().skip(page - 1).limit(limit));
    }


}
