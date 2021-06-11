package ca.uqam.tool.vivoproxy.swagger.api.impl;

import java.io.IOException;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import ca.uqam.tool.vivoproxy.pattern.command.Command;
import ca.uqam.tool.vivoproxy.pattern.command.CommandFactory;
import ca.uqam.tool.vivoproxy.pattern.command.CommandInvoker;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.pattern.command.receiver.SparqlQueryReceiver;
import ca.uqam.tool.vivoproxy.swagger.api.ApiResponseMessage;
import ca.uqam.tool.vivoproxy.swagger.api.NotFoundException;
import ca.uqam.tool.vivoproxy.swagger.api.VivoApiService;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2021-06-11T05:18:52.086-04:00")
public class VivoApiServiceImpl extends VivoApiService {
    private static final String PASSWD = "Vivo2435....";
    private static final String LOGIN = "vivo@uqam.ca";
    @Override
    public Response getindividualByIRI( @NotNull String IRI, SecurityContext securityContext) throws NotFoundException {
        String value;
        try {
            com.squareup.okhttp.Response response = run(IRI);
            value = response.body().string();
        } catch (IOException e) {
            value = e.getMessage();
        }
//        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, value)).build();
        return Response.ok().entity(value).build();
    }
    public static void main(String[] args) throws IOException {
        VivoApiServiceImpl vivoApiServiceImpl = new VivoApiServiceImpl();
        String iri = "http://localhost:8080/vivo/individual/n6870";
        vivoApiServiceImpl.run(iri);
    }
    private com.squareup.okhttp.Response run(String iri) throws IOException {
        CommandFactory cf = CommandFactory.getInstance();
        SparqlQueryReceiver session = new SparqlQueryReceiver();
        CommandInvoker invoker = new CommandInvoker();
        invoker.setSession(session);
        cf.setInvoker(invoker);
        Command sparqlDescribeCommand = cf.createSparqlDescribeCommand(LOGIN, PASSWD, iri);
        CommandResult resu = invoker.execute(sparqlDescribeCommand);
        com.squareup.okhttp.Response response = (com.squareup.okhttp.Response)resu.getResult();
        return response;
    }

}
