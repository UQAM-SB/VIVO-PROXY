package ca.uqam.tool.vivoproxy.swagger.api.factories;

import ca.uqam.tool.vivoproxy.swagger.api.OrganizationApiService;
import ca.uqam.tool.vivoproxy.swagger.api.impl.OrganizationApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2021-12-16T09:09:24.006-05:00[America/New_York]")public class OrganizationApiServiceFactory {
    private final static OrganizationApiService service = new OrganizationApiServiceImpl();

    public static OrganizationApiService getOrganizationApi() {
        return service;
    }
}
