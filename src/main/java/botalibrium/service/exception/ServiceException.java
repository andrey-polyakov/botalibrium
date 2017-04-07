package botalibrium.service.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by apolyakov on 4/5/2017.
 */
public class ServiceException extends Exception {

    private Map<String, Object> relevantData = new HashMap<>();

    public ServiceException(String s) {
        super(s);
    }

    public ServiceException set(String name, Object value) {
        relevantData.put(name, value);
        return this;
    }

}
