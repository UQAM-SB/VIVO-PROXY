package ca.uqam.tool.vivoproxy.swagger.api.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import ca.uqam.tool.vivoproxy.pattern.command.Command;
import ca.uqam.tool.vivoproxy.pattern.command.CommandFactory;
import ca.uqam.tool.vivoproxy.pattern.command.CommandInvoker;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.pattern.command.receiver.VivoReceiver;
import ca.uqam.tool.vivoproxy.swagger.api.ApiResponseMessage;
import ca.uqam.tool.vivoproxy.swagger.api.NotFoundException;
import ca.uqam.tool.vivoproxy.swagger.api.VivoApiService;
import ca.uqam.tool.vivoproxy.swagger.api.VivoProxyResponseMessage;
import ca.uqam.tool.vivoproxy.swagger.api.impl.util.Credential;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2021-06-11T05:18:52.086-04:00")
public class VivoApiServiceImpl extends VivoApiService {
    @Override
    public Response getindividualByIRI( @NotNull String IRI, SecurityContext securityContext) throws NotFoundException {
        String value;
        try {
            com.squareup.okhttp.Response response = run(IRI);
            value = response.body().string();
        } catch (IOException e) {
            value = e.getMessage();
        }
        return Response.ok().entity(new VivoProxyResponseMessage(VivoProxyResponseMessage.OK, value)).build();
    }
    public static void main(String[]  args) throws IOException, OWLOntologyCreationException, OWLOntologyStorageException {
        VivoApiServiceImpl vivoApiServiceImpl = new VivoApiServiceImpl();
        String iri = "http://localhost:8080/vivo/individual/n6229";
        com.squareup.okhttp.Response reponse = vivoApiServiceImpl.run(iri);
        String ontoString = reponse.body().string();
        System.out.println(ontoString);
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
    private com.squareup.okhttp.Response run(String iri) throws IOException {
        CommandFactory cf = CommandFactory.getInstance();
        VivoReceiver session = new VivoReceiver();
        CommandInvoker invoker = new CommandInvoker();
        Command sparqlDescribeCommand = cf.createSparqlDescribeCommand(Credential.getLogin(), Credential.getPasswd(), iri, "application/rdf+xml");
        invoker.register(sparqlDescribeCommand);
        CommandResult resu = invoker.execute();
        com.squareup.okhttp.Response response = (com.squareup.okhttp.Response)resu.getResult();
        return response;
    }

}
