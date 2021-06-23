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
import ca.uqam.tool.vivoproxy.swagger.api.PersonApiService;
import ca.uqam.tool.vivoproxy.swagger.api.VivoProxyResponseMessage;
import ca.uqam.tool.vivoproxy.swagger.api.impl.util.Credential;
import ca.uqam.tool.vivoproxy.swagger.model.Person;
import ca.uqam.tool.vivoproxy.swagger.model.PositionOfPerson;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2021-06-16T17:59:05.995-04:00")
public class PersonApiServiceImpl extends PersonApiService {
    public Response createPerson(Person body, SecurityContext securityContext) throws NotFoundException {
        try {
            return Response.ok().entity(new VivoProxyResponseMessage(VivoProxyResponseMessage.OK, runCreatePerson(body).body().string())).build();
        } catch (IOException e) {
            throw new NotFoundException(-1, e.getMessage());
        }
    }
    @Override
    public Response createPositionFor(PositionOfPerson body, SecurityContext securityContext) throws NotFoundException {
        CommandFactory cf = CommandFactory.getInstance();
        VivoReceiver session = new VivoReceiver();
        CommandInvoker invoker = new CommandInvoker();
        invoker.setSession(session);
        cf.setInvoker(invoker);
        /*
         * Commands creation
         */
        Command loginCommand = cf.createLogin(Credential.getLogin(), Credential.getPasswd());
        Command createPositionForCmd = cf.createPositionFor(body);
        /*
         * Commands invocation
         */

        CommandResult result =invoker.execute(loginCommand);
        result = invoker.execute(createPositionForCmd);
        String newUserIri = body.getPersonIRI();
        Command sparqlDescribeCommand = cf.createSparqlDescribeCommand(Credential.getLogin(), Credential.getPasswd(), newUserIri, "application/rdf+xml");
        com.squareup.okhttp.Response sparqlResponse = invoker.execute(sparqlDescribeCommand).getOkhttpResult();

        try {
            return Response.ok().entity(new VivoProxyResponseMessage(VivoProxyResponseMessage.OK, sparqlResponse.body().string() )).build();

        } catch (Exception e) {
            return Response.serverError().entity(new VivoProxyResponseMessage(VivoProxyResponseMessage.ERROR, e.getMessage())).build();

        }
    }
    private com.squareup.okhttp.Response runCreatePerson(Person person) throws IOException  {
        CommandFactory cf = CommandFactory.getInstance();
        VivoReceiver session = new VivoReceiver();
        CommandInvoker invoker = new CommandInvoker();
        invoker.setSession(session);
        cf.setInvoker(invoker);
        Command loginCommand = cf.createLogin(Credential.getLogin(), Credential.getPasswd());
        Command addPersonCommand = cf.createAddPerson(person.getFirstName(), person.getMiddleName(), person.getLastName(), person.getPersonType());
        CommandResult result =invoker.execute(loginCommand);
        result = invoker.execute(addPersonCommand);
        com.squareup.okhttp.Response response = result.getOkhttpResult();
        String newUserIri = VivoReceiverHelper.getUriResponse(response.body().string());
        Command sparqlDescribeCommand = cf.createSparqlDescribeCommand(Credential.getLogin(), Credential.getPasswd(), newUserIri, "application/rdf+xml");
        com.squareup.okhttp.Response sparqlResponse = invoker.execute(sparqlDescribeCommand).getOkhttpResult();
        return sparqlResponse;
    }    

    public static void main(String[]  args) throws IOException, OWLOntologyCreationException, OWLOntologyStorageException {
        if (true ){
            Person person = new Person();
            person.firstName("Michel10");
            person.lastName("Héon");
            PersonApiService service = new PersonApiServiceImpl();
            String response = ((PersonApiServiceImpl) service).runCreatePerson(person).body().string();
            System.out.println(response);
        }
        System.out.println("Done!");
    }
}
