package botalibrium;

import botalibrium.dto.output.Page;
import botalibrium.entity.Batch;
import botalibrium.entity.embedded.PlantMaterial;
import botalibrium.entity.embedded.containers.EmptyContainer;
import botalibrium.entity.embedded.containers.TemporalStringTuple;
import botalibrium.service.BatchesService;
import botalibrium.service.exception.ServiceException;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static botalibrium.TemporalVariableTest.ELVES_MEDIA;
import static botalibrium.TemporalVariableTest.FAIRY_FUNGI_MEDIA;
import static botalibrium.service.temporal.TemporalVariableHelper.END_OF_TIME;
import static org.junit.Assert.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {JUnitConfig.class, Context.class})
public class ApplicationTests {

    @Inject
    private BatchesService bs;
    Timestamp dec1966 = new Timestamp(new DateTime(1966,12, 1,0,0).getMillis());

    Timestamp jan2017 = new Timestamp(new DateTime(2017,1, 1,0,0).getMillis());
    Timestamp feb2017 = new Timestamp(new DateTime(2017,2, 1,0,0).getMillis());
    Timestamp mar2017 = new Timestamp(new DateTime(2017,3, 1,0,0).getMillis());

    Timestamp may2017 = new Timestamp(new DateTime(2017,5, 1,0,0).getMillis());
    Timestamp jun2017 = new Timestamp(new DateTime(2017,6, 1,0,0).getMillis());
    Timestamp jul2017 = new Timestamp(new DateTime(2017,7, 1,0,0).getMillis());

    public void createBatch(String tag, TemporalStringTuple ... items) {
        Batch b = new Batch();
        PlantMaterial material = new PlantMaterial();
        material.setTaxon("Nepenthes apruverus");
        material.setSupplier("eBay/test");
        b.setMaterial(material);
        EmptyContainer c = new EmptyContainer();
        c.getMedia().addAll(Arrays.asList(items));
        c.setTag(tag);
        b.getContainers().add(c);
        bs.save(b);
    }

    //@Before
    public void startFromScratch() {
        bs.truncate();
    }

    @Test(expected = ServiceException.class)
    public void duplicatedTagTest() {
        createBatch("A");
        createBatch("A");
    }

    @Test
    public void notDuplicatedTagTest() {
        createBatch("B");
        createBatch("C");
    }

    @Test
    public void filter2Test() {
        createBatch("FAI-001",
                new TemporalStringTuple(feb2017, FAIRY_FUNGI_MEDIA));
        createBatch("FAI-002",
                new TemporalStringTuple(dec1966, ELVES_MEDIA),
                new TemporalStringTuple(jul2017, FAIRY_FUNGI_MEDIA));
        Page p1 = bs.mediaSearch(FAIRY_FUNGI_MEDIA, jan2017, feb2017, 1, 25, null);
        assertEquals(1, p1.getItems().size());
        EmptyContainer container = (EmptyContainer) p1.getItems().get(0);
        assertEquals("FAI-002", container.getTag());

        Page p2 = bs.mediaSearch(FAIRY_FUNGI_MEDIA, jun2017, END_OF_TIME, 1, 25, null);
        assertEquals(1, p2.getItems().size());
        EmptyContainer container2 = (EmptyContainer) p2.getItems().get(0);
        assertEquals("FAI-001", container2.getTag());

        p2 = bs.mediaSearch(FAIRY_FUNGI_MEDIA, jul2017, new Timestamp(new DateTime().getMillis()), 1, 25, null);
        assertEquals(2, p2.getItems().size());
    }

    @Test
    public void filterTestA() {
        createBatch("filterTestA-01",
                new TemporalStringTuple(dec1966, ELVES_MEDIA),
                new TemporalStringTuple(feb2017, FAIRY_FUNGI_MEDIA));
        createBatch("filterTestA-02",
                new TemporalStringTuple(jun2017, ELVES_MEDIA));
        Page p1 = bs.mediaSearch(ELVES_MEDIA, jun2017, feb2017, 1, 25);
        assertEquals(2, p1.getItems().size());

    }

    public void createDifferentMediaContainer(String tag, List<TemporalStringTuple> media) {
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
