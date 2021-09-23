package ca.uqam.tool.vivoproxy.swagger.api.impl;

import java.io.IOException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import ca.uqam.tool.util.credential.LOGIN;
import ca.uqam.tool.vivoproxy.pattern.command.CommandFactory;
import ca.uqam.tool.vivoproxy.pattern.command.CommandInvoker;
import ca.uqam.tool.vivoproxy.pattern.command.concrete.AddConceptCommand;
import ca.uqam.tool.vivoproxy.pattern.command.receiver.VivoReceiver;
import ca.uqam.tool.vivoproxy.swagger.api.ApiResponseMessage;
import ca.uqam.tool.vivoproxy.swagger.api.ConceptApiService;
import ca.uqam.tool.vivoproxy.swagger.api.NotFoundException;
import ca.uqam.tool.vivoproxy.swagger.model.Concept;
import ca.uqam.tool.vivoproxy.swagger.model.LinguisticLabel;
import ca.uqam.tool.vivoproxy.swagger.model.ModelAPIResponse;
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
			AddConceptCommand createAddConceptCommand = (AddConceptCommand) cf.createAddConceptCommand(concept);
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
		} catch (IOException e) {
			throw new NotFoundException(-1, e.getMessage());
		}
    }
    
    public static void main(String[]  args) throws NotFoundException{
    	LinguisticLabel cl = new LinguisticLabel();
    	cl.label("toto");
    	cl.language("fr-CA");
    	LinguisticLabel cl2 = new LinguisticLabel();
    	cl2.label("foo-bar");
    	cl2.language("en-US");
    	Concept concept = new Concept();
    	concept.setIRI("http://purl.org/uqam.ca/vocabulary/expertise#pompom");
    	concept.addLabelsItem(cl);
    	concept.addLabelsItem(cl2);
    	ConceptApiServiceImpl service = new ConceptApiServiceImpl();
    	Response response = service.createConcept(concept, null);
        System.out.println(response);
        System.out.println("Done!");
    }
}
