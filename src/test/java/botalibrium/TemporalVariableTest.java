package botalibrium;

import botalibrium.entity.embedded.containers.TemporalStringTuple;
import botalibrium.service.temporal.TemporalVariableHelper;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

public class TemporalVariableTest {
    public static final String MAGIC_MUSHROOMS_MEDIA = "MAGIC MUSHROOMS MEDIA";
    public static final String FAIRY_FUNGI_MEDIA = "FAIRY FUNGI MEDIA";
    public static final String ELVES_MEDIA = "ELVES MEDIA";
    public static final String REDWOOD_SAWDUST = "REDWOOD SAWDUST";

    @Test
    public void multipleRecordsHistoryTest() {
        List<TemporalStringTuple> media = new ArrayList<>();
        media.add(new TemporalStringTuple(
                new DateTime(2017,1, 1,1,1).getMillis(),
                MAGIC_MUSHROOMS_MEDIA));
        media.add(new TemporalStringTuple(
                new DateTime(2018,1, 1,1,1).getMillis(),
                FAIRY_FUNGI_MEDIA));
        media.add(new TemporalStringTuple(
                new DateTime(2017,4, 1,1,1).getMillis(),
                ELVES_MEDIA));
        media.add(new TemporalStringTuple(
                new DateTime(2017,1, 2,1,1).getMillis(),
                REDWOOD_SAWDUST));
        TemporalVariableHelper.align(media);
        assertEquals(MAGIC_MUSHROOMS_MEDIA, media.get(0).getValue());
        assertEquals(REDWOOD_SAWDUST, media.get(1).getValue());
        assertEquals(ELVES_MEDIA, media.get(2).getValue());
        assertEquals(FAIRY_FUNGI_MEDIA, media.get(3).getValue());
    }

    @Test
    public void singleRecordHistoryTest() {
        List<TemporalStringTuple> media = new ArrayList<>();
        media.add(new TemporalStringTuple(
                new DateTime(2017,1, 1,1,1).getMillis(),
                MAGIC_MUSHROOMS_MEDIA));
        TemporalVariableHelper.align(media);
        assertEquals(MAGIC_MUSHROOMS_MEDIA, media.get(0).getValue());
        assertNull(media.get(0).getEffectiveTo());
    }

}
