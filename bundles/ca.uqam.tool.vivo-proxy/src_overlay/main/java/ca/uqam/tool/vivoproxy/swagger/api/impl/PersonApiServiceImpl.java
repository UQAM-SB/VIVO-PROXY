package ca.uqam.tool.vivoproxy.swagger.api.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import ca.uqam.tool.util.credential.LOGIN;
import ca.uqam.tool.vivoproxy.pattern.command.Command;
import ca.uqam.tool.vivoproxy.pattern.command.CommandFactory;
import ca.uqam.tool.vivoproxy.pattern.command.CommandInvoker;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.pattern.command.concrete.AddHasResearchAreaCommand;
import ca.uqam.tool.vivoproxy.pattern.command.concrete.AddResearchAreaOfCommand;
import ca.uqam.tool.vivoproxy.pattern.command.receiver.VivoReceiver;
import ca.uqam.tool.vivoproxy.pattern.command.util.VivoReceiverHelper;
import ca.uqam.tool.vivoproxy.swagger.api.ApiResponseMessage;
import ca.uqam.tool.vivoproxy.swagger.api.NotFoundException;
import ca.uqam.tool.vivoproxy.swagger.api.PersonApiService;
import ca.uqam.tool.vivoproxy.swagger.api.VivoProxyResponseMessage;
import ca.uqam.tool.vivoproxy.swagger.model.AuthorOfADocument;
import ca.uqam.tool.vivoproxy.swagger.model.ModelAPIResponse;
import ca.uqam.tool.vivoproxy.swagger.model.Person;
import ca.uqam.tool.vivoproxy.swagger.model.PositionOfPerson;
import ca.uqam.tool.vivoproxy.swagger.model.ResourceToResource;
import ca.uqam.tool.vivoproxy.util.SemanticWebMediaType;
/**
 * @author heon
 *
 */
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2021-07-19T21:08:05.454-04:00[America/New_York]")
public class PersonApiServiceImpl extends PersonApiService {
	private static final String VIVO_PASSWD = LOGIN.getPasswd(); 
	private static final String VIVO_LOGIN = LOGIN.getUserName();
	private final static Logger LOGGER = Logger.getLogger(PersonApiServiceImpl.class.getName());
	/*
	 * Script for testing
	 */
	public static void main(String[]  args) throws IOException, OWLOntologyCreationException, OWLOntologyStorageException, NotFoundException {
		if (true ){

			Person person = new Person();
			person.setFirstName("Michel11");  
			person.setLastName("Héon"); 
			PersonApiService service = new PersonApiServiceImpl();
			//			Response response = service.createPerson(person, null);
			//			System.out.println(response.getEntity());
		}
		System.out.println("Done!");
	}
	/* (non-Javadoc)
	 * @see ca.uqam.tool.vivoproxy.swagger.api.PersonApiService#createPerson(ca.uqam.tool.vivoproxy.swagger.model.Person, javax.ws.rs.core.SecurityContext)
	 */
	public Response createPerson(Person person,SecurityContext securityContext) throws NotFoundException {
		try {
			CommandFactory cf = CommandFactory.getInstance();
			VivoReceiver session = new VivoReceiver();
			CommandInvoker invoker = new CommandInvoker();
			/*
			 * Create commands
			 */
			Command loginCommand = cf.createLogin(LOGIN.getUserName(), LOGIN.getPasswd());
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
			ModelAPIResponse apiResp = new ModelAPIResponse();
			apiResp.setIrIValue(newUserIri);
			apiResp.setViVOMessage(" return code: " +response.code()+ " "  +response.message());
			apiResp.setCode(ApiResponseMessage.OK);
			apiResp.setType(new ApiResponseMessage(ApiResponseMessage.OK,"").getType());
			Response apiResponse = Response.ok().entity(apiResp).build();
			return apiResponse;
		} catch (IOException e) {
			return Response.serverError().entity(new VivoProxyResponseMessage(VivoProxyResponseMessage.ERROR, e.getMessage())).build();
		}

	}

	/* (non-Javadoc)
	 * @see ca.uqam.tool.vivoproxy.swagger.api.PersonApiService#createUsersWithListInput(java.util.List, javax.ws.rs.core.SecurityContext)
	 */
	public Response createUsersWithListInput(List<Person> body,SecurityContext securityContext)
			throws NotFoundException {
		CommandFactory cf = CommandFactory.getInstance();
		VivoReceiver session = new VivoReceiver();
		CommandInvoker invoker = new CommandInvoker();
		CommandResult result = null;
		/*
		 * Create commands
		 */
		Command loginCommand = cf.createLogin(LOGIN.getUserName(), LOGIN.getPasswd());
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
		Command sparqlDescribeCommand = cf.createSparqlDescribeCommand(LOGIN.getUserName(), LOGIN.getPasswd(), uris, SemanticWebMediaType.APPLICATION_RDF_XML.toString());
		invoker.flush();
		invoker.register(sparqlDescribeCommand);

		try {
			invoker.execute();
			com.squareup.okhttp.Response sparqlResponse = sparqlDescribeCommand.getCommandResult().getOkhttpResult();
			String sparqlResp = sparqlResponse.body().string();
			VivoProxyResponseMessage vivoMessage = new VivoProxyResponseMessage(VivoProxyResponseMessage.OK, sparqlResp);
			Response apiResponse = Response.ok().entity(vivoMessage).type(SemanticWebMediaType.TEXT_PLAIN).build();
			return apiResponse;

			//  			return Response.ok().entity(new VivoProxyResponseMessage(VivoProxyResponseMessage.OK, loginCommand.getCommandResult().getOkhttpResult().body().string())).build();
		} catch (IOException e) {
			throw new NotFoundException(-1, e.getMessage());
		}
	}
	/* (non-Javadoc)
	 * @see ca.uqam.tool.vivoproxy.swagger.api.PersonApiService#createPerson(java.lang.String, java.lang.String, java.lang.String, java.lang.String, javax.ws.rs.core.SecurityContext)
	 */
	public Response createPerson(String personType, String firstName, String lastName, String middleName,
			SecurityContext securityContext) throws NotFoundException {
		Person person = new Person();
		person.setPersonType(personType);
		person.setFirstName(firstName);
		person.setLastName(lastName);
		person.setMiddleName(middleName);
		return this.createPerson(person, securityContext);
	}
	public Response personAddOrganisationalPositionTo(PositionOfPerson posOfPers, SecurityContext securityContext)
			throws NotFoundException {
		try {
			CommandFactory cf = CommandFactory.getInstance();
			VivoReceiver session = new VivoReceiver();
			CommandInvoker invoker = new CommandInvoker();
			/*
			 * Commands creation
			 */
			Command loginCommand = cf.createLogin(LOGIN.getUserName(), LOGIN.getPasswd());
			Command createPositionForCmd = cf.createPositionFor(posOfPers);
			Command logOutCommand = cf.createLogout();
			/*
			 * Commands registration
			 */
			invoker.register(loginCommand);
			invoker.register(createPositionForCmd);
			invoker.register(logOutCommand);
			invoker.execute();
			String sparqlResp = createPositionForCmd.getCommandResult().getOkhttpResult().body().string();
			ModelAPIResponse apiResp = new ModelAPIResponse();
			apiResp.setIrIValue(posOfPers.getPersonIRI());
			apiResp.setViVOMessage(sparqlResp);
			apiResp.setCode(ApiResponseMessage.OK);
			apiResp.setType(new ApiResponseMessage(ApiResponseMessage.OK,"").getType());
			Response apiResponse = Response.ok().entity(apiResp).build();
			return apiResponse;

		} catch (Exception e) {
			return Response.serverError().entity(new VivoProxyResponseMessage(VivoProxyResponseMessage.ERROR, e.getMessage())).build();
		}
	}
	@Override
	public Response personAddResearchAreaOf(ResourceToResource resourceToLink, SecurityContext securityContext)
			throws NotFoundException {
		try {

			CommandFactory cf = CommandFactory.getInstance();
			VivoReceiver session = new VivoReceiver(); 
			CommandInvoker invoker = new CommandInvoker(); 
			AddResearchAreaOfCommand addResearchAreaOfCommand = (AddResearchAreaOfCommand) cf.createAddResearchAreaOfCommand(VIVO_LOGIN, VIVO_PASSWD, resourceToLink, SemanticWebMediaType.APPLICATION_RDF_XML.toString());
			invoker.register(addResearchAreaOfCommand);
			invoker.execute();
			com.squareup.okhttp.Response createAddConceptResponse = addResearchAreaOfCommand.getCommandResult().getOkhttpResult();
			String sparqlResp = createAddConceptResponse.body().string();
			ModelAPIResponse apiResp = new ModelAPIResponse();
			apiResp.setIrIValue(resourceToLink.getSubjectIRI());
			apiResp.setViVOMessage(sparqlResp);
			apiResp.setCode(ApiResponseMessage.OK);
			apiResp.setType(new ApiResponseMessage(ApiResponseMessage.OK,"").getType());
			Response apiResponse = Response.ok().entity(apiResp).build();
			return apiResponse;

		} catch (IOException e) {
			throw new NotFoundException(-1, e.getMessage());
		}

	}
	@Override
	public Response personHasAddResearchArea(ResourceToResource resourceToLink, SecurityContext securityContext)
			throws NotFoundException {
		try {

			CommandFactory cf = CommandFactory.getInstance();
			VivoReceiver session = new VivoReceiver(); 
			CommandInvoker invoker = new CommandInvoker(); 
			AddHasResearchAreaCommand addHasResearchAreaCommand = (AddHasResearchAreaCommand) cf.createAddHasResearchAreaCommand(VIVO_LOGIN, VIVO_PASSWD, resourceToLink, SemanticWebMediaType.APPLICATION_RDF_XML.toString());
			invoker.register(addHasResearchAreaCommand);
			invoker.execute();
			com.squareup.okhttp.Response createAddConceptResponse = addHasResearchAreaCommand.getCommandResult().getOkhttpResult();
			
			String sparqlResp = createAddConceptResponse.body().string();
			ModelAPIResponse apiResp = new ModelAPIResponse();
			apiResp.setIrIValue(resourceToLink.getSubjectIRI());
			apiResp.setViVOMessage(sparqlResp);
			apiResp.setCode(ApiResponseMessage.OK);
			apiResp.setType(new ApiResponseMessage(ApiResponseMessage.OK,"").getType());
			Response apiResponse = Response.ok().entity(apiResp).build();
			return apiResponse;
		} catch (IOException e) {
			throw new NotFoundException(-1, e.getMessage());
		}
	}
	public Response personAddDocument(AuthorOfADocument author, SecurityContext securityContext)
			throws NotFoundException {
		try {
			CommandFactory cf = CommandFactory.getInstance();
			VivoReceiver session = new VivoReceiver();
			CommandInvoker invoker = new CommandInvoker();
			/*
			 * Commands creation
			 */
			Command loginCommand = cf.createLogin(LOGIN.getUserName(), LOGIN.getPasswd());
			Command addAuthorOfDocumentCommand = cf.addAuthorOfDocument(author);
			Command logOutCommand = cf.createLogout();
			/*
			 * Commands registration
			 */
			invoker.register(loginCommand);
			invoker.register(addAuthorOfDocumentCommand);
			invoker.register(logOutCommand);
			invoker.execute();
			String sparqlResp = addAuthorOfDocumentCommand.getCommandResult().getOkhttpResult().body().string();
			ModelAPIResponse apiResp = new ModelAPIResponse();
			apiResp.setIrIValue(author.getPersonIRI());
			apiResp.setViVOMessage(sparqlResp);
			apiResp.setCode(ApiResponseMessage.OK);
			apiResp.setType(new ApiResponseMessage(ApiResponseMessage.OK,"").getType());
			Response apiResponse = Response.ok().entity(apiResp).build();
			return apiResponse;

		} catch (Exception e) {
			return Response.serverError().entity(new VivoProxyResponseMessage(VivoProxyResponseMessage.ERROR, e.getMessage())).build();
		}	}

}
