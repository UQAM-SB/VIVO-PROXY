package ca.uqam.tool.vivoproxy.swagger.api.factories;

import ca.uqam.tool.vivoproxy.swagger.api.ConceptApiService;
import ca.uqam.tool.vivoproxy.swagger.api.impl.ConceptApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2021-12-16T09:09:24.006-05:00[America/New_York]")public class ConceptApiServiceFactory {
    private final static ConceptApiService service = new ConceptApiServiceImpl();

    public static ConceptApiService getConceptApi() {
        return service;
    }
}
