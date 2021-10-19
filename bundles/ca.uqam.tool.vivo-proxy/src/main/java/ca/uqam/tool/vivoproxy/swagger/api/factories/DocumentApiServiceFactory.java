package ca.uqam.tool.vivoproxy.swagger.api.factories;

import ca.uqam.tool.vivoproxy.swagger.api.DocumentApiService;
import ca.uqam.tool.vivoproxy.swagger.api.impl.DocumentApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2021-10-19T03:59:19.892-04:00[America/New_York]")public class DocumentApiServiceFactory {
    private final static DocumentApiService service = new DocumentApiServiceImpl();

    public static DocumentApiService getDocumentApi() {
        return service;
    }
}
