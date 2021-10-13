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
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

import ca.uqam.tool.vivoproxy.util.SemanticWebMediaType;
import io.swagger.util.Json;

@Provider
@Produces({
	"text/turtle",
	"application/ld+json", 
	"application/n-triples",
	"text/plain",
	"application/rdf+xml",
	"text/n3",
	"text/owl-functional",
	"text/owl-manchester",
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
			if (SemanticWebMediaType.TEXT_TURTLE.toString().equals(mediaType.toString())) {
				owlDocFormat = new TurtleDocumentFormat();
				setPrefix(owlDocFormat);
			} else if (SemanticWebMediaType.APPLICATION_LD_JSON.toString().equals(mediaType.toString())) {
				owlDocFormat = new RDFJsonLDDocumentFormat();
				setPrefix(owlDocFormat);
			} else if (SemanticWebMediaType.TEXT_PLAIN.toString().equals(mediaType.toString())) {
				owlDocFormat = new NTriplesDocumentFormat();
				setPrefix(owlDocFormat);
			} else if (SemanticWebMediaType.APPLICATION_RDF_XML.toString().equals(mediaType.toString())) {
				owlDocFormat = new RDFXMLDocumentFormat();
				setPrefix(owlDocFormat);
			} else if (SemanticWebMediaType.TEXT_N3.toString().equals(mediaType.toString())) {
				owlDocFormat = new N3DocumentFormat();
				setPrefix(owlDocFormat);
			} else if (SemanticWebMediaType.TEXT_OWL_FUNCTIONAL.toString().equals(mediaType.toString())) {
				owlDocFormat = new FunctionalSyntaxDocumentFormat();
				setPrefix(owlDocFormat);
			} else if (SemanticWebMediaType.TEXT_OWL_MANCHESTER.toString().equals(mediaType.toString())) {
				owlDocFormat = new ManchesterSyntaxDocumentFormat();
				setPrefix(owlDocFormat);
			} else if (SemanticWebMediaType.APPLICATION_OWL_XML.toString().equals(mediaType.toString())) {
				owlDocFormat = new OWLXMLDocumentFormat();
				setPrefix(owlDocFormat);
			} else {
				owlDocFormat = new TurtleDocumentFormat();
				setPrefix(owlDocFormat);
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
	private void setPrefix(OWLDocumentFormat owlDocFormat) {
		if (owlDocFormat.isPrefixOWLDocumentFormat()) {
			owlDocFormat.asPrefixOWLDocumentFormat().setPrefix("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
			owlDocFormat.asPrefixOWLDocumentFormat().setPrefix("obo", "http://purl.obolibrary.org/obo/");
			owlDocFormat.asPrefixOWLDocumentFormat().setPrefix("study_protocol", "http://purl.org/net/OCRe/study_protocol.owl#");
			owlDocFormat.asPrefixOWLDocumentFormat().setPrefix("ns", "http://www.w3.org/2003/06/sw-vocab-status/ns#");
			owlDocFormat.asPrefixOWLDocumentFormat().setPrefix("skos", "http://www.w3.org/2004/02/skos/core#");
			owlDocFormat.asPrefixOWLDocumentFormat().setPrefix("geopolitical", "http://aims.fao.org/aos/geopolitical.owl#");
			owlDocFormat.asPrefixOWLDocumentFormat().setPrefix("vitro", "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#");
			owlDocFormat.asPrefixOWLDocumentFormat().setPrefix("skos2", "http://www.w3.org/2008/05/skos#");
			owlDocFormat.asPrefixOWLDocumentFormat().setPrefix("core", "http://vivoweb.org/ontology/core#");
			owlDocFormat.asPrefixOWLDocumentFormat().setPrefix("terms", "http://purl.org/dc/terms/");
			owlDocFormat.asPrefixOWLDocumentFormat().setPrefix("statistics", "http://purl.org/net/OCRe/statistics.owl#");
			owlDocFormat.asPrefixOWLDocumentFormat().setPrefix("crdc-ccrd-data", "http://purl.org/uqam.ca/vocabulary/crdc-ccrd/individual#");
			owlDocFormat.asPrefixOWLDocumentFormat().setPrefix("crdc-ccrd", "http://purl.org/uqam.ca/vocabulary/crdc_ccrd#");

		}

	}
}
