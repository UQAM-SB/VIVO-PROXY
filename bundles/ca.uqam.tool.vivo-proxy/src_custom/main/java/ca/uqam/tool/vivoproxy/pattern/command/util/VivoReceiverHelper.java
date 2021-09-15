package ca.uqam.tool.vivoproxy.pattern.command.util;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import ca.uqam.tool.vivoproxy.pattern.command.receiver.VivoReceiver;
import ca.uqam.tool.vivoproxy.pattern.command.receiver.util.EditKeyForPosition;

public class VivoReceiverHelper {
	private final static Logger LOGGER = Logger.getLogger(VivoReceiverHelper.class.getName());

	public static final String FOAF_PERSON = "http://xmlns.com/foaf/0.1/Person";
	public static final String OBO_RO_0000053 = "http://purl.obolibrary.org/obo/RO_0000053";
	public static final String NEW_INDIVIDUAL_FORM_GENERATOR = "edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators.VIVONewIndividualFormGenerator";


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
			" PREFIX  ro:   <http://purl.obolibrary.org/obo/ro.owl#> \n";

    public static Response gotoAdminPage(String siteUrl, OkHttpClient httpClient) throws IOException {
        Request request = new Request.Builder()
                .url(siteUrl +"/siteAdmin")
                .method("GET", null)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:88.0) Gecko/20100101 Firefox/88.0")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .addHeader("Accept-Language", "fr,fr-FR;q=0.8,en-US;q=0.5,en;q=0.3")
                .addHeader("Connection", "keep-alive")
                .addHeader("Referer", siteUrl+"/")
                .addHeader("Upgrade-Insecure-Requests", "1")
                .build();
        Response response = httpClient.newCall(request).execute();
        return response;
    }
    public static Response gotoIndividualPage(String siteUrl, OkHttpClient httpClient, String personUri ) throws IOException {
        Request request = new Request.Builder()
                .url(personUri)
                .method("GET", null)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:88.0) Gecko/20100101 Firefox/88.0")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .addHeader("Accept-Language", "fr,fr-FR;q=0.8,en-US;q=0.5,en;q=0.3")
                .addHeader("Connection", "keep-alive")
                .addHeader("Referer", siteUrl+"/people")
                .addHeader("Upgrade-Insecure-Requests", "1")
                .build();
        Response response = httpClient.newCall(request).execute();
        return response;
    }
    
    public static String getEditKey(String siteUrl, OkHttpClient httpClient, 
            String subjectUri , 
            String predicateUri,
            String domainUri,
            String rangeUri) throws IOException {
        /* Example
         * http://192.168.7.23:8080/vivo/editRequestDispatch
         * subjectUri=http://localhost:8080/vivo/individual/n4851
         * predicateUri=http://purl.obolibrary.org/obo/RO_0000053
         * domainUri=http://xmlns.com/foaf/0.1/Person
         * rangeUri=http://vivoweb.org/ontology/core#MemberRole
         * */
        HttpUrl url = HttpUrl.parse(siteUrl +"/editRequestDispatch").newBuilder()
                .addQueryParameter("subjectUri", subjectUri)
                .addQueryParameter("predicateUri", predicateUri)
                .addQueryParameter("domainUri", domainUri)
                .addQueryParameter("rangeUri", rangeUri)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:88.0) Gecko/20100101 Firefox/88.0")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .addHeader("Accept-Language", "fr,fr-FR;q=0.8,en-US;q=0.5,en;q=0.3")
                .addHeader("Connection", "keep-alive")
                .addHeader("Referer", subjectUri )
                .addHeader("Upgrade-Insecure-Requests", "1")
                .build();
        Response response = httpClient.newCall(request).execute();
        return getKeyValue(response.body().string());
    }

    public static String getEditKey(String siteUrl, OkHttpClient httpClient, String vivoType) throws IOException {
        HttpUrl url = HttpUrl.parse(siteUrl +"/editRequestDispatch").newBuilder()
                .addQueryParameter("typeOfNew", vivoType)
                .addQueryParameter("editForm", "edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators.VIVONewIndividualFormGenerator")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:88.0) Gecko/20100101 Firefox/88.0")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .addHeader("Accept-Language", "fr,fr-FR;q=0.8,en-US;q=0.5,en;q=0.3")
                .addHeader("Connection", "keep-alive")
                .addHeader("Referer", siteUrl +"/siteAdmin")
                .addHeader("Upgrade-Insecure-Requests", "1")
                .build();
        Response response = httpClient.newCall(request).execute();
        String respUrl = response.networkResponse().request().urlString();
        String doc = response.body().string();
 //       System.out.println(doc);
        String editKey = getKeyValue(doc);  
		LOGGER.info(siteUrl +"/siteAdmin : editKey =  " + editKey + " return url="+respUrl+" with return code " + response.code());
        return editKey;
    }
    public static String getEditKey(String siteUrl, OkHttpClient httpClient, EditKeyForPosition editKeyVar) throws IOException {
        HttpUrl url = HttpUrl.parse(siteUrl +"/editRequestDispatch").newBuilder()
                .addQueryParameter("subjectUri", editKeyVar.getSubjectUri())
                .addQueryParameter("predicateUri", editKeyVar.getPredicateUri())
                .addQueryParameter("domainUri", editKeyVar.getDomainUri())
                .addQueryParameter("rangeUri", editKeyVar.getRangeUri())
                .build();
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:88.0) Gecko/20100101 Firefox/88.0")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .addHeader("Accept-Language", "fr,fr-FR;q=0.8,en-US;q=0.5,en;q=0.3")
                .addHeader("Connection", "keep-alive")
                .addHeader("Referer", editKeyVar.getSubjectUri() )
                .addHeader("Upgrade-Insecure-Requests", "1")
                .build();
        Response response = httpClient.newCall(request).execute();
        return getKeyValue(response.body().string());
    }

    private static String getKeyValue(String html) {
        String editKey = null;
        //
        // Extracting editKey value from html code returned by the get call. 
        //
        Document doc = Jsoup.parse(html);
        Elements inputs = doc.select("input");
        for (Iterator iterator = inputs.iterator(); iterator.hasNext();) {
            Element input = (Element) iterator.next();
            String name = input.attr("name");
            if ("editKey".equals(name)){
                editKey = input.attr("value");
            }
        }
        return editKey;
    }
    public static String getUriResponse(String body) {
        String uri = null;
        //
        // Extracting id-url value from html code return by the get call. 
        //
        Document doc = Jsoup.parse(body);
        Element uri_link = doc.getElementsByClass("uri-link").first();
        if (uri_link != null ) uri = uri_link.select("a").first().attr("href");        
        return uri;
    }
	public static String getNetworkUri(Response response) {
		String uri = null; 
		try {
			uri = response.networkResponse().request().urlString();
		} catch (Exception e) {
		}
		return uri;
	}
	public static String getUriResponse(Response result) throws IOException {
		return getUriResponse(result.body().string());
	}
}
