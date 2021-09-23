package ca.uqam.tool.vivoproxy.pattern.command.receiver;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

import ca.uqam.tool.util.credential.LOGIN;
import ca.uqam.tool.vivoproxy.pattern.command.AbstractReceiver;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.swagger.model.AuthorOfADocument;
import ca.uqam.tool.vivoproxy.swagger.model.IndividualType;
import ca.uqam.tool.vivoproxy.swagger.model.LinguisticLabel;
import ca.uqam.tool.vivoproxy.swagger.model.Statement;

/**
 * @author Michel Héon
 *
 */
public class VivoReceiver_addStatement_Test extends AbstractReceiver {
	String SparqlPrefix = "PREFIX  crdc: <http://purl.org/uqam.ca/vocabulary/crdc_ccrd#> \n"+
			" PREFIX  ocrer: <http://purl.org/net/OCRe/research.owl#> \n"+
			" PREFIX  p3:   <http://vivoweb.org/ontology/vitroAnnotfr_CA#> \n"+
			" PREFIX  owl:  <http://www.w3.org/2002/07/owl#> \n"+
			" PREFIX  scires: <http://vivoweb.org/ontology/scientific-research#> \n"+
			" PREFIX  xsd:  <http://www.w3.org/2001/XMLSchema#> \n"+
			" PREFIX  swrlb: <http://www.w3.org/2003/11/swrlb#> \n"+
			" PREFIX  skos: <http://www.w3.org/2004/02/skos/core#> \n"+
			" PREFIX  rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"+
			" PREFIX  ocresd: <http://purl.org/net/OCRe/study_design.owl#> \n"+
			" PREFIX  swo:  <http://www.ebi.ac.uk/efo/swo/> \n"+
			" PREFIX  cito: <http://purl.org/spar/cito/> \n"+
			" PREFIX  geo:  <http://aims.fao.org/aos/geopolitical.owl#> \n"+
			" PREFIX  ocresst: <http://purl.org/net/OCRe/statistics.owl#> \n"+
			" PREFIX  dcterms: <http://purl.org/dc/terms/> \n"+
			" PREFIX  vivo: <http://vivoweb.org/ontology/core#> \n"+
			" PREFIX  text: <http://jena.apache.org/text#> \n"+
			" PREFIX  event: <http://purl.org/NET/c4dm/event.owl#> \n"+
			" PREFIX  vann: <http://purl.org/vocab/vann/> \n"+
			" PREFIX  foaf: <http://xmlns.com/foaf/0.1/> \n"+
			" PREFIX  c4o:  <http://purl.org/spar/c4o/> \n"+
			" PREFIX  fabio: <http://purl.org/spar/fabio/> \n"+
			" PREFIX  swrl: <http://www.w3.org/2003/11/swrl#> \n"+
			" PREFIX  vcard: <http://www.w3.org/2006/vcard/ns#> \n"+
			" PREFIX  crdc-data: <http://purl.org/uqam.ca/vocabulary/crdc-ccrd/individual#> \n"+
			" PREFIX  vitro: <http://vitro.mannlib.cornell.edu/ns/vitro/0.7#> \n"+
			" PREFIX  vitro-public: <http://vitro.mannlib.cornell.edu/ns/vitro/public#> \n"+
			" PREFIX  rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"+
			" PREFIX  ocresp: <http://purl.org/net/OCRe/study_protocol.owl#> \n"+
			" PREFIX  bibo: <http://purl.org/ontology/bibo/> \n"+
			" PREFIX  obo:  <http://purl.obolibrary.org/obo/> \n"+
			" PREFIX  ro:   <http://purl.obolibrary.org/obo/ro.owl#> \n";
	public static final String FOAF_PERSON = "http://xmlns.com/foaf/0.1/Person";
	public static final String OBO_RO_0000053 = "http://purl.obolibrary.org/obo/RO_0000053";
	public static final String NEW_INDIVIDUAL_FORM_GENERATOR = "edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators.VIVONewIndividualFormGenerator";

	public static void main (String[] argv) throws IOException
	{
		String username=LOGIN.getUserName();
		String password=LOGIN.getPasswd();
		VivoReceiver vr = new VivoReceiver();
		vr.login(username, password);
		Statement stmt = new Statement();
		stmt.setSubject("http://localhost:8080/vivo/individual/n4253");
		stmt.setPredicate("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
		stmt.setObject("http://vivoweb.org/ontology/core#FacultyMember");
		CommandResult resu = vr.addaStatement(stmt)	;
		System.err.println(resu.getOkhttpResult().body().string());
	}

}
