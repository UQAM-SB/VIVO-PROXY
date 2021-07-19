package ca.uqam.tool.vivoproxy.swagger.api.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

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
	private final static Logger LOGGER = Logger.getLogger(VivoReceiver.class.getName());
	/*
	 * Script for testing
	 */
	public static void main(String[]  args) throws IOException, OWLOntologyCreationException, OWLOntologyStorageException {
		if (true ){
			Person person = new Person();
			person.firstName("Michel11");
			person.lastName("Héon");
			PersonApiService service = new PersonApiServiceImpl();
			String response = ((PersonApiServiceImpl) service).runCreatePerson(person).body().string();
			System.out.println(response);
		}
		System.out.println("Done!");
	}
	public Response createPerson(Person body, SecurityContext securityContext) throws NotFoundException {
		try {
			return Response.ok().entity(new VivoProxyResponseMessage(VivoProxyResponseMessage.OK, runCreatePerson(body).body().string())).build();
		} catch (IOException e) {
			throw new NotFoundException(-1, e.getMessage());
		}
	}
	@Override
	public Response createPositionFor(PositionOfPerson body, SecurityContext securityContext) throws NotFoundException {
		try {
			CommandFactory cf = CommandFactory.getInstance();
			VivoReceiver session = new VivoReceiver();
			CommandInvoker invoker = new CommandInvoker();
			/*
			 * Commands creation
			 */
			Command loginCommand = cf.createLogin(Credential.getLogin(), Credential.getPasswd());
			Command createPositionForCmd = cf.createPositionFor(body);
			Command logOutCommand = cf.createLogout();
			/*
			 * Commands registration
			 */
			invoker.register(loginCommand);
			invoker.register(createPositionForCmd);
			invoker.register(logOutCommand);
			CommandResult result =invoker.execute();
			String newUserIri = body.getPersonIRI();
			Command sparqlDescribeCommand = cf.createSparqlDescribeCommand(Credential.getLogin(), Credential.getPasswd(), newUserIri, "application/rdf+xml");
			invoker.flush();
			invoker.register(sparqlDescribeCommand);
			com.squareup.okhttp.Response sparqlResponse = invoker.execute().getOkhttpResult();
			return Response.ok().entity(new VivoProxyResponseMessage(VivoProxyResponseMessage.OK, sparqlResponse.body().string() )).build();

		} catch (Exception e) {
			return Response.serverError().entity(new VivoProxyResponseMessage(VivoProxyResponseMessage.ERROR, e.getMessage())).build();
		}
	}
	private com.squareup.okhttp.Response runCreatePerson(Person person) throws IOException  {
		CommandFactory cf = CommandFactory.getInstance();
		VivoReceiver session = new VivoReceiver();
		CommandInvoker invoker = new CommandInvoker();
		/*
		 * Create commands
		 */
		Command loginCommand = cf.createLogin(Credential.getLogin(), Credential.getPasswd());
		Command addPersonCommand = cf.createAddPerson(person);
		Command logOutCommand = cf.createLogout();

		/*
		 * Register commands
		 */
		invoker.register(loginCommand);
		invoker.register(addPersonCommand);
		invoker.register(logOutCommand);
		/*
		 * Execute commands
		 */
		CommandResult result =invoker.execute();
        com.squareup.okhttp.Response response = addPersonCommand.getCommandResult().getOkhttpResult();
        String newUserIri = VivoReceiverHelper.getUriResponse(response.body().string());
		LOGGER.info("Creating user at uri "+ newUserIri+" with return code " + response.code());
		Command sparqlDescribeCommand = cf.createSparqlDescribeCommand(Credential.getLogin(), Credential.getPasswd(), newUserIri, "application/rdf+xml");
		invoker.flush();
		invoker.register(sparqlDescribeCommand);
		com.squareup.okhttp.Response sparqlResponse = invoker.execute().getOkhttpResult();
		return sparqlResponse;
	}    
	@Override
	public Response createUsersWithListInput(List<Person> body, SecurityContext securityContext)
			throws NotFoundException {
		CommandFactory cf = CommandFactory.getInstance();
		VivoReceiver session = new VivoReceiver();
		CommandInvoker invoker = new CommandInvoker();
		CommandResult result = null;
		/*
		 * Create commands
		 */
		Command loginCommand = cf.createLogin(Credential.getLogin(), Credential.getPasswd());
		Command logOutCommand = cf.createLogout();

		/*
		 * Register commands
		 */
		invoker.register(loginCommand);
		Command createAddPersonCmd = cf.createAddPerson(body);
		invoker.register(createAddPersonCmd);
		invoker.register(logOutCommand);
		try {
			invoker.execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Person> personsList = new ArrayList<>();
		result = createAddPersonCmd.getCommandResult();
		List<String> uris = (List<String>) result.getResult();
		Command sparqlDescribeCommand = cf.createSparqlDescribeCommand(Credential.getLogin(), Credential.getPasswd(), uris, "application/rdf+xml");
		invoker.flush();
		invoker.register(sparqlDescribeCommand);

		try {
			invoker.execute();
			com.squareup.okhttp.Response sparqlResponse = sparqlDescribeCommand.getCommandResult().getOkhttpResult();
			return Response.ok().entity(new VivoProxyResponseMessage(VivoProxyResponseMessage.OK, loginCommand.getCommandResult().getOkhttpResult().body().string())).build();
		} catch (IOException e) {
			throw new NotFoundException(-1, e.getMessage());
		}
	}
}
