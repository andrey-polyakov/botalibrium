package botalibrium.service.exception;

import lombok.Data;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

/**
 * Created by apolyakov on 4/5/2017.
 */
@Data
public class ServiceException extends WebApplicationException {

    protected Map<String, Object> relevantData = new HashMap<>();
    private int status;

    public ServiceException(String message) {
        super(Response.status(INTERNAL_SERVER_ERROR).entity(new Dto(message)).type(APPLICATION_JSON).build());
    }

    public ServiceException(String message, Response.Status status) {
        super(Response.status(status).entity(new Dto(message)).type(APPLICATION_JSON).build());
    }

    public ServiceException set(String name, Object value) {
        relevantData.put(name, value);
        return this;
    }

    @Data
    static class Dto {
        private String message;

        public Dto(String message) {
            this.message = message;
        }
    }

}
