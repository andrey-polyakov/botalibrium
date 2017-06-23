package botalibrium.dta.input.bulk;

import botalibrium.entity.embedded.containers.CommunityContainer;
import botalibrium.entity.embedded.records.Record;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by apolyakov on 6/16/2017.
 */
public class InsertRecordsInBulk {
    private Map<String, CommunityContainer.CountLog> tagsToCountLog = new HashMap<>();
    private boolean preview = true;
    private Record record;

    public Map<String, CommunityContainer.CountLog> getTagsToCountLog() {
        return tagsToCountLog;
    }

    public void setTags(Map<String, CommunityContainer.CountLog> map) {
        this.tagsToCountLog = map;
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
