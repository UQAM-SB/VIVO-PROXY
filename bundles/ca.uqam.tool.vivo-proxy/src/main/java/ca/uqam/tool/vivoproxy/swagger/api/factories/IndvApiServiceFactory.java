package ca.uqam.tool.vivoproxy.swagger.api.factories;

import ca.uqam.tool.vivoproxy.swagger.api.IndvApiService;
import ca.uqam.tool.vivoproxy.swagger.api.impl.IndvApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2022-02-22T15:03:56.936-05:00[America/New_York]")public class IndvApiServiceFactory {
    private final static IndvApiService service = new IndvApiServiceImpl();

    public static IndvApiService getIndvApi() {
        return service;
    }
}
