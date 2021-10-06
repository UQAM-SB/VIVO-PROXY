package ca.uqam.tool.vivoproxy.swagger.api.factories;

import ca.uqam.tool.vivoproxy.swagger.api.ConceptApiService;
import ca.uqam.tool.vivoproxy.swagger.api.impl.ConceptApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2021-10-06T07:21:56.290-04:00[America/New_York]")public class ConceptApiServiceFactory {
    private final static ConceptApiService service = new ConceptApiServiceImpl();

    public static ConceptApiService getConceptApi() {
        return service;
    }
}
