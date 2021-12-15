package ca.uqam.tool.vivoproxy.swagger.api.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import ca.uqam.tool.util.credential.LOGIN;
import ca.uqam.tool.vivoproxy.pattern.command.Command;
import ca.uqam.tool.vivoproxy.pattern.command.CommandFactory;
import ca.uqam.tool.vivoproxy.pattern.command.CommandInvoker;
import ca.uqam.tool.vivoproxy.pattern.command.concrete.AddConceptCommand;
import ca.uqam.tool.vivoproxy.pattern.command.receiver.VivoReceiver;
import ca.uqam.tool.vivoproxy.swagger.api.ConceptApiService;
import ca.uqam.tool.vivoproxy.swagger.api.NotFoundException;
import ca.uqam.tool.vivoproxy.swagger.api.impl.util.ApiServiceImplHelper;
import ca.uqam.tool.vivoproxy.swagger.model.Concept;
import ca.uqam.tool.vivoproxy.swagger.model.LinguisticLabel;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2021-07-28T15:50:35.108-04:00[America/New_York]")
public class ConceptApiServiceImpl extends ConceptApiService {
	private static final String YOUR_PASSWD = LOGIN.getPasswd(); 
	private static final String YOUR_LOGIN = LOGIN.getUserName();
    public Response createConcept(Concept concept, SecurityContext securityContext) throws NotFoundException {
		try {
			CommandFactory cf = CommandFactory.getInstance();
			VivoReceiver session = new VivoReceiver(); 
			CommandInvoker invoker = new CommandInvoker(); 
			AddConceptCommand createAddConceptCommand = (AddConceptCommand) cf.createAddConceptCommand(concept);
			invoker.register(createAddConceptCommand);
			invoker.execute();
			return ApiServiceImplHelper.buildMessage((Command)createAddConceptCommand);
		} catch (Exception e) {
			throw new NotFoundException(-1, e.getMessage());
		}
    }
    public Response createConceptWithNewIRI(Concept body, SecurityContext securityContext) throws NotFoundException {
    	body.setIRI(null);
        return createConcept(body, securityContext);
    }
    public static void main(String[]  args) throws NotFoundException{
        List<LinguisticLabel> labels = new ArrayList<LinguisticLabel>();
    	LinguisticLabel cl = new LinguisticLabel();
    	cl.label("toto");
    	cl.language("fr-CA");
    	LinguisticLabel cl2 = new LinguisticLabel();
    	cl2.label("foo-bar");
    	cl2.language("en-US");
        labels.add(cl);
        labels.add(cl2);
    	Concept concept = new Concept();
    	concept.setIRI("http://purl.org/uqam.ca/vocabulary/expertise#pompom");
    	concept.labels(labels);
    	concept.addLabelsItem(cl2);
    	ConceptApiServiceImpl service = new ConceptApiServiceImpl();
    	Response response = service.createConcept(concept, null);
        System.out.println(response);
        System.out.println(response.getEntity().toString());
        concept.setIRI(null);
    	response = service.createConceptWithNewIRI(concept, null);
        System.out.println(response);
        System.out.println(response.getEntity().toString());
        
        
        System.out.println("Done!");
    }
}
