package botalibrium.service;

import botalibrium.entity.PlantFile;
import botalibrium.entity.PlantMaterial;
import botalibrium.entity.Supplier;
import botalibrium.entity.Taxon;
import botalibrium.service.exception.ServiceException;
import org.bson.types.ObjectId;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class RecordsService {

    private BasicDAO<PlantMaterial, ObjectId> plantMaterials;
    private BasicDAO<PlantFile, ObjectId> plantFiles;
    private BasicDAO<Supplier, ObjectId> suppliers;
    private BasicDAO<Taxon, ObjectId> taxa;

    @Autowired
    public RecordsService(BasicDAO<PlantMaterial, ObjectId> plantMaterials, BasicDAO<PlantFile, ObjectId> plantFiles, BasicDAO<Supplier, ObjectId> suppliers, BasicDAO<Taxon, ObjectId> taxa) {
        this.plantMaterials = plantMaterials;
        this.plantFiles = plantFiles;
        this.suppliers = suppliers;
        this.taxa = taxa;
    }

    public void create(PlantFile newFile) {
        if (newFile.getParent() == null || newFile.getParent().equals(newFile)) {
            new ServiceException("This item could not be its parent");//TODO Hierarchy check
        }
        if (newFile.getTaxon() != null) {
            create(newFile.getTaxon());
        }
        if (newFile.getMaterial() != null) {
            create(newFile.getMaterial());
        }
        plantFiles.save(newFile);
    }

    public void create(PlantMaterial material) {
        if (material.getSupplier() != null) {
            create(material.getSupplier());
        }
        plantMaterials.save(material);
    }

    public void create(Supplier supplier) {
        suppliers.save(supplier);
    }

    public void create(Taxon taxon) {
        taxa.save(taxon);
    }

}
