package botalibrium.service;

import botalibrium.entity.PlantFile;
import botalibrium.entity.PlantMaterial;
import botalibrium.entity.Supplier;
import botalibrium.entity.Taxon;
import botalibrium.service.exception.ServiceException;
import botalibrium.service.exception.TaxaService;
import org.bson.types.ObjectId;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class RecordsService {
    @Autowired
    private BasicDAO<PlantMaterial, ObjectId> plantMaterials;
    @Autowired
    private BasicDAO<PlantFile, ObjectId> plantFiles;
    @Autowired
    private BasicDAO<Supplier, ObjectId> suppliers;
    @Autowired
    private TaxaService taxaService;


    public void create(PlantFile newFile) {
        if (newFile.getParent() == null || newFile.getParent().equals(newFile)) {
            new ServiceException("This item could not be its parent");//TODO Hierarchy check
        }
        if (newFile.getMaterial() != null) {
            create(newFile.getMaterial());
        }
        if (newFile.getTaxon() != null && newFile.getTaxon().getId() == null) {
            taxaService.save(newFile.getTaxon());
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


}
