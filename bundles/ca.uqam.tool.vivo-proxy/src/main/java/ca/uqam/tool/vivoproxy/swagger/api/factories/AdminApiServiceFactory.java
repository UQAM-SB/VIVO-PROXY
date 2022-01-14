package ca.uqam.tool.vivoproxy.swagger.api.factories;

import ca.uqam.tool.vivoproxy.swagger.api.AdminApiService;
import ca.uqam.tool.vivoproxy.swagger.api.impl.AdminApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2022-01-14T03:45:37.393-05:00[America/New_York]")public class AdminApiServiceFactory {
    private final static AdminApiService service = new AdminApiServiceImpl();

    public static AdminApiService getAdminApi() {
        return service;
    }
}
