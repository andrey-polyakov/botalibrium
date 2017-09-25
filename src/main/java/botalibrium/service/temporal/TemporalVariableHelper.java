package botalibrium.service.temporal;

import botalibrium.entity.embedded.containers.TemporalStringTuple;
import botalibrium.service.exception.ServiceException;
import org.joda.time.DateTime;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TemporalVariableHelper {

    public static final Timestamp END_OF_TIME = new Timestamp(new DateTime(9999,9,9,9,9).getMillis());

    public static List<TemporalStringTuple> align(List<TemporalStringTuple> t) {
        Collections.sort(t);
        for (int ii = 0; ii < t.size(); ii++) {
            TemporalStringTuple thisItem = t.get(ii);
            TemporalStringTuple nextItem;
            if (ii + 1 < t.size()) {
                nextItem = t.get(ii + 1);
                if (thisItem.getEffectiveFrom().equals(nextItem.getEffectiveFrom())) {
                    throw new ServiceException("Invalid temporal sequence for field 'Media': more than one record for the same EffectiveFrom date");
                }
                thisItem.setEffectiveTo(new Timestamp(nextItem.getEffectiveFrom().getTime() - 1));
                if (thisItem.getEffectiveTo().compareTo(thisItem.getEffectiveFrom()) < 0) {
                    throw new ServiceException("Invalid temporal sequence for field 'Media': EffectiveTo past EffectiveFrom");
                }
            }
        }
        return t;
    }

    public static String getValueFor(Date asOfDate, List<TemporalStringTuple> t) {
        if (t == null || t.isEmpty()) {
            return "N/A";
        }
        return null;
    }

}