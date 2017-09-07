package botalibrium.entity.embedded.containers;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TemporalStringTuple extends TemporalTuple {
    private String value;

    public TemporalStringTuple(Long effectiveFrom, String value) {
        this.effectiveFrom = new Timestamp(effectiveFrom);
        this.value = value;
    }

    public TemporalStringTuple() {
        //
    }

    public TemporalStringTuple(Timestamp ts, String v) {
        this.effectiveFrom = ts;
        this.value = v;
    }
}