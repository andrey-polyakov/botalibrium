package botalibrium.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		register(ContainersEndpoint.class);
	}

	@Bean
	@Primary
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
		mapper.setDateFormat(new SimpleDateFormat("MM-dd-yyyy HH:mm:ss"));
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		return mapper;
	}


}
