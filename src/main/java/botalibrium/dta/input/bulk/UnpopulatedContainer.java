package botalibrium.dta.input.bulk;

import java.util.HashSet;
import java.util.Set;

/**
 * Pattern used to populate batch.
 */
public class UnpopulatedContainer {
    private String tag = "";
    private String type = "";
    private String description = "";
    private Set<String> media = new HashSet<>();
    private int containersCount = 1;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getMedia() {
        return media;
    }

    public void setMedia(Set<String> media) {
        this.media = media;
    }

    public int getContainersCount() {
        return containersCount;
    }

    public void setContainersCount(int containersCount) {
        this.containersCount = containersCount;
    }
}
