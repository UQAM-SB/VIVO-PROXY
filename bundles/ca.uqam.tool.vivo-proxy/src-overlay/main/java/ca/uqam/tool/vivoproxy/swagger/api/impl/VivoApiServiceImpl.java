package ca.uqam.tool.vivoproxy.swagger.api.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import ca.uqam.tool.util.credential.LOGIN;
import ca.uqam.tool.vivoproxy.pattern.command.Command;
import ca.uqam.tool.vivoproxy.pattern.command.CommandFactory;
import ca.uqam.tool.vivoproxy.pattern.command.CommandInvoker;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.pattern.command.receiver.VivoReceiver;
import ca.uqam.tool.vivoproxy.swagger.api.NotFoundException;
import ca.uqam.tool.vivoproxy.swagger.api.VivoApiService;
import ca.uqam.tool.vivoproxy.swagger.api.VivoProxyResponseMessage;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2021-07-19T20:54:54.802-04:00[America/New_York]")public class VivoApiServiceImpl extends VivoApiService {
	/* (non-Javadoc)
	 * @see ca.uqam.tool.vivoproxy.swagger.api.VivoApiService#getindividualByIRI(java.lang.String, javax.ws.rs.core.SecurityContext)
	 */
	public Response getindividualByIRI(String IRI,SecurityContext securityContext)
			throws NotFoundException {
		try {

			CommandFactory cf = CommandFactory.getInstance();
			VivoReceiver session = new VivoReceiver();
			CommandInvoker invoker = new CommandInvoker();
			Command sparqlDescribeCommand = cf.createSparqlDescribeCommand(LOGIN.getUserName(), LOGIN.getPasswd(), IRI, "application/rdf+xml");
			invoker.register(sparqlDescribeCommand);
			CommandResult resu; 
			resu = invoker.execute();
			com.squareup.okhttp.Response response = (com.squareup.okhttp.Response)resu.getResult();
			return Response.ok().entity(new VivoProxyResponseMessage(VivoProxyResponseMessage.OK, response.body().string())).build();
		} catch (IOException e) {
			return Response.serverError().entity(new VivoProxyResponseMessage(VivoProxyResponseMessage.ERROR, e.getMessage())).build();
		}
	}
	
    public static void main(String[]  args) throws IOException, OWLOntologyCreationException, OWLOntologyStorageException, NotFoundException {
        VivoApiServiceImpl vivoApiServiceImpl = new VivoApiServiceImpl();
        String iri = "http://localhost:8080/vivo/individual/n4595";
        Response reponse = vivoApiServiceImpl.getindividualByIRI(iri, null);
        String ontoString = ((VivoProxyResponseMessage) (reponse.getEntity())).getMessage();
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(new ByteArrayInputStream(ontoString.getBytes()));
        TurtleDocumentFormat turtleFormat = new TurtleDocumentFormat();
        try {
            manager.saveOntology(ontology, turtleFormat, System.out);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        System.out.println("Done!");
    }
}
