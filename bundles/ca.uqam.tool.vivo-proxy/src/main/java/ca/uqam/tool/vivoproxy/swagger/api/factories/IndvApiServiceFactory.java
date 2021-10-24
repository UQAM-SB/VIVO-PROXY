package ca.uqam.tool.vivoproxy.swagger.api.factories;

import ca.uqam.tool.vivoproxy.swagger.api.IndvApiService;
import ca.uqam.tool.vivoproxy.swagger.api.impl.IndvApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2021-10-23T07:39:42.585-04:00[America/New_York]")public class IndvApiServiceFactory {
    private final static IndvApiService service = new IndvApiServiceImpl();

    public static IndvApiService getIndvApi() {
        return service;
    }
}
