package ca.uqam.tool.vivoproxy.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;
import org.apache.jena.sparql.modify.UpdateProcessRemoteBase;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import ca.uqam.tool.util.credential.LOGIN;

public class SparqlHelper {
	public static String SparqlPrefix = "PREFIX  crdc: <http://purl.org/uqam.ca/vocabulary/crdc_ccrd#> \n"+
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
			" PREFIX  ro:   <http://purl.obolibrary.org/obo/ro.owl#> \n" +
			" PREFIX  sfnc: <http://vivoweb.org/sparql/function#> \n";

	public static final String FOAF_PERSON = "http://xmlns.com/foaf/0.1/Person";
	public static final String OBO_RO_0000053 = "http://purl.obolibrary.org/obo/RO_0000053";
	public static final String NEW_INDIVIDUAL_FORM_GENERATOR = "edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators.VIVONewIndividualFormGenerator";
	public static final List<QuerySolution> sendSelectQuery(String siteUrl , String queryStr){
		// L'URL du
		String sparqlEndpointUrl = siteUrl +"/api/sparqlQuery";
		// le libellé de la requête
		// la construction de la requête
		Query query = QueryFactory.create(queryStr);
		ResultSet resultSet = null;
		// Construction de l'exécuteur
		List<QuerySolution> solList = new ArrayList<>();
		try ( QueryExecution qexec = QueryExecutionFactory.sparqlService(sparqlEndpointUrl, query) ) {
			// L'authentification administrateur de vivo
			((QueryEngineHTTP)qexec).addParam("email", LOGIN.getUserName()) ;
			((QueryEngineHTTP)qexec).addParam("password", LOGIN.getPasswd()) ;
			// Lancer l'exécution
			resultSet = qexec.execSelect();
			while (resultSet.hasNext()) {
				QuerySolution qs = resultSet.nextSolution();
				solList.add(qs);
			}


			//Imprimer le résultat de la requête
			//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			//           ResultSetFormatter.out(outputStream, results);
			// Aussi disponible
			//     ResultSetFormatter.outputAsCSV(outputStream, results);
			//     ResultSetFormatter.outputAsTSV(outputStream, results);
			//     ResultSetFormatter.outputAsJSON(outputStream, results);
			//     ResultSetFormatter.outputAsXML(outputStream, results);
			//            String formatedResult = outputStream.toString();
			//            System.out.println(formatedResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return solList;
	}
	public static Response updateVIVOWithModel(Model model) throws IOException {
		String sparqlEndpointUrl = 	LOGIN.getSparqlUpdateURL();
		ByteArrayOutputStream modelTriples = new ByteArrayOutputStream();
		RDFDataMgr.write(modelTriples, model, Lang.NTRIPLES) ;
		String modelString;
		try {
			modelString = new String(modelTriples.toString("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			modelString = new String(modelTriples.toString());
		}
		String updateQuery = ""
				+ "INSERT DATA {"
				+ "   GRAPH <http://vitro.mannlib.cornell.edu/default/vitro-kb-2> { "
				+ modelString 
				+ "    } }";
		String bodyValue = 
				"email="+LOGIN.getUserName()+
				"&password="+LOGIN.getPasswd()+ 
				"&update="+updateQuery;
		//		OkHttpClient client = new OkHttpClient();
		OkHttpClient client = getUnsafeOkHttpClient();
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = RequestBody.create(mediaType, bodyValue);
		String sparqlUpdateUrl = (new URL(sparqlEndpointUrl)).toString();
		Request request = new Request.Builder()
				.url(sparqlUpdateUrl)
				.method("POST", body)
				.addHeader("Accept", SemanticWebMediaType.APPLICATION_RDF_XML.toString())
				.addHeader("Content-Type", "application/x-www-form-urlencoded")
				.build();
		Response response = null;
		try {
			response = client.newCall(request).execute();
		} catch (SSLPeerUnverifiedException e) {
			e.printStackTrace(System.err);
		}
		return response;
	}
	private static OkHttpClient getUnsafeOkHttpClient() {
		try {
			// Create a trust manager that does not validate certificate chains
			final TrustManager[] trustAllCerts = new TrustManager[] {
					new X509TrustManager() {
						@Override
						public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
						}

						@Override
						public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
						}

						@Override
						public java.security.cert.X509Certificate[] getAcceptedIssuers() {
							return null;
						}
					}
			};

			// Install the all-trusting trust manager
			final SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
			// Create an ssl socket factory with our all-trusting manager
			final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

			OkHttpClient okHttpClient = new OkHttpClient();
			okHttpClient.setSslSocketFactory(sslSocketFactory);
			okHttpClient.setHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});

			return okHttpClient;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * This should not be used in production unless you really don't care
	 * about the security. Use at your own risk.
	 */
}
