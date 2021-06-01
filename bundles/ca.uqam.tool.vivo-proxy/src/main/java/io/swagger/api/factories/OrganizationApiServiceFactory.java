package io.swagger.api.factories;

import io.swagger.api.OrganizationApiService;
import io.swagger.api.impl.OrganizationApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2021-05-31T17:14:53.469-04:00")
public class OrganizationApiServiceFactory {
    private final static OrganizationApiService service = new OrganizationApiServiceImpl();

    public static OrganizationApiService getOrganizationApi() {
        return service;
    }
}
