package ca.uqam.tool.vivoproxy.swagger.api.factories;

import ca.uqam.tool.vivoproxy.swagger.api.VivoApiService;
import ca.uqam.tool.vivoproxy.swagger.api.impl.VivoApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2021-07-19T10:52:45.494-04:00")
public class VivoApiServiceFactory {
    private final static VivoApiService service = new VivoApiServiceImpl();

    public static VivoApiService getVivoApi() {
        return service;
    }
}
