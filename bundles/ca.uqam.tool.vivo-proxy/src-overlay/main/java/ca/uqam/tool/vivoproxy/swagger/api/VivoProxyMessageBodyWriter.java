package ca.uqam.tool.vivoproxy.swagger.api;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.N3DocumentFormat;
import org.semanticweb.owlapi.formats.NTriplesDocumentFormat;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RDFJsonLDDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import io.swagger.util.Json;

@Provider
@Produces({
    "application/json", 
    "text/plain",
    "application/rdf+xml",
    "text/n3",
    "text/turtle",
	"text/funtional",
	"text/manchester",
    "application/owl+xml"
	})
public class VivoProxyMessageBodyWriter implements MessageBodyWriter<VivoProxyResponseMessage> {

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return VivoProxyResponseMessage.class.isAssignableFrom(type);
	}
	@Override
	public long getSize(VivoProxyResponseMessage o, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return -1;
	}
	@Override
	public void writeTo(VivoProxyResponseMessage msg, Class<?> type, Type genericType, Annotation[] annotations,
			MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
					throws IOException, WebApplicationException {
		String ontoString = msg.getMessage();
		try {
			OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
			OWLOntology ontology = manager.loadOntologyFromOntologyDocument(new ByteArrayInputStream(ontoString.getBytes()));
			OWLDocumentFormat owlDocFormat = null;
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			if ("text/turtle".equals(mediaType.toString())) {
				owlDocFormat = new TurtleDocumentFormat();
			} else if ("application/json".equals(mediaType.toString())) {
				owlDocFormat = new RDFJsonLDDocumentFormat();
			} else if ("text/plain".equals(mediaType.toString())) {
				owlDocFormat = new NTriplesDocumentFormat();
			} else if ("application/rdf+xml".equals(mediaType.toString())) {
				owlDocFormat = new RDFXMLDocumentFormat();
			} else if ("text/n3".equals(mediaType.toString())) {
				owlDocFormat = new N3DocumentFormat();
			} else if ("text/funtional".equals(mediaType.toString())) {
				owlDocFormat = new FunctionalSyntaxDocumentFormat();
			} else if ("text/manchester".equals(mediaType.toString())) {
				owlDocFormat = new ManchesterSyntaxDocumentFormat();
			} else if ("application/owl+xml".equals(mediaType.toString())) {
				owlDocFormat = new OWLXMLDocumentFormat();
			} else {
				owlDocFormat = new TurtleDocumentFormat();
			}
			manager.saveOntology(ontology, owlDocFormat, stream);
			entityStream.write(stream.toByteArray());
		} catch (OWLOntologyStorageException e) {
			// TODO Bloc catch auto-généré
			e.printStackTrace();
		} catch (OWLOntologyCreationException e) {
			// TODO Bloc catch auto-généré
			e.printStackTrace();
		}
	}
}
