package ca.uqam.tool.vivoproxy.pattern.command.receiver;

import java.io.File;
import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.jena.query.Query;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.eclipse.rdf4j.model.vocabulary.FOAF;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import ca.uqam.tool.util.credential.LOGIN;
import ca.uqam.tool.vivoproxy.pattern.command.AbstractReceiver;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.pattern.command.receiver.util.EditKeyForPosition;
import ca.uqam.tool.vivoproxy.pattern.command.util.VivoReceiverHelper;
import ca.uqam.tool.vivoproxy.swagger.model.AuthorOfADocument;
import ca.uqam.tool.vivoproxy.swagger.model.Concept;

import ca.uqam.tool.vivoproxy.swagger.model.Document;
import ca.uqam.tool.vivoproxy.swagger.model.Image;
import ca.uqam.tool.vivoproxy.swagger.model.IndividualType;
import ca.uqam.tool.vivoproxy.swagger.model.LinguisticLabel;
import ca.uqam.tool.vivoproxy.swagger.model.Organization;
import ca.uqam.tool.vivoproxy.swagger.model.Person;
import ca.uqam.tool.vivoproxy.swagger.model.PersonWithEmail;
import ca.uqam.tool.vivoproxy.swagger.model.PositionOfPerson;
import ca.uqam.tool.vivoproxy.swagger.model.ResourceToResource;
import ca.uqam.tool.vivoproxy.swagger.model.Statement;
import ca.uqam.tool.vivoproxy.util.SemanticWebMediaType;
import ca.uqam.tool.vivoproxy.util.SparqlHelper;
import ca.uqam.vivo.vocabulary.VIVO;

/**
 * @author Michel Héon
 *
 */
/**
 * @author Michel Héon; Université du Québec à Montréal
 * @filename VivoReceiver.java
 * @date 23 sept. 2021
 */
public class VivoReceiver extends AbstractReceiver {
	public static final String FOAF_PERSON = "http://xmlns.com/foaf/0.1/Person";
	public static final String OBO_RO_0000053 = "http://purl.obolibrary.org/obo/RO_0000053";
	public static final String NEW_INDIVIDUAL_FORM_GENERATOR = "edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators.VIVONewIndividualFormGenerator";

	private OkHttpClient httpClient;
	protected CookieManager cookieManager;
	protected String hostName="http://192.168.7.23:8080";
	protected String vivoSiteName="vivo";
	protected String editKey;


	/**
	 * 
	 */
	private final static Logger LOGGER = Logger.getLogger(VivoReceiver.class.getName());


	/**
	 * @param username
	 * @param password
	 * @return
	 * @throws IOException
	 */
	public VivoReceiver() {
		super();
		cookieManager = new CookieManager();
		cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
		httpClient = new OkHttpClient();
		httpClient.setCookieHandler(cookieManager);
		httpClient.setConnectTimeout(10, TimeUnit.SECONDS);
		httpClient.setWriteTimeout(5, TimeUnit.MINUTES);
		httpClient.setReadTimeout(5, TimeUnit.MINUTES);
	}
	public OkHttpClient getHttpClient() {
		return httpClient;
	}

	/**
	 * @param username
	 * @param password
	 * @return
	 * @throws IOException
	 */
	public CommandResult login(String username, String password) throws IOException {
		password = password +"";
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = RequestBody.create(mediaType, "loginName="+username+"&loginPassword="+password+"&loginForm=Connexion");
		String url = getHostName()+"/"+getVivoSiteName() + "/authenticate";
		String origin = getHostName();
		String referer = getHostName()+"/"+getVivoSiteName()+"/login";
		Request request = new Request.Builder()
				.url(url)
				.method("POST", body)
				.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
				.addHeader("Accept-Language", "fr,fr-FR;q=0.8,en-US;q=0.5,en;q=0.3")
				.addHeader("Content-Type", "application/x-www-form-urlencoded")
				.addHeader("Origin", origin)
				.addHeader("Connection", "keep-alive")
				.addHeader("Referer", referer)
				.addHeader("Upgrade-Insecure-Requests", "1")
				.build();
		Response response = getHttpClient().newCall(request).execute();
		LOGGER.info("Login for " + username +" with return code: "+response.code() + " (" +response.message() +  ") response url ("+ VivoReceiverHelper.getNetworkUri(response)+")");
		return CommandResult.asCommandResult(response);
	}
	/**
	 * @return
	 * @throws IOException
	 */
	public CommandResult logout() throws IOException{

		String url = getHostName()+"/"+getVivoSiteName() + "/logout";
		String referer = getHostName()+"/"+getVivoSiteName()+"/people";
		Request request = new Request.Builder()
				.url(url)
				.method("GET", null)
				.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
				.addHeader("Accept-Language", "fr,fr-FR;q=0.8,en-US;q=0.5,en;q=0.3")
				.addHeader("Connection", "keep-alive")
				.addHeader("Referer", referer)
				.addHeader("Upgrade-Insecure-Requests", "1")
				.build();
		Response response = getHttpClient().newCall(request).execute();
		LOGGER.info("Logout with return code: "+response.code() + " (" +response.message() +  ") ");
		return CommandResult.asCommandResult(response);
	}


	/* (non-Javadoc)
	 * @see ca.uqam.tool.vivoproxy.pattern.command.CommandResult#getSiteUrl()
	 */
	protected String getSiteUrl() {
		return getHostName()+"/"+getVivoSiteName();
	}
	
	public CommandResult addPerson(Person person) throws IOException {
		String aType = person.getPersonType();
		String uuid = UUID.randomUUID().toString();
		String updateQuery = SparqlHelper.SparqlPrefix 
				+ "INSERT { GRAPH <> { \n" ;
		String queryCore =
				" ?persIRI a <"+ aType +"> , owl:Thing , obo:BFO_0000004 , obo:BFO_0000001 , foaf:Agent , obo:BFO_0000002 , foaf:Person . \n" 
						+ " ?persIRI  vitro:mostSpecificType  <"+ aType +"> . \n"
						+ " ?persIRI  vitro:uuid  \""+ uuid +"\" . \n"
						+ " ?persIRI obo:ARG_2000028  ?vcardIndv . \n";
		/*
		 * Adding rdfs:label "name" for each language
		 */
		List<LinguisticLabel> fName = person.getFirstName();
		List<LinguisticLabel> lName = person.getLastName();
		for (Iterator iterator = fName.iterator(); iterator.hasNext();) {
			LinguisticLabel fNameLabel = (LinguisticLabel) iterator.next();
			for (Iterator iterator2 = lName.iterator(); iterator2.hasNext();) {
				LinguisticLabel lNameLabel = (LinguisticLabel) iterator2.next();
				if (fNameLabel.getLanguage().equals(lNameLabel.getLanguage())){
					String name = fNameLabel.getLabel() + " "+ lNameLabel.getLabel();
					queryCore += " ?persIRI rdfs:label \"" + name +"\"@" + fNameLabel.getLanguage() + " . \n";
				}
			}
		}
		/*
		 * Build vcard:Individual part
		 */
		queryCore += " ?vcardIndv a vcard:Kind , obo:BFO_0000031 , owl:Thing , obo:IAO_0000030 , obo:BFO_0000002 , obo:ARG_2000379 , obo:BFO_0000001 , vcard:Individual . \n " ;
		queryCore += " 	?vcardIndv obo:ARG_2000029 ?vivoIndv . \n " ; 
		queryCore += " 	?vcardIndv vcard:hasName ?vcardHasName . \n " ; 
		//		queryCore += " 	?vcardIndv vcard:hasTitle ?vcardHasTitle . \n " ; 
		queryCore += " 	?vcardIndv vitro:mostSpecificType  vcard:Individual . \n " ; 
		/*
		 * Build vcard:Individual part vcard:hasName
		 */
		queryCore += " ?vcardHasName a owl:Thing , vcard:Identification , vcard:Addressing , vcard:Explanatory , vcard:Communication , vcard:Name . \n " ; 
		queryCore += "    ?vcardHasName   vitro:mostSpecificType vcard:Name . \n " ; 
		for (Iterator iterator = fName.iterator(); iterator.hasNext();) {
			LinguisticLabel fNameLabel = (LinguisticLabel) iterator.next();
			queryCore += " 	?vcardHasName vcard:givenName \"" + fNameLabel.getLabel() +"\"@" + fNameLabel.getLanguage() + " . \n";
		}

		for (Iterator iterator2 = lName.iterator(); iterator2.hasNext();) {
			LinguisticLabel lNameLabel = (LinguisticLabel) iterator2.next();
			queryCore += " 	?vcardHasName vcard:familyName \"" + lNameLabel.getLabel() +"\"@" + lNameLabel.getLanguage() + " . \n";  
		}

		updateQuery += queryCore + "} } WHERE { \n";
		updateQuery += " <http://localhost:8080/vivo/individual/n> sfnc:hasNewIRI ?vivoIndv  ; \n"; 
		updateQuery += "	sfnc:hasNewIRI ?vcardIndv ; \n" ;
		updateQuery += " 	sfnc:hasNewIRI ?vcardHasName ; \n" ;
		updateQuery += "	sfnc:hasNewIRI ?persIRI . \n";
		updateQuery += " } " ;
		String bodyValue = 
				"email="+LOGIN.getUserName()+
				"&password="+LOGIN.getPasswd()+ 
				"&update="+updateQuery;
		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = RequestBody.create(mediaType, bodyValue);
		Request request = new Request.Builder()
				.url(getSiteUrl()+"/api/sparqlUpdate")
				.method("POST", body)
				.addHeader("Accept", SemanticWebMediaType.APPLICATION_RDF_XML.toString())
				.addHeader("Content-Type", "application/x-www-form-urlencoded")
				.build();
		Response response = client.newCall(request).execute();

		return DescribeByUUID(uuid.toString());

	}
	/**
	 * @param person
	 * @return
	 * @throws IOException
	 */
	public CommandResult addPerson(PersonWithEmail person) throws IOException {
		String aType = person.getPersonType();
		String eMail = person.getEMail();
		String uuid = eMail.split("@")[0];
		String updateQuery = SparqlHelper.SparqlPrefix 
				+ "INSERT { GRAPH <> { \n" ;
		String queryCore =
				" ?persIRI a <"+ aType +"> , owl:Thing , obo:BFO_0000004 , obo:BFO_0000001 , foaf:Agent , obo:BFO_0000002 , foaf:Person . \n" 
						+ " ?persIRI  vitro:mostSpecificType  <"+ aType +"> . \n"
						+ " ?persIRI obo:ARG_2000028  ?vcardIndv . \n";
		/*
		 * Adding rdfs:label "name" for each language
		 */
		List<LinguisticLabel> fName = person.getFirstName();
		List<LinguisticLabel> lName = person.getLastName();
		for (Iterator iterator = fName.iterator(); iterator.hasNext();) {
			LinguisticLabel fNameLabel = (LinguisticLabel) iterator.next();
			for (Iterator iterator2 = lName.iterator(); iterator2.hasNext();) {
				LinguisticLabel lNameLabel = (LinguisticLabel) iterator2.next();
				if (fNameLabel.getLanguage().equals(lNameLabel.getLanguage())){
					String name = fNameLabel.getLabel() + " "+ lNameLabel.getLabel();
					queryCore += " ?persIRI rdfs:label \"" + name +"\"@" + fNameLabel.getLanguage() + " . \n";
				}
			}
		}
		/*
		 * Build vcard:Individual part
		 */
		queryCore += " ?vcardIndv a vcard:Kind , obo:BFO_0000031 , owl:Thing , obo:IAO_0000030 , obo:BFO_0000002 , obo:ARG_2000379 , obo:BFO_0000001 , vcard:Individual . \n " ;
		queryCore += " 	?vcardIndv obo:ARG_2000029 ?vivoIndv . \n " ; 
		queryCore += " 	?vcardIndv vcard:hasName ?vcardHasName . \n " ; 
		queryCore += " 	?vcardIndv vcard:hasEmail ?vcardHasEmail . \n " ; 
		//		queryCore += " 	?vcardIndv vcard:hasTitle ?vcardHasTitle . \n " ; 
		queryCore += " 	  ?vcardIndv vitro:mostSpecificType  vcard:Individual . \n " ; 
		/*
		 * Build vcard:Individual part vcard:hasName
		 */
		queryCore += " ?vcardHasName a owl:Thing , vcard:Identification , vcard:Addressing , vcard:Explanatory , vcard:Communication , vcard:Name . \n " ; 
		queryCore += "     ?vcardHasName vitro:mostSpecificType vcard:Name . \n " ; 

		for (Iterator iterator = fName.iterator(); iterator.hasNext();) {
			LinguisticLabel fNameLabel = (LinguisticLabel) iterator.next();
			queryCore += " 	?vcardHasName vcard:givenName \"" + fNameLabel.getLabel() +"\"@" + fNameLabel.getLanguage() + " . \n";
		}

		for (Iterator iterator2 = lName.iterator(); iterator2.hasNext();) {
			LinguisticLabel lNameLabel = (LinguisticLabel) iterator2.next();
			queryCore += " 	?vcardHasName vcard:familyName \"" + lNameLabel.getLabel() +"\"@" + lNameLabel.getLanguage() + " . \n";  
		}
		queryCore += " ?vcardHasEmail a owl:Thing , vcard:Addressing , vcard:Code , vcard:Communication , vcard:Email , vcard:Explanatory , vcard:Identification , vcard:Type , vcard:Work . \n";
		queryCore += "     ?vcardHasEmail vitro:mostSpecificType vcard:Email . \n";
	    queryCore += "     ?vcardHasEmail vitro:mostSpecificType vcard:Work  . \n";
		queryCore += "     ?vcardHasEmail vcard:email \"" +eMail+ "\" . \n";  

		updateQuery += queryCore + "} } WHERE { \n";
//		updateQuery += " <http://localhost:8080/vivo/individual/n> sfnc:hasNewIRI ?vivoIndv  ; \n"; 
//		updateQuery += "	sfnc:hasNewIRI ?vcardIndv ; \n" ;
//		updateQuery += " 	sfnc:hasNewIRI ?vcardHasName ; \n" ;
//		updateQuery += " 	sfnc:hasNewIRI ?vcardHasEmail . \n" ;
		updateQuery += "	bind(URI(\"http://localhost:8080/vivo/individual/"+uuid+"-indv\") as ?vivoIndv) . \n";
		updateQuery += "	bind(URI(\"http://localhost:8080/vivo/individual/"+uuid+"-vcard\") as ?vcardIndv) . \n";
		updateQuery += "	bind(URI(\"http://localhost:8080/vivo/individual/"+uuid+"-vcardName\") as ?vcardHasName) . \n";
		updateQuery += "	bind(URI(\"http://localhost:8080/vivo/individual/"+uuid+"-vcardEmail\") as ?vcardHasEmail) . \n";
		updateQuery += "	bind(URI(\"http://localhost:8080/vivo/individual/"+uuid+"\") as ?persIRI) . \n";
		updateQuery += " } " ;
//		System.out.println(updateQuery);
//		System.exit(0);
		String bodyValue = 
				"email="+LOGIN.getUserName()+
				"&password="+LOGIN.getPasswd()+ 
				"&update="+updateQuery;
		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = RequestBody.create(mediaType, bodyValue);
		Request request = new Request.Builder()
				.url(getSiteUrl()+"/api/sparqlUpdate")
				.method("POST", body)
				.addHeader("Accept", SemanticWebMediaType.APPLICATION_RDF_XML.toString())
				.addHeader("Content-Type", "application/x-www-form-urlencoded")
				.build();
		Response response = client.newCall(request).execute();
//		System.out.println(response.body().string());
		String newIRI = "http://localhost:8080/vivo/individual/"+uuid;
//		System.out.println(newIRI);
		return DESCRIBE(LOGIN.getUserName(), LOGIN.getPasswd(), newIRI, SemanticWebMediaType.TEXT_PLAIN.toString());
	}
				//DescribeByUUID(uuid.toString());	}

	/**
	 * @deprecated
	 * @param person
	 * @return
	 * @throws IOException
	 */
	public CommandResult addPerson_(Person person) throws IOException {
		Response response = VivoReceiverHelper.gotoAdminPage(getHostName()+"/"+getVivoSiteName(), getHttpClient());
		LOGGER.info(getHostName()+"/"+getVivoSiteName() + "with return code " + response.code());
		// Get an editKey, a must to have before adding data to VIVO
		editKey = VivoReceiverHelper.getEditKey(getHostName()+"/"+getVivoSiteName(), getHttpClient(), person.getPersonType());
		//
		// Adding to VIVO
		String label = (person.getFirstName() != null ? person.getFirstName() + "+" : "")
				//				+ (person.getMiddleName() != null ? person.getMiddleName() + "+" : "")
				+ (person.getLastName() != null ? person.getLastName() : "");
		String bodyValue = (person.getFirstName() != null ? "firstName=" + person.getFirstName() : "")
				//				+ (person.getMiddleName() != null ? "&middleName=" + person.getMiddleName() : "")
				+ (person.getLastName()  != null ? "&lastName=" + person.getLastName()  : "")
				+"&label="+label+"&editKey="+editKey;

		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = RequestBody.create(mediaType, bodyValue);
		Request request = new Request.Builder()
				.url(getHostName()+"/"+getVivoSiteName()+"/edit/process")
				.method("POST", body)
				.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:88.0) Gecko/20100101 Firefox/88.0")
				.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
				.addHeader("Accept-Language", "fr,fr-FR;q=0.8,en-US;q=0.5,en;q=0.3")
				.addHeader("Connection", "keep-alive")
				.addHeader("Referer", 
						getHostName()+"/"+getVivoSiteName()+
						"/editRequestDispatch?typeOfNew="+person.getPersonType()+
						"&editForm=edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators.VIVONewIndividualFormGenerator")
				.addHeader("Upgrade-Insecure-Requests", "1")
				.addHeader("Content-Type", "application/x-www-form-urlencoded")
				.build();
		LOGGER.info("Sending "+ label );
		response = getHttpClient().newCall(request).execute();

		//		String responseUri = VivoReceiverHelper.getUriResponse(response.body().string());
		LOGGER.info("Adding "+ label + " with return code " + response.code());
		return CommandResult.asCommandResult(response);
	}    

	/**
	 * Upload image associated to an individual
	 * @param image
	 * @return
	 * @throws IOException
	 */
	public CommandResult addImageToIndividual(Image image) throws IOException{
		/* 
		 * Goto individual page
		 */
		Response response = VivoReceiverHelper.gotoIndividualPage(getHostName()+"/"+getVivoSiteName(), getHttpClient(), image.getIndividualIRI());
		/*
		 * get editKey
		 */
		System.out.println("getEditKey");
		HttpUrl url = HttpUrl.parse(getHostName()+"/"+getVivoSiteName() +"/uploadImages").newBuilder()
				.addQueryParameter("entityUri", image.getIndividualIRI())
				.addQueryParameter("action", "add")
				.build();
		Request request = new Request.Builder()
				.url(url)
				.method("GET", null)
				.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:88.0) Gecko/20100101 Firefox/88.0")
				.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
				.addHeader("Accept-Language", "fr,fr-FR;q=0.8,en-US;q=0.5,en;q=0.3")
				.addHeader("Connection", "keep-alive")
				.addHeader("Referer", image.getIndividualIRI())
				.addHeader("Upgrade-Insecure-Requests", "1")
				.build();
		response =  getHttpClient()
				.newCall(request).execute();

		editKey = VivoReceiverHelper.getKeyValue(response.body().string());

		/*
		 * UploadImage
		 */

		File file = new File(image.getImageURL());
		String contentType = file.toURL().openConnection().getContentType();
		RequestBody fileBody = RequestBody.create(MediaType.parse(contentType), file);

		RequestBody requestBody = new MultipartBuilder()
				.type(MultipartBuilder.FORM)
				.addFormDataPart("Content-Disposition","form-data; name=\"datafile\"; filename=\""+file.getName()+"\"")
				.addFormDataPart("Content-Type",contentType)
				.addFormDataPart("datafile",file.getName(),fileBody)
				.build();
		HttpUrl upLoadUrl = HttpUrl.parse(getHostName()+"/"+getVivoSiteName() +"/uploadImages").newBuilder()
				.addQueryParameter("entityUri", image.getIndividualIRI())
				.addQueryParameter("action", "upload")
				.addQueryParameter("imageUrl", image.getImageURL())
				.build();

		Request upLoadRequest = new Request.Builder()
				.url(upLoadUrl)
				.method("POST", requestBody)
				.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
				.addHeader("Accept-Language", "fr,fr-FR;q=0.8,en-US;q=0.5,en;q=0.3")
				.addHeader("Content-Type", "image/jpeg")
				.addHeader("Origin", getHostName())
				.addHeader("Connection", "keep-alive")
				.addHeader("Referer", url.toString())
				.addHeader("Upgrade-Insecure-Requests", "1")
				.addHeader("Sec-Fetch-Dest", "document")
				.addHeader("Sec-Fetch-Mode", "navigate")
				.addHeader("Sec-Fetch-Site", "same-origin")
				.addHeader("Sec-Fetch-User", "?1")
				.build();
		Response upLoadResponse =  getHttpClient().newCall(upLoadRequest).execute();
		/*
		 * Save image
		 */

		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody saveBody = RequestBody.create(mediaType, "x="+image.getOrigX()+"&y="+image.getOrigY()+"&w="+image.getWidth()+"&h="+image.getHeight());

		HttpUrl saveUrl = HttpUrl.parse(getHostName()+"/"+getVivoSiteName() +"/uploadImages").newBuilder()
				.addQueryParameter("entityUri", image.getIndividualIRI())
				.addQueryParameter("action", "save")
				.build();

		Request saveRequest = new Request.Builder()
				.url(saveUrl)
				.method("POST", saveBody)
				.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
				.addHeader("Accept-Language", "fr,fr-FR;q=0.8,en-US;q=0.5,en;q=0.3")
				.addHeader("Content-Type", "application/x-www-form-urlencoded")
				.addHeader("Origin", getHostName())
				.addHeader("Connection", "keep-alive")
				.addHeader("Referer", upLoadUrl.toString())
				.addHeader("Upgrade-Insecure-Requests", "1")
				.addHeader("Sec-Fetch-Dest", "document")
				.addHeader("Sec-Fetch-Mode", "navigate")
				.addHeader("Sec-Fetch-Site", "same-origin")
				.addHeader("Sec-Fetch-User", "?1")
				.build();
		Response saveResponse =  getHttpClient().newCall(saveRequest).execute();
		return CommandResult.asCommandResult(saveResponse);       

	}

	/**
	 * @param author
	 * @return
	 * @throws IOException
	 */
	public CommandResult addDocumentToPerson(AuthorOfADocument author) throws IOException {
		/* 
		 * Goto individual page
		 */
		Response response = VivoReceiverHelper.gotoIndividualPage(getHostName()+"/"+getVivoSiteName(), getHttpClient(), author.getDocumentIRI());
		/*
		 * Get the editKey
		 */
		EditKeyForPosition editKeyVar = new EditKeyForPosition();
		editKeyVar.setSubjectUri(author.getPersonIRI()); 
		editKeyVar.setPredicateUri(VIVO.relatedBy.getURI());
		editKeyVar.setDomainUri(FOAF.PERSON.stringValue());
		editKeyVar.setRangeUri(VIVO.Authorship.getURI());
		editKey = VivoReceiverHelper.getEditKey(getHostName()+"/"+getVivoSiteName(), getHttpClient(),editKeyVar);
		/*
		 * Build url and refUrl
		 */

		HttpUrl url = HttpUrl.parse(getSiteUrl() +"/edit/process").newBuilder()
				.addQueryParameter("title", "")
				.addQueryParameter("pubUri", author.getDocumentIRI())
				.addQueryParameter("collection", "")
				.addQueryParameter("collectionDisplay", "")
				.addQueryParameter("collectionUri", "")
				.addQueryParameter("book", "")
				.addQueryParameter("bookDisplay", "")
				.addQueryParameter("bookUri", "")
				.addQueryParameter("conference", "")
				.addQueryParameter("conferenceDisplay", "")
				.addQueryParameter("conferenceUri", "")
				.addQueryParameter("event", "")
				.addQueryParameter("eventDisplay", "")
				.addQueryParameter("eventUri", "")
				.addQueryParameter("editor", "")
				.addQueryParameter("editorDisplay", "")
				.addQueryParameter("editorUri", "")
				.addQueryParameter("publisher", "")
				.addQueryParameter("publisherDisplay", "")
				.addQueryParameter("publisherUri", "")
				.addQueryParameter("locale", "")
				.addQueryParameter("volume", "")
				.addQueryParameter("number", "")
				.addQueryParameter("issue", "")
				.addQueryParameter("chapterNbr", "")
				.addQueryParameter("startPage", "")
				.addQueryParameter("endPage", "")
				.addQueryParameter("dateTime-year", "")
				.addQueryParameter("editKey", editKey)
				.build();

		HttpUrl refererUrl = HttpUrl.parse(getSiteUrl() +"/editRequestDispatch").newBuilder()
				.addQueryParameter("subjectUri", editKeyVar.getSubjectUri())
				.addQueryParameter("predicateUri", editKeyVar.getPredicateUri())
				.addQueryParameter("domainUri", editKeyVar.getDomainUri())
				.addQueryParameter("rangeUri", editKeyVar.getRangeUri())
				.build();
		/*
		 * send Request
		 */
		Request request = new Request.Builder()
				.url(url)
				.method("GET", null)
				.addHeader("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:89.0) Gecko/20100101 Firefox/89.0")
				.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
				.addHeader("Accept-Language", "fr,fr-FR;q=0.8,en-US;q=0.5,en;q=0.3")
				.addHeader("Connection", "keep-alive")
				.addHeader("Referer", refererUrl.toString())
				.addHeader("Upgrade-Insecure-Requests", "1")
				.build();
		response = getHttpClient().newCall(request).execute();
		return CommandResult.asCommandResult(response);       
	}


	/**
	 * @param author
	 * @return
	 * @throws IOException
	 */
	public CommandResult addAuhorToDocument(AuthorOfADocument author) throws IOException {
		throw new NotImplementedException("addAuhorToDocument NOT Implemented"); 

		/* 
		 * Goto individual page
		 */
		//		Response response = VivoReceiverHelper.gotoIndividualPage(getHostName()+"/"+getVivoSiteName(), getHttpClient(), author.getDocumentIRI());
		/*
		 * Get the editKey
		 */
		//		EditKeyForPosition editKeyVar = new EditKeyForPosition();
		//		editKeyVar.setSubjectUri(author.getDocumentIRI()); 
		//		editKeyVar.setPredicateUri(VIVO.relatedBy.getURI());
		//		editKeyVar.setDomainUri("http://purl.obolibrary.org/obo/IAO_0000030");
		//		editKeyVar.setRangeUri(VIVO.Authorship.getURI());
		//		editKey = VivoReceiverHelper.getEditKey(getHostName()+"/"+getVivoSiteName(), getHttpClient(),editKeyVar);
		/*
		 * Build url and refUrl
		 */
		//		String lastName = (author.getLastName() != null ? author.getLastName() : "");
		//		String firstName = (author.getFirstName() != null ? author.getFirstName() : "");
		//		String middleName = (author.getMiddleName() != null ? author.getMiddleName() : "");
		//
		//		String label = (author.getFirstName() != null ? author.getFirstName() + " " : "")
		//				+ (author.getMiddleName() != null ? author.getMiddleName() + " " : "")
		//				+ (author.getLastName() != null ? author.getLastName() : "");
		//
		//		HttpUrl url = HttpUrl.parse(getSiteUrl() +"/edit/process").newBuilder()
		//				.addQueryParameter("authorType", author.getPersonType())
		//				.addQueryParameter("lastName",lastName)
		//				.addQueryParameter("firstName", firstName)
		//				.addQueryParameter("middleName", middleName)
		//				.addQueryParameter("personUri", author.getPersonIRI())
		//				.addQueryParameter("orgUri", "")
		//				.addQueryParameter("label",label)
		//				.addQueryParameter("rank", "1")
		//				.addQueryParameter("editKey", editKey)
		//				.addQueryParameter("submit-Create", "Create+Entry")
		//				.build();
		//		HttpUrl refererUrl = HttpUrl.parse(getSiteUrl() +"/editRequestDispatch").newBuilder()
		//				.addQueryParameter("subjectUri", editKeyVar.getSubjectUri())
		//				.addQueryParameter("predicateUri", editKeyVar.getPredicateUri())
		//				.addQueryParameter("domainUri", editKeyVar.getDomainUri())
		//				.addQueryParameter("rangeUri", editKeyVar.getRangeUri())
		//				.build();
		//		/*
		//		 * send Request
		//		 */
		//		Request request = new Request.Builder()
		//				.url(url)
		//				.method("GET", null)
		//				.addHeader("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:89.0) Gecko/20100101 Firefox/89.0")
		//				.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		//				.addHeader("Accept-Language", "fr,fr-FR;q=0.8,en-US;q=0.5,en;q=0.3")
		//				.addHeader("Connection", "keep-alive")
		//				.addHeader("Referer", refererUrl.toString())
		//				.addHeader("Upgrade-Insecure-Requests", "1")
		//				.build();
		//		response = getHttpClient().newCall(request).execute();
		//		return CommandResult.asCommandResult(response);       
	}
	/**
	 * @param organisationName
	 * @param vivoOrganisationType
	 * @return
	 * @throws IOException
	 */
	public CommandResult addOrganization(Organization organization) throws IOException {
		String uuid = UUID.randomUUID().toString();
		String orgType = organization.getOrganizationType();
		String updateQuery = SparqlHelper.SparqlPrefix 
				+ "INSERT { GRAPH <> { \n"
				+ "?orgIRI a foaf:Agent , foaf:Organization ,  obo:BFO_0000004 , obo:BFO_0000001 , obo:BFO_0000002 , owl:Thing, <"+ orgType +"> . \n"
				+ "?orgIRI  vitro:uuid  \""+ uuid +"\" . \n" ;
		List<LinguisticLabel> labels = organization.getNames();
		String queryCore = "";
		for (Iterator iterator = labels.iterator(); iterator.hasNext();) {
			LinguisticLabel aLabel = (LinguisticLabel) iterator.next();
			String subject = "?orgIRI ";
			String predicate = "<http://www.w3.org/2000/01/rdf-schema#label> ";
			String object = "\"" +aLabel.getLabel() +"\"@"+aLabel.getLanguage() + " . \n";
			queryCore+= subject;
			queryCore+= predicate;
			queryCore+= object;
		}		
		updateQuery += queryCore + "} } WHERE \n{ <http://localhost:8080/vivo/individual/n> sfnc:hasNewIRI ?orgIRI . 	} " ;
		String bodyValue = 
				"email="+LOGIN.getUserName()+
				"&password="+LOGIN.getPasswd()+ 
				"&update="+updateQuery;
		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = RequestBody.create(mediaType, bodyValue);
		Request request = new Request.Builder()
				.url(getSiteUrl()+"/api/sparqlUpdate")
				.method("POST", body)
				.addHeader("Accept", SemanticWebMediaType.APPLICATION_RDF_XML.toString())
				.addHeader("Content-Type", "application/x-www-form-urlencoded")
				.build();
		Response response = client.newCall(request).execute();
		return DescribeByUUID(uuid.toString());


		/*
		 * Retreive information
		 */
//		String describeQuery = SparqlHelper.SparqlPrefix + " describe ?orgIRI \n"+
//				" where { \n"
//				+ queryCore
//				+ " }";
//		bodyValue = 
//				"email="+LOGIN.getUserName()+
//				"&password="+LOGIN.getPasswd()+ 
//				"&query="+describeQuery;
//
//		LOGGER.fine(describeQuery);
//		client = new OkHttpClient();
//		mediaType = MediaType.parse("application/x-www-form-urlencoded");
//		body = RequestBody.create(mediaType, bodyValue);
//		request = new Request.Builder()
//				.url(getSiteUrl()+"/api/sparqlQuery")
//				.method("POST", body)
//				.addHeader("Accept", SemanticWebMediaType.TEXT_PLAIN.toString())
//				.addHeader("Content-Type", "application/x-www-form-urlencoded")
//				.build();
//		response = client.newCall(request).execute();
//		return CommandResult.asCommandResult(response);
	}
	/**
	 * @deprecated
	 * @param organization
	 * @return
	 * @throws IOException
	 */
	public CommandResult addOrganization_(Organization organization) throws IOException {
		List<LinguisticLabel> names = organization.getNames();
		int nameCtr = 0;
		String orgIRI = null;
		CommandResult returnVal = null;
		Response orgResp=null;;
		String resultString = null;
		/**
		 * Iterate for each langiages
		 */
		for (Iterator iterator = names.iterator(); iterator.hasNext();) {
			nameCtr++;
			LinguisticLabel name = (LinguisticLabel) iterator.next();
			if (nameCtr==1){
				/**
				 * Set Vivo to appropriate linguistic context
				 */
				VivoReceiverHelper.changeLinguisticContext(getHostName()+"/"+getVivoSiteName(), getHttpClient(), name.getLanguage());
				/*
				 * Get editKey
				 */
				Response response = VivoReceiverHelper.gotoAdminPage(getHostName()+"/"+getVivoSiteName(), getHttpClient());
				editKey = VivoReceiverHelper.getEditKey(getHostName()+"/"+getVivoSiteName(), getHttpClient(), organization.getOrganizationType());
				HttpUrl url = HttpUrl.parse(getSiteUrl() +"/edit/process").newBuilder()
						.addQueryParameter("label", name.getLabel())
						.addQueryParameter("editKey", editKey)
						.build();
				/*
				 * Create an Organization
				 */
				HttpUrl referedUrl = HttpUrl.parse(getSiteUrl() +"/editRequestDispatch").newBuilder()
						.addQueryParameter("editForm", NEW_INDIVIDUAL_FORM_GENERATOR)
						.addQueryParameter("typeOfNew", organization.getOrganizationType())
						.build();

				Request request = new Request.Builder()
						//                .url("http://192.168.7.23:8080/vivo/edit/process?label=Coll%C3%A8ge+de+Hull&editKey=44278894")
						.url(url)
						.method("GET", null)
						.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
						.addHeader("Accept-Language", "fr,fr-FR;q=0.8,en-US;q=0.5,en;q=0.3")
						.addHeader("Connection", "keep-alive")
						.addHeader("Referer", referedUrl.toString())
						.addHeader("Upgrade-Insecure-Requests", "1")
						.build();
				LOGGER.info("Sending "+ name.getLabel() );
				orgResp = getHttpClient().newCall(request).execute();
				resultString = orgResp.body().string();
				if (names.size() < 2 ){
					/*
					 * Case if only one language are defined
					 * Get result and exit
					 */
					return CommandResult.asCommandResult(resultString);
				} else {
					/*
					 * in case of more than one language
					 */
					orgIRI = VivoReceiverHelper.getUriResponse(resultString);
				}
			} else {
				/*
				 * For other language do addLabels
				 */
				List<LinguisticLabel> labels=new ArrayList<LinguisticLabel>();
				labels.add(name);
				returnVal = addLabels(orgIRI, labels);
			}
		}
		return CommandResult.asCommandResult(resultString);
	}

	public CommandResult addPositionOfPerson(PositionOfPerson position) throws IOException
	{
		String posType = position.getPositionTypeIRI();
		int posCtr = 0;
		CommandResult returnVal = null;
		Response orgResp=null;;
		String resultString = null;
		LinguisticLabel firstTitle = null;

		String updateConceptQuery = SparqlHelper.SparqlPrefix 
				+ "INSERT { GRAPH <> { \n"
				+ "   ?newIRI a <"+ posType +"> , owl:Thing , vivo:Position, vivo:Relationship , obo:BFO_0000020 , obo:BFO_0000001 , obo:BFO_0000002  . \n"
				+ "   ?newIRI vitro:mostSpecificType  <"+ posType +"> . \n";

		List<LinguisticLabel> labels = position.getPositionTitleLabel();
		for (Iterator iterator = labels.iterator(); iterator.hasNext();) {
			LinguisticLabel aLabel = (LinguisticLabel) iterator.next();
			String subject = "    ?newIRI ";
			String predicate = "<http://www.w3.org/2000/01/rdf-schema#label> ";
			String object = "\"" +aLabel.getLabel() +"\"@"+aLabel.getLanguage() + " . \n";
			updateConceptQuery+= subject;
			updateConceptQuery+= predicate;
			updateConceptQuery+= object;
		}		
		String persIRI = position.getPersonIRI();
		updateConceptQuery+= "<"+ persIRI +"> vivo:relatedBy  ?newIRI . \n";
		updateConceptQuery+= " ?newIRI vivo:relates  <"+ persIRI +">  . \n";
		String orgIRI = position.getOrganisationIRI();
		updateConceptQuery+= " ?newIRI vivo:relates  <"+ orgIRI +">  . \n";

		String startYear = position.getStartFieldYear();
		String endYear = position.getEndFieldYear();
		if (startYear!=null && !startYear.isEmpty() || (endYear!=null && !endYear.isEmpty()) ) {
			updateConceptQuery+= " ?newIRI vivo:dateTimeInterval  ?dateTimeIntervalIRI  . \n";
			updateConceptQuery+= " ?dateTimeIntervalIRI  a  owl:Thing , obo:BFO_0000038 , obo:BFO_0000001 , obo:BFO_0000008 , obo:BFO_0000003 , vivo:DateTimeInterval  . \n" ;
			updateConceptQuery+= " ?dateTimeIntervalIRI vitro:mostSpecificType  vivo:DateTimeInterval . \n" ;
		}
		if (startYear!=null && !startYear.isEmpty()) {
			updateConceptQuery+= " ?dateTimeIntervalIRI vivo:start  ?startYearIRI  . \n";
			updateConceptQuery+= " ?startYearIRI  a  owl:Thing , obo:BFO_0000148 , obo:BFO_0000001 , obo:BFO_0000008 , obo:BFO_0000003 , vivo:DateTimeValue  . \n" ;
			updateConceptQuery+= " ?startYearIRI vitro:mostSpecificType  vivo:DateTimeValue . \n" ;
			updateConceptQuery+= " ?startYearIRI vivo:dateTimePrecision  vivo:yearPrecision . \n" ;
			updateConceptQuery+= " ?startYearIRI vivo:dateTime \"" +startYear +"\"^^xsd:dateTime . \n" ;
		}
		if (endYear!=null && !endYear.isEmpty()) {
			updateConceptQuery+= " ?dateTimeIntervalIRI vivo:end  ?endYearIRI  . \n";
			updateConceptQuery+= " ?endYearIRI  a  owl:Thing , obo:BFO_0000148 , obo:BFO_0000001 , obo:BFO_0000008 , obo:BFO_0000003 , vivo:DateTimeValue  . \n" ;
			updateConceptQuery+= " ?endYearIRI vitro:mostSpecificType  vivo:DateTimeValue . \n" ;
			updateConceptQuery+= " ?endYearIRI vivo:dateTimePrecision  vivo:yearPrecision . \n" ;
			updateConceptQuery+= " ?endYearIRI vivo:dateTime \"" +endYear +"\"^^xsd:dateTime . \n" ;
		}

		/*
		 * Build Where Clause
		 */
		updateConceptQuery += "} } WHERE { \n"
				+ "    <http://localhost:8080/vivo/individual/n> sfnc:hasNewIRI ?newIRI . \n";
		if (startYear!=null && !startYear.isEmpty() || (endYear!=null && !endYear.isEmpty()) ) {
			updateConceptQuery += "    <http://localhost:8080/vivo/individual/n> sfnc:hasNewIRI ?dateTimeIntervalIRI . \n";
		}
		if (startYear!=null && !startYear.isEmpty()) {
			updateConceptQuery += "    <http://localhost:8080/vivo/individual/n> sfnc:hasNewIRI ?startYearIRI . \n";
		}
		if (endYear!=null && !endYear.isEmpty()) {
			updateConceptQuery += "    <http://localhost:8080/vivo/individual/n> sfnc:hasNewIRI ?endYearIRI . \n";
		}

		updateConceptQuery += "} " ;
		String bodyValue = 
				"email="+LOGIN.getUserName()+
				"&password="+LOGIN.getPasswd()+ 
				"&update="+updateConceptQuery;
//		System.out.println(updateConceptQuery);
		//		if (true ) return null;
		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = RequestBody.create(mediaType, bodyValue);
		Request request = new Request.Builder()
				.url(getSiteUrl()+"/api/sparqlUpdate")
				.method("POST", body)
				.addHeader("Accept", SemanticWebMediaType.APPLICATION_RDF_XML.toString())
				.addHeader("Content-Type", "application/x-www-form-urlencoded")
				.build();
		Response response = client.newCall(request).execute();
		return CommandResult.asCommandResult(response);




	}
	/**
	 * @deprecated
	 * @param position
	 * @return
	 * @throws IOException
	 */
	public CommandResult addPositionOfPerson_(PositionOfPerson position) throws IOException
	{
		List<LinguisticLabel> posTitleList = position.getPositionTitleLabel();
		int posCtr = 0;
		String orgIRI = null;
		CommandResult returnVal = null;
		Response orgResp=null;;
		String resultString = null;
		LinguisticLabel firstTitle = null;
		/**
		 * Iterate for each languages
		 */
		for (Iterator iterator = posTitleList.iterator(); iterator.hasNext();) {
			posCtr++;
			LinguisticLabel posTitle = (LinguisticLabel) iterator.next();
			if (posCtr==1){
				/**
				 * Set Vivo to appropriate linguistic context
				 */
				VivoReceiverHelper.changeLinguisticContext(getHostName()+"/"+getVivoSiteName(), getHttpClient(), posTitle.getLanguage());
				/*
				 * Get editKey
				 */

				/* 
				 * Goto individual page
				 */
				Response response = VivoReceiverHelper.gotoIndividualPage(getHostName()+"/"+getVivoSiteName(), getHttpClient(), position.getPersonIRI());
				/*
				 * Get the Edit Key for this operation
				 */
				EditKeyForPosition editKeyVar = new EditKeyForPosition();
				editKeyVar.setSubjectUri(position.getPersonIRI()); 
				editKeyVar.setPredicateUri(VIVO.relatedBy.getURI());
				editKeyVar.setDomainUri(FOAF.PERSON.stringValue());
				editKeyVar.setRangeUri(VIVO.Position.getURI()); 
				editKey = VivoReceiverHelper.getEditKey(getHostName()+"/"+getVivoSiteName(), getHttpClient(),editKeyVar);

				HttpUrl url = HttpUrl.parse(getSiteUrl() +"/edit/process").newBuilder()
						.addQueryParameter("orgType", FOAF.ORGANIZATION.stringValue())
						//				.addQueryParameter("orgLabel", "")
						//				.addQueryParameter("orgLabelDisplay", "Fondation nationale des sciences")
						.addQueryParameter("existingOrg", position.getOrganisationIRI())
						.addQueryParameter("positionTitle", posTitle.getLabel())
						.addQueryParameter("positionType", position.getPositionTypeIRI())
						.addQueryParameter("startField-year", position.getStartFieldYear())
						.addQueryParameter("endField-year", position.getEndFieldYear())
						.addQueryParameter("editKey", editKey)
						.addQueryParameter("submit-Create", "Create+Entry")
						.build();
				HttpUrl refererUrl = HttpUrl.parse(getSiteUrl() +"/editRequestDispatch").newBuilder()
						.addQueryParameter("subjectUri", editKeyVar.getSubjectUri())
						.addQueryParameter("predicateUri", editKeyVar.getPredicateUri())
						.addQueryParameter("domainUri", editKeyVar.getDomainUri())
						.addQueryParameter("rangeUri", editKeyVar.getRangeUri())
						.build();

				Request request = new Request.Builder()
						.url(url.toString())
						.method("GET", null)
						.addHeader("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:89.0) Gecko/20100101 Firefox/89.0")
						.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
						.addHeader("Accept-Language", "fr,fr-FR;q=0.8,en-US;q=0.5,en;q=0.3")
						.addHeader("Connection", "keep-alive")
						.addHeader("Referer", refererUrl.toString())
						.addHeader("Upgrade-Insecure-Requests", "1")
						.build();
				orgResp = getHttpClient().newCall(request).execute();
				/*
				 * Find IRI for Position
				 */
				String getPosIRIQuery = SparqlHelper.SparqlPrefix +
						"SELECT ?posIRI \n"
						+ "WHERE { \n"
						+ " 	<"+position.getPersonIRI()+"> vivo:relatedBy ?posIRI . \n"
						+ " 	?posIRI a <"+position.getPositionTypeIRI()+"> ;  \n"
						+ " 			rdfs:label ?label . \n"
						+ " 	filter(regex(str(?label), \""+posTitle.getLabel() + "\" )) . \n"
						+ " } ";
				List<QuerySolution> solResult = SparqlHelper.sendSelectQuery(getSiteUrl(), getPosIRIQuery);
				try {
					orgIRI = solResult.get(0).get("posIRI").asResource().getURI();
				} catch (Exception e) {
					// TODO: handle exception
				}
				//				for (Iterator iterator2 = solResult.iterator(); iterator2.hasNext();) {
				//					QuerySolution querySolution = (QuerySolution) iterator2.next();
				//					System.out.println(querySolution.toString());
				//				}

				if (posTitleList.size() < 2 ){
					return CommandResult.asCommandResult(orgIRI);
				} 
			} else {
				/*
				 * For other language do addLabels
				 */
				List<LinguisticLabel> labels=new ArrayList<LinguisticLabel>();
				labels.add(posTitle);
				returnVal = addLabels(orgIRI, labels);
			}
		}
		return CommandResult.asCommandResult(orgIRI);

	}

	/**
	 * @param personUri
	 * @param organizationUri
	 * @param organizationLabel
	 * @param roleLabel
	 * @param startField_year
	 * @param endField_year
	 * @param vivoOrganisationType
	 * @return
	 * @throws IOException
	 */
	public CommandResult addMemberOf(String personUri, String organizationUri, String organizationLabel, String roleLabel, String startField_year,
			String endField_year, String vivoOrganisationType) throws IOException {
		VivoReceiverHelper.gotoIndividualPage(getHostName()+"/"+getVivoSiteName(), getHttpClient(), personUri);

		Response response = VivoReceiverHelper.gotoAdminPage(getHostName()+"/"+getVivoSiteName(), getHttpClient());
		// Get an editKey, a must to have before adding data to VIVO
		editKey = VivoReceiverHelper.getEditKey(getHostName()+"/"+getVivoSiteName(), getHttpClient(), personUri, OBO_RO_0000053, FOAF_PERSON, vivoOrganisationType);

		HttpUrl url = HttpUrl.parse(getHostName()+"/"+getVivoSiteName() +"/edit/process").newBuilder()
				.addQueryParameter("roleActivityType", vivoOrganisationType)
				.addQueryParameter("activityLabel", null)
				.addQueryParameter("activityLabelDisplay", organizationLabel)
				.addQueryParameter("roleToActivityPredicate", null)
				.addQueryParameter("existingRoleActivity", organizationUri)
				.addQueryParameter("roleLabel", roleLabel)
				.addQueryParameter("startField-year", startField_year)
				.addQueryParameter("endField-year", endField_year)
				.addQueryParameter("editKey", editKey)
				.build();
		HttpUrl refererUrl = HttpUrl.parse(getHostName()+"/"+getVivoSiteName() +"/editRequestDispatch").newBuilder()
				.addQueryParameter("subjectUri", personUri)
				.addQueryParameter("predicateUri", OBO_RO_0000053)
				.addQueryParameter("domainUri", FOAF.PERSON.stringValue())
				.addQueryParameter("rangeUri", VIVO.MemberRole.getURI())
				.build();

		Request request = new Request.Builder()
				.url(url)
				.method("GET", null)
				.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:88.0) Gecko/20100101 Firefox/88.0")
				.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
				.addHeader("Accept-Language", "fr,fr-FR;q=0.8,en-US;q=0.5,en;q=0.3")
				.addHeader("Connection", "keep-alive")
				.addHeader("Referer", refererUrl.toString())
				.addHeader("Upgrade-Insecure-Requests", "1")
				.build();

		return CommandResult.asCommandResult(response);
	}

	/**
	 * @param username
	 * @param passwd
	 * @param IRI
	 * @param MIME_Type
	 * @return
	 * @throws IOException
	 */
	public CommandResult DESCRIBE(String username, String passwd, String IRI, String MIME_Type) throws IOException{
		String bodyValue = 
				"email="+username+
				"&password="+passwd+
				"&query=DESCRIBE <"+IRI+">";

		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = RequestBody.create(mediaType, bodyValue);
		Request request = new Request.Builder()
				.url(getSiteUrl()+"/api/sparqlQuery")
				.method("POST", body)
				.addHeader("Accept", MIME_Type)
				.addHeader("Content-Type", "application/x-www-form-urlencoded")
				.build();
		Response response = client.newCall(request).execute();
		return CommandResult.asCommandResult(response);
	}
	/**
	 * @param document
	 * @return
	 * @throws IOException
	 */
	public CommandResult addDocument(Document document) throws IOException{
		Response response = VivoReceiverHelper.gotoAdminPage(getHostName()+"/"+getVivoSiteName(), getHttpClient());
		LOGGER.info(getHostName()+"/"+getVivoSiteName() + "with return code " + response.code());
		// Get an editKey, a must to have before adding data to VIVO
		editKey = VivoReceiverHelper.getEditKey(getHostName()+"/"+getVivoSiteName(), getHttpClient(), document.getDocTypeIRI());
		String label = (document.getTitle() != null ? document.getTitle()  : "");
		String bodyValue = "&label="+label+"&editKey="+editKey;

		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = RequestBody.create(mediaType, bodyValue);
		Request request = new Request.Builder()
				.url(getHostName()+"/"+getVivoSiteName()+"/edit/process")
				.method("POST", body)
				.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:88.0) Gecko/20100101 Firefox/88.0")
				.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
				.addHeader("Accept-Language", "fr,fr-FR;q=0.8,en-US;q=0.5,en;q=0.3")
				.addHeader("Connection", "keep-alive")
				.addHeader("Referer", 
						getHostName()+"/"+getVivoSiteName()+
						"/editRequestDispatch?typeOfNew="+document.getDocTypeIRI()+
						"&editForm=edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators.VIVONewIndividualFormGenerator")
				.addHeader("Upgrade-Insecure-Requests", "1")
				.addHeader("Content-Type", "application/x-www-form-urlencoded")
				.build();
		LOGGER.info("Sending "+ label );
		response = getHttpClient().newCall(request).execute();

		//		String responseUri = VivoReceiverHelper.getUriResponse(response.body().string());
		LOGGER.info("Adding "+ label + " with return code " + response.code());
		return CommandResult.asCommandResult(response);
	}

	/**
	 * @param IRI
	 * @param labels
	 * @return
	 * @throws IOException
	 */
	public CommandResult addaStatement(Statement statement) throws IOException{
		String updateConceptQuery = ""
				+ "INSERT DATA  { GRAPH <> { " 
				+ "\n <" +statement.getSubject() +"> "
				+ "<" +statement.getPredicate() +"> "
				+ "<" +statement.getObject() +"> "
				+ " . \n } }";
		String bodyValue = 
				"email="+LOGIN.getUserName()+
				"&password="+LOGIN.getPasswd()+ 
				"&update="+updateConceptQuery;
//		System.out.println(bodyValue);
		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = RequestBody.create(mediaType, bodyValue);
		Request request = new Request.Builder()
				.url(getSiteUrl()+"/api/sparqlUpdate")
				.method("POST", body)
				.addHeader("Accept", SemanticWebMediaType.APPLICATION_RDF_XML.toString())
				.addHeader("Content-Type", "application/x-www-form-urlencoded")
				.build();
		Response response = client.newCall(request).execute();
		return CommandResult.asCommandResult(response);
	}

	/**
	 * @param IRI
	 * @param labels
	 * @return
	 * @throws IOException
	 */
	public CommandResult addLabels(String IRI, List<LinguisticLabel> labels) throws IOException{
		String updateConceptQuery = ""
				+ "INSERT DATA  { GRAPH <> { " ;
		String subjIrI = "\n <" +IRI +"> ";
		String predIRI = "<" +RDFS.label.getURI() +"> ";
		for (Iterator iterator = labels.iterator(); iterator.hasNext();) {
			LinguisticLabel label = (LinguisticLabel) iterator.next();
			String objLiteral = "\""+label.getLabel()+"\""+"@"+label.getLanguage() ;
			updateConceptQuery +=  subjIrI 
					+ predIRI
					+ objLiteral + " ." ;
		}
		updateConceptQuery += " \n } }";
		String bodyValue = 
				"email="+LOGIN.getUserName()+
				"&password="+LOGIN.getPasswd()+ 
				"&update="+updateConceptQuery;
		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = RequestBody.create(mediaType, bodyValue);
		Request request = new Request.Builder()
				.url(getSiteUrl()+"/api/sparqlUpdate")
				.method("POST", body)
				.addHeader("Accept", SemanticWebMediaType.APPLICATION_RDF_XML.toString())
				.addHeader("Content-Type", "application/x-www-form-urlencoded")
				.build();
		Response response = client.newCall(request).execute();
		return CommandResult.asCommandResult(response);

	}
	/**
	 * @param indvType
	 * @return
	 * @throws IOException 
	 */
	public CommandResult addType(IndividualType indvType) throws IOException{
		String subjIrI = "<" +indvType.getIndividualIRI() +"> ";
		String predIRI = "<" +RDF.type.getURI() +"> ";
		String objIrI = "<" +indvType.getVivoTypeIRI() +"> ";
		String updateConceptQuery = ""
				+ "INSERT DATA  { GRAPH <> { "
				+ subjIrI 
				+ predIRI
				+ objIrI +" . } }";
		String bodyValue = 
				"email="+LOGIN.getUserName()+
				"&password="+LOGIN.getPasswd()+ 
				"&update="+updateConceptQuery;
	//	System.out.println(bodyValue);
		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = RequestBody.create(mediaType, bodyValue);
		Request request = new Request.Builder()
				.url(getSiteUrl()+"/api/sparqlUpdate")
				.method("POST", body)
				.addHeader("Accept", SemanticWebMediaType.APPLICATION_RDF_XML.toString())
				.addHeader("Content-Type", "application/x-www-form-urlencoded")
				.build();
		Response response = client.newCall(request).execute();
		return CommandResult.asCommandResult(response);

	}
	/**
	 * @param username
	 * @param passwd
	 * @param concept
	 * @param MIME_Type
	 * @return
	 * @throws IOException
	 */
	public CommandResult addConcept(Concept concept) throws IOException{
		String iri = concept.getIRI();
		String uuid = UUID.randomUUID().toString();
		String updateConceptQuery = SparqlHelper.SparqlPrefix 
				+ "INSERT { GRAPH <> { \n";
		List<LinguisticLabel> labels = concept.getLabels();
		for (Iterator iterator = labels.iterator(); iterator.hasNext();) {
			LinguisticLabel conceptLabel = (LinguisticLabel) iterator.next();
			String subject = "?conceptIRI ";
			String predicate = "<http://www.w3.org/2000/01/rdf-schema#label> ";
			String object = "\"" +conceptLabel.getLabel() +"\"@"+conceptLabel.getLanguage() + " . \n";
			updateConceptQuery+= subject;
			updateConceptQuery+= predicate;
			updateConceptQuery+= object;
		}		
		updateConceptQuery += "?conceptIRI  <http://www.w3.org/1999/02/22-rdf-syntax-ns#type>  <http://www.w3.org/2004/02/skos/core#Concept> . \n" ;
		updateConceptQuery += "?conceptIRI  vitro:uuid  \""+ uuid +"\" . \n";

		if (concept.getIRI()==null || concept.getIRI().isEmpty()){
			updateConceptQuery +=  "} } WHERE \n{ <http://localhost:8080/vivo/individual/n> sfnc:hasNewIRI ?conceptIRI . } \n" ;
		} else {
			updateConceptQuery +=  "} } WHERE \n{ BIND (IRI(\""+iri+"\") AS ?conceptIRI) . } \n" ;		
		}
		System.out.println(updateConceptQuery);

		String bodyValue = 
				"email="+LOGIN.getUserName()+
				"&password="+LOGIN.getPasswd()+ 
				"&update="+updateConceptQuery;
		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = RequestBody.create(mediaType, bodyValue);
		Request request = new Request.Builder()
				.url(getSiteUrl()+"/api/sparqlUpdate")
				.method("POST", body)
				.addHeader("Accept", SemanticWebMediaType.APPLICATION_RDF_XML.toString())
				.addHeader("Content-Type", "application/x-www-form-urlencoded")
				.build();
		Response response = client.newCall(request).execute();
		System.out.println(response.body().string());
//		return CommandResult.asCommandResult(response);
		return DescribeByUUID(uuid.toString());

	}
	/**
	 * @param username
	 * @param passwd
	 * @param resourcesToLink
	 * @param MIME_Type
	 * @return
	 * @throws IOException
	 */
	public CommandResult addPersonHasResearchArea(String username, String passwd, ResourceToResource resourcesToLink, String MIME_Type) throws IOException{
		/*
		 * Build SPARQL update
		 */
		String updateConceptQuery = ""
				+ "INSERT DATA  { GRAPH <> { ";
		String subject = "<" +resourcesToLink.getSubjectIRI() +"> ";
		String predicate = "<" +VIVO.hasResearchArea.getURI() +"> ";
		String object = "<" +resourcesToLink.getObjectIRI() +"> ";
		updateConceptQuery+= subject;
		updateConceptQuery+= predicate;
		updateConceptQuery+= object;
		updateConceptQuery += " . }}" ;

		String bodyValue = 
				"email="+username+
				"&password="+passwd+
				"&update="+updateConceptQuery;
		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = RequestBody.create(mediaType, bodyValue);
		Request request = new Request.Builder()
				.url(getSiteUrl()+"/api/sparqlUpdate")
				.method("POST", body)
				.addHeader("Accept", MIME_Type)
				.addHeader("Content-Type", "application/x-www-form-urlencoded")
				.build();
		Response response = client.newCall(request).execute();
		return CommandResult.asCommandResult(response);

	}
	/**
	 * @param username
	 * @param passwd
	 * @param resourcesToLink
	 * @param MIME_Type
	 * @return
	 * @throws IOException
	 */
	public CommandResult addResearchAreaOfafPerson(String username, String passwd, ResourceToResource resourcesToLink, String MIME_Type) throws IOException{
		/*
		 * Build SPARQL update
		 */
		String updateConceptQuery = ""
				+ "INSERT DATA  { GRAPH <> { ";
		String subject = "<" +resourcesToLink.getSubjectIRI() +"> ";
		String predicate = "<" +VIVO.researchAreaOf.getURI() +"> ";
		String object = "<" +resourcesToLink.getObjectIRI() +"> ";
		updateConceptQuery+= subject;
		updateConceptQuery+= predicate;
		updateConceptQuery+= object;
		updateConceptQuery += " . }}" ;

		String bodyValue = 
				"email="+username+
				"&password="+passwd+
				"&update="+updateConceptQuery;
		/*
		 * send SPARQL update
		 */

		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = RequestBody.create(mediaType, bodyValue);
		Request request = new Request.Builder()
				.url(getSiteUrl()+"/api/sparqlUpdate")
				.method("POST", body)
				.addHeader("Accept", MIME_Type)
				.addHeader("Content-Type", "application/x-www-form-urlencoded")
				.build();
		Response response = client.newCall(request).execute();
		return CommandResult.asCommandResult(response);

	}

	/**
	 * @param username
	 * @param passwd
	 * @param IriList
	 * @return
	 * @throws IOException
	 */
	public CommandResult DESCRIBE(String username, String passwd, List<String> IriList, String MIME_Type) throws IOException{
		String bodyValue = 
				"email="+username+
				"&password="+passwd+
				"&query=DESCRIBE";

		for (Iterator iterator = IriList.iterator(); iterator.hasNext();) {
			String IRI = (String) iterator.next();
			bodyValue += " <"+IRI+"> ";
		}

		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = RequestBody.create(mediaType, bodyValue);
		Request request = new Request.Builder()
				.url(getSiteUrl()+"/api/sparqlQuery")
				.method("POST", body)
				.addHeader("Accept", MIME_Type)
				.addHeader("Content-Type", "application/x-www-form-urlencoded")
				.build();
		Response response = client.newCall(request).execute();

		return CommandResult.asCommandResult(response);
	}

	/**
	 * @param username
	 * @param passwd
	 * @param IRI
	 * @return
	 * @throws IOException
	 */
	public CommandResult DESCRIBE(String username, String passwd, String IRI) throws IOException{
		return DESCRIBE(username, passwd, IRI, "application/json");
	}
	/**
	 * @param personsList
	 * @return
	 * @throws IOException
	 */
	public CommandResult addPerson(List<Person> personsList) throws IOException {
		List<String> personsUriList = new ArrayList<>();

		for (Iterator iterator = personsList.iterator(); iterator.hasNext();) {
			Person person = (Person) iterator.next();
			CommandResult commandResult = addPerson(person);
			personsUriList.add(VivoReceiverHelper.getUriResponse(commandResult.getOkhttpResult()));
		}
		return CommandResult.asCommandResult(personsUriList);
	}
	/**
	 * Describe the IRI with specific vitro:uuid $uuid
	 * @param uuid
	 * @return
	 * @throws IOException
	 */
	public CommandResult DescribeByUUID(String uuid) throws IOException {
		String describeQuery = SparqlHelper.SparqlPrefix + 
				" DESCRIBE ?indv \n"+
				" WHERE { \n"+
				" ?indv vitro:uuid \""+uuid+"\" . \n"+
				" }";
		String bodyValue = 
				"email="+LOGIN.getUserName()+
				"&password="+LOGIN.getPasswd()+ 
				"&query="+describeQuery;

		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = RequestBody.create(mediaType, bodyValue);
		Request request = new Request.Builder()
				.url(getSiteUrl()+"/api/sparqlQuery")
				.method("POST", body)
				.addHeader("Accept", SemanticWebMediaType.TEXT_PLAIN.toString())
				.addHeader("Content-Type", "application/x-www-form-urlencoded")
				.build();
		Response response = client.newCall(request).execute();
		return CommandResult.asCommandResult(response);
	}
	/**
	 * @param login
	 * @param passwd
	 * @param label
	 * @param MIME_Type
	 * @return
	 * @throws IOException
	 */
	public CommandResult DescribeByLabel(String login, String passwd, String label, String MIME_Type) throws IOException {
		String describeQuery = SparqlHelper.SparqlPrefix + " describe ?s \n"+
				" where { \n"+
				" ?s rdfs:label ?label . \n"+
				" FILTER (LCASE(STR(?label))=LCASE(STR(\""+label+"\"))) \n"+
				" }";
		String bodyValue = 
				"email="+login+
				"&password="+passwd+
				"&query="+describeQuery;

		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = RequestBody.create(mediaType, bodyValue);
		Request request = new Request.Builder()
				.url(getSiteUrl()+"/api/sparqlQuery")
				.method("POST", body)
				.addHeader("Accept", MIME_Type)
				.addHeader("Content-Type", "application/x-www-form-urlencoded")
				.build();
		Response response = client.newCall(request).execute();
		return CommandResult.asCommandResult(response);
	}
}
