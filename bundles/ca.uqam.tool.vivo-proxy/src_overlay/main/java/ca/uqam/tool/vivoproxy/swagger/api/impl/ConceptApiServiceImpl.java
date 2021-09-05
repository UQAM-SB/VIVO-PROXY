package ca.uqam.tool.vivoproxy.swagger.api.impl;

import ca.uqam.tool.util.credential.LOGIN;
import ca.uqam.tool.vivoproxy.pattern.command.Command;
import ca.uqam.tool.vivoproxy.pattern.command.CommandFactory;
import ca.uqam.tool.vivoproxy.pattern.command.CommandInvoker;
import ca.uqam.tool.vivoproxy.pattern.command.concrete.AddConceptCommand;
import ca.uqam.tool.vivoproxy.pattern.command.receiver.VivoReceiver;
import ca.uqam.tool.vivoproxy.swagger.api.*;
import ca.uqam.tool.vivoproxy.swagger.model.*;
import ca.uqam.tool.vivoproxy.util.SemanticWebMediaType;

import java.util.Map;
import java.util.List;



import java.io.IOException;
import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import javax.ws.rs.core.Response; 
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2021-07-28T15:50:35.108-04:00[America/New_York]")
public class ConceptApiServiceImpl extends ConceptApiService {
	private static final String YOUR_PASSWD = LOGIN.getPasswd(); 
	private static final String YOUR_LOGIN = LOGIN.getUserName();
    @Override
    public Response createConcept(Concept concept, SecurityContext securityContext) throws NotFoundException {
		try {
			CommandFactory cf = CommandFactory.getInstance();
			VivoReceiver session = new VivoReceiver(); 
			CommandInvoker invoker = new CommandInvoker(); 
			AddConceptCommand createAddConceptCommand = (AddConceptCommand) cf.createAddConceptCommand(YOUR_LOGIN, YOUR_PASSWD, concept, SemanticWebMediaType.APPLICATION_RDF_XML.toString());
			invoker.register(createAddConceptCommand);
			invoker.execute();
			com.squareup.okhttp.Response createAddConceptResponse = createAddConceptCommand.getCommandResult().getOkhttpResult();
			String sparqlResp = createAddConceptResponse.body().string();
			ModelAPIResponse apiResp = new ModelAPIResponse();
			apiResp.setIrIValue(concept.getIRI());
			apiResp.setViVOMessage(sparqlResp);
			apiResp.setCode(ApiResponseMessage.OK);
			apiResp.setType(new ApiResponseMessage(ApiResponseMessage.OK,"").getType());
			Response apiResponse = Response.ok().entity(apiResp).build();
			return apiResponse;
			
			
//			Command sparqlDescribeCommand = cf.createSparqlDescribeCommand(YOUR_LOGIN, YOUR_PASSWD, concept.getIRI(),SemanticWebMediaType.APPLICATION_RDF_XML.toString());
//			invoker.flush();
//			invoker.register(sparqlDescribeCommand);
//			invoker.execute();
//			com.squareup.okhttp.Response sparqlResponse = sparqlDescribeCommand.getCommandResult().getOkhttpResult();
//			String reponseString = sparqlResponse.body().string();
//			VivoProxyResponseMessage vivoMessage = new VivoProxyResponseMessage(VivoProxyResponseMessage.OK, reponseString);
//			Response apiResponse = Response.ok().entity(vivoMessage).build();
//			return apiResponse;

		} catch (IOException e) {
			throw new NotFoundException(-1, e.getMessage());
		}
    }
    
    public static void main(String[]  args) throws NotFoundException{
    	Concept concept = new Concept();
    	concept.setIRI("http://purl.org/uqam.ca/vocabulary/expertise#pompom3");
    	ConceptLabel cl = new ConceptLabel();
    	cl.label("toto3");
    	cl.language("fr-CA");
    	concept.addLabelsItem(cl);
    	ConceptLabel cl2 = new ConceptLabel();
    	cl2.label("tota3");
    	cl2.language("en-US");
    	concept.addLabelsItem(cl2);
    	ConceptApiServiceImpl service = new ConceptApiServiceImpl();
    	Response response = service.createConcept(concept, null);
        System.out.println(response);
        System.out.println("Done!");
    }
}
