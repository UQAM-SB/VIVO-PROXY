package ca.uqam.tool.vivoproxy.swagger.api.impl;

import java.io.IOException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import ca.uqam.tool.vivoproxy.pattern.command.Command;
import ca.uqam.tool.vivoproxy.pattern.command.CommandFactory;
import ca.uqam.tool.vivoproxy.pattern.command.CommandInvoker;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.pattern.command.receiver.VivoReceiver;
import ca.uqam.tool.vivoproxy.pattern.command.util.VivoReceiverHelper;
import ca.uqam.tool.vivoproxy.swagger.api.ApiResponseMessage;
import ca.uqam.tool.vivoproxy.swagger.api.NotFoundException;
import ca.uqam.tool.vivoproxy.swagger.api.OrganizationApiService;
import ca.uqam.tool.vivoproxy.swagger.api.PersonApiService;
import ca.uqam.tool.vivoproxy.swagger.api.VivoProxyResponseMessage;
import ca.uqam.tool.vivoproxy.swagger.api.impl.util.Credential;
import ca.uqam.tool.vivoproxy.swagger.model.Organization;
import ca.uqam.tool.vivoproxy.swagger.model.Person;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2021-06-10T18:18:23.710-04:00")
public class OrganizationApiServiceImpl extends OrganizationApiService {
    private static final String PASSWD = "Vivo2435....";
    private static final String LOGIN = "vivo@uqam.ca";
    @Override
    public Response createOrganization(Organization body, SecurityContext securityContext) throws NotFoundException {
        try { 
            return Response.ok().entity(new VivoProxyResponseMessage(VivoProxyResponseMessage.OK, run(body).body().string())).build();
        } catch (IOException e) {
            throw new NotFoundException(-1, e.getMessage());
        }
    }
    private com.squareup.okhttp.Response run(Organization organ) throws IOException  {
        CommandFactory cf = CommandFactory.getInstance();
        VivoReceiver session = new VivoReceiver();
        CommandInvoker invoker = new CommandInvoker(); 
        Command loginCommand = cf.createLogin(Credential.getLogin(), Credential.getPasswd());
        Command addOrganisationCommand = cf.createOrganization(organ.getName(), organ.getOrganizationType());
        Command logout = cf.createLogout();
        invoker.register(loginCommand);
        invoker.register(addOrganisationCommand);
        invoker.register(logout);
        CommandResult result = invoker.execute();
        com.squareup.okhttp.Response response = addOrganisationCommand.getCommandResult().getOkhttpResult();
        String newUserIri = VivoReceiverHelper.getUriResponse(response.body().string());
        Command sparqlDescribeCommand = cf.createSparqlDescribeCommand(Credential.getLogin(), Credential.getPasswd(), newUserIri, "application/rdf+xml");
        invoker.flush();
        invoker.register(sparqlDescribeCommand);
        com.squareup.okhttp.Response sparqlResponse = invoker.execute().getOkhttpResult();
        return sparqlResponse;
    }    
    
    public static void main(String[]  args) throws IOException, OWLOntologyCreationException, OWLOntologyStorageException {
        Organization organ = new Organization();
        organ.setName("Test Organization");
        organ.setOrganizationType("http://vivoweb.org/ontology/core#University");
        OrganizationApiService service = new OrganizationApiServiceImpl();
        String response = ((OrganizationApiServiceImpl)service).run(organ).body().string();
        System.out.println(response);
        System.out.println("Done!");
    }
}

