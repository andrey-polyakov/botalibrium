package botalibrium.service;

import botalibrium.entity.PlantMaterial;
import botalibrium.entity.Taxon;
import botalibrium.service.exception.ValidationException;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by apolyakov on 5/1/2017.
 */
@Component
public class PlantMaterialService {

    private BasicDAO<PlantMaterial, ObjectId> pm;
    private BasicDAO<Taxon, ObjectId> taxa;

    @Autowired
    public PlantMaterialService(BasicDAO<PlantMaterial, ObjectId> pm, BasicDAO<Taxon, ObjectId> taxa) {
        this.pm = pm;
        this.taxa = taxa;
    }

    public PlantMaterial save(PlantMaterial t) throws ValidationException {
        if (t.getTaxon() != null) {
            if (t.getTaxon().getId() == null) {
                taxa.save(t.getTaxon());
            }
        } else {
            throw new ValidationException("Taxon must be provided for every record");
        }
        Key<PlantMaterial> key = pm.save(t);
        t.setId((ObjectId) key.getId());
        return t;
    }
}
