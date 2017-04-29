package botalibrium.service;

import botalibrium.entity.Taxon;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class TaxaService {

    private BasicDAO<Taxon, ObjectId> taxa;

    @Autowired
    public TaxaService(BasicDAO<Taxon, ObjectId> taxa) {
        this.taxa = taxa;
    }

    public Taxon save(Taxon t) {
        Key<Taxon> key = taxa.save(t);
        t.setId((ObjectId) key.getId());
        return t;
    }
}
