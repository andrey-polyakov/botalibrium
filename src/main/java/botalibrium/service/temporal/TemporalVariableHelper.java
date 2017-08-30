package botalibrium.service.temporal;

import botalibrium.entity.embedded.containers.TemporalTuple;
import botalibrium.service.exception.ServiceException;
import org.joda.time.DateTime;

import java.util.Collections;
import java.util.List;

public class TemporalVariableHelper {
    public static <E> List<TemporalTuple<E>> align(List<TemporalTuple<E>> t) {
        Collections.sort(t);
        for (int ii = 0; ii < t.size(); ii ++) {
            TemporalTuple<E> thisItem = t.get(ii);
            TemporalTuple nextItem;
            if (ii + 1 < t.size()) {
                nextItem = t.get(ii + 1);
                thisItem.setEffectiveTo(new DateTime(nextItem.getEffectiveFrom()).minusMillis(1).toDate());
                if (thisItem.getEffectiveTo().compareTo(thisItem.getEffectiveFrom()) < 0) {
                    throw new ServiceException("Invalid temporal sequence for field 'Media'");
                }
            }
        }
        return t;
    }
}
