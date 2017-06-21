package botalibrium.dta.input;

import botalibrium.entity.embedded.records.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apolyakov on 6/16/2017.
 */
public class BulkOperation {
    private List<String> tags = new ArrayList<>();
    private boolean preview = true;
    private Record record;

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public boolean isPreview() {
        return preview;
    }

    public void setPreview(boolean preview) {
        this.preview = preview;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }


}
