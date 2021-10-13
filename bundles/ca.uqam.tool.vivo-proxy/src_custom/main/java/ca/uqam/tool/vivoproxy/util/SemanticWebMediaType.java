package ca.uqam.tool.vivoproxy.util;

import javax.ws.rs.core.MediaType;

public interface SemanticWebMediaType {
	static final MediaType APPLICATION_RDF_XML = MediaType.valueOf("application/rdf+xml");
	static final MediaType TEXT_PLAIN = MediaType.valueOf("text/plain");
	static final MediaType APPLICATION_OWL_XML = MediaType.valueOf("application/owl+xml");
	static final MediaType TEXT_OWL_MANCHESTER = MediaType.valueOf("text/owl-manchester");
	static final MediaType TEXT_OWL_FUNCTIONAL = MediaType.valueOf("text/owl-functional");
	static final MediaType TEXT_N3 = MediaType.valueOf("text/n3");
	static final MediaType TEXT_NT = MediaType.valueOf("application/n-triples");
	static final MediaType APPLICATION_LD_JSON = MediaType.valueOf("application/ld+json");
	static final MediaType TEXT_TURTLE = MediaType.valueOf("text/turtle");
}
