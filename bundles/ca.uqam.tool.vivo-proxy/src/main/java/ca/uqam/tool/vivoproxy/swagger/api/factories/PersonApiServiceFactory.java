package ca.uqam.tool.vivoproxy.swagger.api.factories;

import ca.uqam.tool.vivoproxy.swagger.api.PersonApiService;
import ca.uqam.tool.vivoproxy.swagger.api.impl.PersonApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2021-07-15T06:02:28.027-04:00")
public class PersonApiServiceFactory {
    private final static PersonApiService service = new PersonApiServiceImpl();

    public static PersonApiService getPersonApi() {
        return service;
    }
}
