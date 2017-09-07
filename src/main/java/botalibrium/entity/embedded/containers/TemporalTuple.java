package botalibrium.entity.embedded.containers;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

import static botalibrium.service.temporal.TemporalVariableHelper.END_OF_TIME;

@Data
public class TemporalTuple implements Comparable<TemporalTuple> {
    protected Timestamp effectiveFrom = new Timestamp(new Date().getTime()), effectiveTo = END_OF_TIME;

    @Override
    public int compareTo(TemporalTuple o) {
        return effectiveFrom.compareTo(o.effectiveFrom);
    }
}