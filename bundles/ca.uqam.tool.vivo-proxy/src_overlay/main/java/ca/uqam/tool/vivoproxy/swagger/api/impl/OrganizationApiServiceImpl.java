package ca.uqam.tool.vivoproxy.swagger.api.impl;

import java.io.IOException;
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
import ca.uqam.tool.vivoproxy.pattern.command.receiver.VivoReceiver;
import ca.uqam.tool.vivoproxy.pattern.command.util.VivoReceiverHelper;
import ca.uqam.tool.vivoproxy.util.SemanticWebMediaType;
import ca.uqam.tool.vivoproxy.swagger.api.*;
import ca.uqam.tool.vivoproxy.swagger.model.*;

import java.util.Map;
import java.util.List;
import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2021-07-21T06:59:15.044-04:00[America/New_York]")
public class OrganizationApiServiceImpl extends OrganizationApiService {
	private static final String YOUR_PASSWD = LOGIN.getPasswd(); 
	private static final String YOUR_LOGIN = LOGIN.getUserName();
	private final static Logger LOGGER = Logger.getLogger(OrganizationApiService.class.getName());

	/* (non-Javadoc)
	 * @see ca.uqam.tool.vivoproxy.swagger.api.OrganizationApiService#createOrganization(ca.uqam.tool.vivoproxy.swagger.model.Organization, javax.ws.rs.core.SecurityContext)
	 */
	public Response createOrganization(Organization organization,SecurityContext securityContext)
			throws NotFoundException {
		try {
			CommandFactory cf = CommandFactory.getInstance();
			VivoReceiver session = new VivoReceiver();
			CommandInvoker invoker = new CommandInvoker();  
			Command loginCommand = cf.createLogin(YOUR_LOGIN, YOUR_PASSWD);
			Command addOrganisationCommand = cf.createOrganization(organization.getName(), organization.getOrganizationType());
			Command logoutCommand = cf.createLogout();
			invoker.register(loginCommand);
			invoker.register(addOrganisationCommand);
			invoker.register(logoutCommand);
			CommandResult result = invoker.execute();
			com.squareup.okhttp.Response response = addOrganisationCommand.getCommandResult().getOkhttpResult();
			String newUserIri = VivoReceiverHelper.getUriResponse(response.body().string());
		
			ModelAPIResponse apiResp = new ModelAPIResponse();
			apiResp.setIrIValue(newUserIri);
			apiResp.setViVOMessage(" return code: " +response.code()+ " "  +response.message());
			apiResp.setCode(ApiResponseMessage.OK);
			apiResp.setType(new ApiResponseMessage(ApiResponseMessage.OK,"").getType());
			Response apiResponse = Response.ok().entity(apiResp).build();
			return apiResponse;
			
			
//			Command sparqlDescribeCommand = cf.createSparqlDescribeCommand(YOUR_LOGIN, YOUR_PASSWD, newUserIri,SemanticWebMediaType.APPLICATION_RDF_XML.toString());
//			invoker.flush();
//			invoker.register(sparqlDescribeCommand);
//			invoker.register(logoutCommand);
//			invoker.execute();
//			com.squareup.okhttp.Response sparqlResponse = sparqlDescribeCommand.getCommandResult().getOkhttpResult();
//			String sparqlResp = sparqlResponse.body().string();
//			VivoProxyResponseMessage vivoMessage = new VivoProxyResponseMessage(VivoProxyResponseMessage.OK, sparqlResp);
//			Response apiResponse = Response.ok().entity(vivoMessage).build();
//			return apiResponse;
		} catch (IOException e) {
			throw new NotFoundException(-1, e.getMessage());
		}  
	}
    /**
     * @param args
     * @throws IOException
     * @throws OWLOntologyCreationException
     * @throws OWLOntologyStorageException
     * @throws NotFoundException 
     */
    public static void main(String[]  args) throws IOException, OWLOntologyCreationException, OWLOntologyStorageException, NotFoundException {
        Organization organ = new Organization();
        organ.setName("Test Organization");
        organ.setOrganizationType("http://vivoweb.org/ontology/core#University");
        OrganizationApiService service = new OrganizationApiServiceImpl();
        Response response = service.createOrganization(organ, null);
        System.out.println(response);
        System.out.println("Done!");
    }
}
