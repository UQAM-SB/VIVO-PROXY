package ca.uqam.tool.vivoproxy.swagger.api.factories;

import ca.uqam.tool.vivoproxy.swagger.api.VivoApiService;
import ca.uqam.tool.vivoproxy.swagger.api.impl.VivoApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2021-06-25T02:54:30.354-04:00")
public class VivoApiServiceFactory {
    private final static VivoApiService service = new VivoApiServiceImpl();

    public static VivoApiService getVivoApi() {
        return service;
    }
}
