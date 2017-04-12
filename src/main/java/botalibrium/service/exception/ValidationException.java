package botalibrium.service.exception;

import java.util.Map;

/**
 * Created by apolyakov on 4/6/2017.
 */
public class ValidationException extends ServiceException {
    public ValidationException(String s) {
        super(s);
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nAdditional data:");
        for (Map.Entry<String, Object> stringObjectEntry : relevantData.entrySet()) {
            sb.append("\n\t");
            sb.append(stringObjectEntry.getKey());
            sb.append(" : ");
            sb.append(stringObjectEntry.getValue().toString());
        }
        return super.getMessage() + sb.toString();
    }
}
