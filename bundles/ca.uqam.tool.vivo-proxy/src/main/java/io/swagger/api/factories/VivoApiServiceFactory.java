package io.swagger.api.factories;

import io.swagger.api.VivoApiService;
import io.swagger.api.impl.VivoApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2021-05-31T17:14:53.469-04:00")
public class VivoApiServiceFactory {
    private final static VivoApiService service = new VivoApiServiceImpl();

    public static VivoApiService getVivoApi() {
        return service;
    }
}
