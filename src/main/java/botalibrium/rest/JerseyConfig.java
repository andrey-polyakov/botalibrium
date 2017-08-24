package botalibrium.rest;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		register(BatchesEndpoint.class);
		register(ContainersEndpoint.class);

		property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
		property(ServerProperties.BV_DISABLE_VALIDATE_ON_EXECUTABLE_OVERRIDE_CHECK, true);
	}

	@Bean
	@Primary
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(mapper.getVisibilityChecker().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);

		mapper.setDateFormat(new SimpleDateFormat("dd MMM, yyyy HH:mm:ss"));
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

		return mapper;
	}


}
