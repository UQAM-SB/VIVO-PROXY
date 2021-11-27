package ca.uqam.tool.vivoproxy.swagger.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
public class JacksonConfig implements ContextResolver<ObjectMapper> {
    private final ObjectMapper objectMapper;

    public JacksonConfig() throws Exception {

        objectMapper = new ObjectMapper()
            .setDateFormat(new RFC3339DateFormat());
    }

    @Override
	public ObjectMapper getContext(Class<?> arg0) {
        return objectMapper;
    }
}
