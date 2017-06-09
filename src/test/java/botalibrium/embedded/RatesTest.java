package botalibrium.embedded;

import botalibrium.entity.embedded.containers.SeedsCommunityContainer;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by apolyakov on 6/8/2017.
 */
public class RatesTest {

    @Test
    public void germinationRateTest() {
        SeedsCommunityContainer scc = new SeedsCommunityContainer();
        scc.setSownSeedsCount(100);
        scc.getCountLogs().add(new SeedsCommunityContainer.CountLog(2, 3, 5, new Date(22332323)));
        assertEquals(10.0, scc.getGerminationRate(), 0.1);
        scc.getCountLogs().add(new SeedsCommunityContainer.CountLog(2, 3, 10, new Date(999999999 * 9999)));
        assertEquals(15.0, scc.getGerminationRate(), 0.1);
    }
}
