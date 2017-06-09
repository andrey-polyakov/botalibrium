package botalibrium.service.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by apolyakov on 4/5/2017.
 */
public class ServiceException extends Exception {

    protected Map<String, Object> relevantData = new HashMap<>();

    public ServiceException(String s) {
        super(s);
    }

    public ServiceException set(String name, Object value) {
        relevantData.put(name, value);
        return this;
    }

    public Map<String, Object> getRelevantData() {
        return relevantData;
    }

    public void setRelevantData(Map<String, Object> relevantData) {
        this.relevantData = relevantData;
    }
}
