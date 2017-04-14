package botalibrium.utilities;

import botalibrium.entity.Taxon;
import botalibrium.entity.base.CustomFieldGroupDefinition;
import botalibrium.service.CustomFieldsService;
import botalibrium.service.exception.TaxaService;
import botalibrium.service.exception.ValidationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

@Component
public class YamlImportUtility {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private CustomFieldsService cfs;
    @Autowired
    private TaxaService ts;


    public void importFromDirectory(String directory) throws IOException, ValidationException {
        Collection<File> files = org.apache.commons.io.FileUtils.listFiles(new File(directory), new String[]{"txt"}, false);
        for (File file : files) {
            if (!file.isFile()) {
                continue;
            }
            if (file.getName().startsWith(CustomFieldGroupDefinition.class.getSimpleName())) {
                cfs.save(mapper.readValue(file, CustomFieldGroupDefinition.class));
            }
            if (file.getName().startsWith(Taxon.class.getSimpleName())) {
                ts.save(mapper.readValue(file, Taxon.class));
            }
            logger.info("Entity imported from file " + file);
        }
    }

}
