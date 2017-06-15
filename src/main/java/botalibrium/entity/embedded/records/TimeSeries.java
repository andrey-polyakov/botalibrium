package botalibrium.entity.embedded.records;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by apolyakov on 6/12/2017.
 */
public class TimeSeries {
    static class Data implements Comparable<Data> {
        private Map<String, String> variables;
        private Timestamp timestamp;

        public Map<String, String> getVariables() {
            return variables;
        }

        public void setVariables(Map<String, String> variables) {
            this.variables = variables;
        }

        public Timestamp getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Timestamp timestamp) {
            this.timestamp = timestamp;
        }

        @Override
        public int compareTo(Data data) {
            return data.timestamp.compareTo(timestamp);
        }
    }
    private List<Data> series;
    private String id, description;

    public List<Data> getSeries() {
        return series;
    }

    public void setSeries(List<Data> series) {
        this.series = series;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
