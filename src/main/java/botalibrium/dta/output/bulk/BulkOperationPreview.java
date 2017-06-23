package botalibrium.dta.output.bulk;

import botalibrium.entity.embedded.containers.CommunityContainer;
import botalibrium.entity.embedded.records.Record;

import java.util.*;

/**
 * Created by apolyakov on 6/16/2017.
 */
public class BulkOperationPreview {
    public static class PreviewItem {
        private String tag;
        private String taxon;
        private Record latestRecord;
        private CommunityContainer.CountLog latestCountLog;

        private Set<String> media = new HashSet<>();

        private List<String> links = new LinkedList<>();

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getTaxon() {
            return taxon;
        }

        public void setTaxon(String taxon) {
            this.taxon = taxon;
        }

        public Record getLatestRecord() {
            return latestRecord;
        }

        public void setLatestRecord(Record latestRecord) {
            this.latestRecord = latestRecord;
        }

        public List<String> getLinks() {
            return links;
        }

        public CommunityContainer.CountLog getLatestCountLog() {
            return latestCountLog;
        }

        public void setLatestCountLog(CommunityContainer.CountLog latestCountLog) {
            this.latestCountLog = latestCountLog;
        }

        public Set<String> getMedia() {
            return media;
        }

        public void setMedia(Set<String> media) {
            this.media = media;
        }

        public void setLinks(List<String> links) {
            this.links = links;
        }
    }
    private Set<String> notFoundItems;
    private Set<String> nothingToDoItems;

    private List<PreviewItem> containers = new ArrayList<>();

    public List<PreviewItem> getContainers() {
        return containers;
    }

    public void setContainers(List<PreviewItem> containers) {
        this.containers = containers;
    }

    public Set<String> getNotFoundItems() {
        return notFoundItems;
    }

    public void setNotFoundItems(Set<String> notFoundItems) {
        this.notFoundItems = notFoundItems;
    }

    public Set<String> getNothingToDoItems() {
        return nothingToDoItems;
    }

    public void setNothingToChangeItems(Set<String> nothingToDoItems) {
        this.nothingToDoItems = nothingToDoItems;
    }
}
