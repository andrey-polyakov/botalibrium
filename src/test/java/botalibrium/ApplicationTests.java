package botalibrium;

import botalibrium.entity.Batch;
import botalibrium.entity.embedded.PlantMaterial;
import botalibrium.entity.embedded.containers.EmptyContainer;
import botalibrium.entity.embedded.containers.TemporalTuple;
import botalibrium.service.BatchesService;
import botalibrium.service.exception.ServiceException;
import botalibrium.service.temporal.TemporalVariableHelper;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {JUnitConfig.class, Context.class})
public class ApplicationTests {

    @Inject
    private BatchesService bs;


    public void createSingleContainerBatch(String tag) {
        Batch b = new Batch();
        PlantMaterial material = new PlantMaterial();
        material.setTaxon("Nepenthes");
        material.setSupplier("eBay/test");
        b.setMaterial(material);
        EmptyContainer c = new EmptyContainer();
        c.setTag(tag);
        b.getContainers().add(c);
        bs.save(b);
    }

    @Test(expected = ServiceException.class)
    public void duplicatedTagTest() {
        createSingleContainerBatch("A");
        createSingleContainerBatch("A");
    }

    @Test
    public void notDuplicatedTagTest() {
        createSingleContainerBatch("B");
        createSingleContainerBatch("C");
    }

    public void createDifferentMediaContainer(String tag, List<TemporalTuple<String>> media) {
        Batch b = new Batch();
        PlantMaterial material = new PlantMaterial("Material");
        b.setMaterial(material);
        EmptyContainer c = new EmptyContainer();
        c.setTag(tag);
        c.setMedia(media);
        b.getContainers().add(c);
        bs.save(b);
    }

}
