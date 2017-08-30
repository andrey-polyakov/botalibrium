package botalibrium.entity.embedded.containers;

import lombok.Data;

import java.util.Date;

@Data
public class TemporalTuple<T> implements Comparable<TemporalTuple> {
    private Date effectiveFrom = new Date(), effectiveTo;
    private T value;

    public TemporalTuple(Date effectiveFrom, T value) {
        this.effectiveFrom = effectiveFrom;
        this.value = value;
    }

    public TemporalTuple() {
        //
    }

    @Override
    public int compareTo(TemporalTuple o) {
        return effectiveFrom.compareTo(o.effectiveFrom);
    }
}