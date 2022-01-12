package ca.uqam.tool.vivoproxy.pattern.command.receiver;

import java.io.File;
import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.OWL2;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.VCARD4;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import ca.uqam.tool.util.credential.LOGIN;
import ca.uqam.tool.vivoproxy.pattern.command.AbstractReceiver;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.pattern.command.receiver.util.EditKeyForPosition;
import ca.uqam.tool.vivoproxy.pattern.command.util.VivoReceiverHelper;
import ca.uqam.tool.vivoproxy.swagger.api.NotFoundException;
import ca.uqam.tool.vivoproxy.swagger.model.AddressSchema;
import ca.uqam.tool.vivoproxy.swagger.model.AuthorOfADocument;
import ca.uqam.tool.vivoproxy.swagger.model.Concept;
import ca.uqam.tool.vivoproxy.swagger.model.Document;
import ca.uqam.tool.vivoproxy.swagger.model.Image;
import ca.uqam.tool.vivoproxy.swagger.model.IndividualType;
import ca.uqam.tool.vivoproxy.swagger.model.LinguisticLabel;
import ca.uqam.tool.vivoproxy.swagger.model.Organization;
import ca.uqam.tool.vivoproxy.swagger.model.Person;
import ca.uqam.tool.vivoproxy.swagger.model.PersonWithOfficeInfo;
import ca.uqam.tool.vivoproxy.swagger.model.PositionOfPerson;
import ca.uqam.tool.vivoproxy.swagger.model.ResourceToResource;
import ca.uqam.tool.vivoproxy.swagger.model.Statement;
import ca.uqam.tool.vivoproxy.util.SemanticWebMediaType;
import ca.uqam.tool.vivoproxy.util.SparqlHelper;
import ca.uqam.util.CertificateUtils;
import ca.uqam.vocab.uqam.UQAM_ORGANIZATION;
import ca.uqam.vocab.uqam.UQAM_PEOPLE;
import ca.uqam.vocab.vitro.VITRO;
import ca.uqam.vocab.vivo.OBO;
import ca.uqam.vocab.vivo.VCARD;
import ca.uqam.vocab.vivo.VIVO;

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

	private OkHttpClient httpClient = null;
	protected CookieManager cookieManager = null;
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
	}
	public OkHttpClient getHttpClient() {
		if (httpClient == null){
			cookieManager = new CookieManager();
			cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
			httpClient = CertificateUtils.getUnsafeOkHttpClient();
			httpClient = new OkHttpClient();
			httpClient.setCookieHandler(cookieManager);
			httpClient.setConnectTimeout(10, TimeUnit.SECONDS);
			httpClient.setWriteTimeout(5, TimeUnit.MINUTES);
			httpClient.setReadTimeout(5, TimeUnit.MINUTES);
		}
		return httpClient;
	}

	/**
	 * @param username
	 * @param password
	 * @return
	 * @throws IOException
	 */
	public CommandResult login(String username, String password) throws Exception {
		password = password;
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = RequestBody.create(mediaType, "loginName="+username+"&loginPassword="+password+"&loginForm=Connexion");
		String url = getSiteUrl() + "/authenticate";
		String origin = getHostName();
		String referer = getSiteUrl()+"/login";
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
		Response response=null;
		try {
			response = getHttpClient().newCall(request).execute();
		} catch (Exception e) {
			LOGGER.info(e.getCause()+ ": "+e.getMessage());
			throw e;
		}
		String html = response.body().string();
		if (!VivoReceiverHelper.isValidLogin(html)){
			throw new Exception("Invalid login");
		}
		return CommandResult.asCommandResult(response);
	}
	/**
	 * @return
	 * @throws IOException
	 */
	public CommandResult logout() throws IOException{
		String url = (getHostName()+"/"+getVivoSiteName() + "/logout").replaceAll("[/]+", "/");
		String referer = (getHostName()+"/"+getVivoSiteName()+"/people").replaceAll("[/]+", "/");
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
	@Override
	protected String getSiteUrl() {
		try {
			return (new URL(getHostName()+getVivoSiteName())).toString();
		} catch (MalformedURLException e) {
		} 
		return null;
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
	 * @deprecated
	 * @param person
	 * @return
	 * @throws IOException
	 */
	@Deprecated
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
	 * @throws Exception 
	 */
	public CommandResult addImageToIndividual(Image image) throws Exception{
		/* 
		 * Goto individual page
		 */
		Response response = VivoReceiverHelper.gotoIndividualPage(getSiteUrl(), getHttpClient(), image.getIndividualIRI());
		/*
		 * get editKey
		 */
	//	System.out.println("getEditKey");
		HttpUrl url = HttpUrl.parse(getSiteUrl() +"/uploadImages").newBuilder()
				.addQueryParameter("entityUri", image.getIndividualIRI())
				.addQueryParameter("action", "add")
				.build();
		HttpUrl refererUrl = HttpUrl.parse(getSiteUrl() +"/individual").newBuilder()
				.addQueryParameter("uri", image.getIndividualIRI())
				.build();
		String urlStr = "http://vivo-uqam.ca-central-1.elasticbeanstalk.com/uploadImages?entityUri=http%3A%2F%2Fpurl.org%2Fvivo.uqam.ca%2Fdata%2Fpeople%23abdallah_chahrazad_uqam_ca&action=add";
		String refererUrlStr = "http://vivo-uqam.ca-central-1.elasticbeanstalk.com/individual?uri=http%3A%2F%2Fpurl.org%2Fvivo.uqam.ca%2Fdata%2Fpeople%23abdallah_chahrazad_uqam_ca";
		Request request = new Request.Builder()
				.url(urlStr)
				.method("GET", null)
				.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:88.0) Gecko/20100101 Firefox/88.0")
				.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
				.addHeader("Accept-Language", "fr,fr-FR;q=0.8,en-US;q=0.5,en;q=0.3")
				.addHeader("Connection", "keep-alive")
				.addHeader("Referer", refererUrlStr)
				.addHeader("Upgrade-Insecure-Requests", "1")
				.build();
		response =  getHttpClient()
				.newCall(request).execute();

//		editKey = VivoReceiverHelper.getKeyValue(response.body().string());
//		if (editKey==null || editKey.isEmpty())
//			throw new Exception("Can't get an 'editKey', maybe you are not logged in");
//
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
		editKeyVar.setDomainUri(FOAF.Person.getURI());
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
	 * @throws Exception 
	 */
	public CommandResult addOrganization(Organization organization) throws Exception {
		Resource orgTypeRes = ResourceFactory.createResource(organization.getOrganizationType());
		String orgId = organization.getId();
		Model model = ModelFactory.createDefaultModel();
		Resource orgdURI = ResourceFactory.createResource(UQAM_ORGANIZATION.id.getURI() +orgId);
		model.add(orgdURI, RDF.type, OBO.BFO_0000001);
		model.add(orgdURI, RDF.type, OBO.BFO_0000002);
		model.add(orgdURI, RDF.type, OBO.BFO_0000004);
		model.add(orgdURI, RDF.type, orgTypeRes);
		model.add(orgdURI, RDF.type, OWL.Thing);
		model.add(orgdURI, RDF.type, FOAF.Agent);
		model.add(orgdURI, RDF.type, FOAF.Organization);
		model.add(orgdURI, VITRO.mostSpecificType, orgTypeRes);
		VivoReceiverHelper.addLinguisticLabelToResource(organization.getNames(), orgdURI,  RDFS.label, model);
		VivoReceiverHelper.addLinguisticLabelToResource(organization.getOverview(), orgdURI, VIVO.overview, model);
		try {
			SparqlHelper.updateVIVOWithModel(model);
		} catch (Exception e) {
			return CommandResult.asCommandResult(e.getMessage());
		}
		//		ByteArrayOutputStream modelString = new ByteArrayOutputStream();
		//		RDFDataMgr.write(modelString, model, Lang.TURTLE) ;
		String subjIRI = "NOT Found";
		try {
			subjIRI = model.listSubjects().toList().get(0).asResource().getURI();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return CommandResult.asCommandResult(subjIRI);
	}

	private static boolean isValid(List<LinguisticLabel> list) {
		return (list !=null && !list.isEmpty());
	}

	public CommandResult addPerson(PersonWithOfficeInfo person) throws IOException {
		Model model = ModelFactory.createDefaultModel();
		Resource resType = ResourceFactory.createResource(person.getPersonType());
		String eMail = person.getEMail();
		String uuid = eMail.replace("@", "_").replace(".", "_");
		Resource persUri = ResourceFactory.createResource(UQAM_PEOPLE.getURI() +uuid);
		model.add(persUri, RDF.type, OWL.Thing);
		model.add(persUri, OWL2.hasKey, person.getId());
		model.add(persUri, RDF.type, OBO.BFO_0000001);
		model.add(persUri, RDF.type, OBO.BFO_0000002);
		model.add(persUri, RDF.type, OBO.BFO_0000004);
		model.add(persUri, RDF.type, resType);
		model.add(persUri, RDF.type, FOAF.Agent);
		model.add(persUri, RDF.type, FOAF.Person);
		model.add(persUri, VITRO.mostSpecificType, resType);
		VivoReceiverHelper.addLinguisticLabelToResource(person.getDisplayName(), persUri, RDFS.label, model);


		/*
		 * Position
		 */
		/*
		 <http://purl.org/vivo.uqam.ca/data/people#abdallah_chahrazad_uqam_ca>        
		 	vivo:relatedBy  <http://purl.org/vivo-uqam.ca/individual/n4870> ;

		<http://purl.org/vivo-uqam.ca/individual/n4870>
        a                       obo:BFO_0000002 , owl:Thing , vivo:Position , obo:BFO_0000001 , vivo:FacultyPosition , vivo:Relationship , obo:BFO_0000020 ;
        rdfs:label              "Directeur du bureau"@fr-ca ;
        vitro:mostSpecificType  vivo:FacultyPosition ;
        vivo:relates            <http://purl.org/vivo.uqam.ca/data/organization#id6015> , <http://purl.org/vivo.uqam.ca/data/people#abdallah_chahrazad_uqam_ca> .



		 */
		if (isValid(person.getOrganizationUnitId()) && isValid(person.getTitle())) {
			Resource positionUri = model.createResource(persUri.getURI()+"-position");
			model.add(persUri, VIVO.relatedBy, positionUri);
			model.add(positionUri, RDF.type, OBO.BFO_0000002);
			model.add(positionUri, RDF.type, OWL.Thing);
			model.add(positionUri, RDF.type, VIVO.Position);
			model.add(positionUri, RDF.type, OBO.BFO_0000001);
			model.add(positionUri, RDF.type, resType);
			model.add(positionUri, RDF.type, VIVO.Relationship);
			model.add(positionUri, RDF.type, OBO.BFO_0000020);
			model.add(positionUri, VITRO.mostSpecificType, resType);
			model.add(positionUri, VIVO.relates, model.createResource(UQAM_ORGANIZATION.id.getURI()+person.getOrganizationUnitId()));
			model.add(positionUri, VIVO.relates, persUri);
			VivoReceiverHelper.addLinguisticLabelToResource(person.getTitle(), positionUri, RDFS.label, model);
		}
		/*
		 * Process: vcard:Individual part
		 */
		Resource vcardIndv = model.createResource(persUri.getURI()+"-vcard");
		model.add(vcardIndv, RDF.type, OWL.Thing);
		model.add(vcardIndv, RDF.type, VCARD.Kind);
		model.add(vcardIndv, RDF.type, VCARD.Individual);
		model.add(vcardIndv, RDF.type, OBO.BFO_0000031);
		model.add(vcardIndv, RDF.type, OBO.IAO_0000030);
		model.add(vcardIndv, RDF.type, OBO.BFO_0000002);
		model.add(vcardIndv, RDF.type, OBO.ARG_2000379);
		model.add(vcardIndv, RDF.type, OBO.BFO_0000001);
		model.add(persUri, OBO.ARG_2000028, vcardIndv);
		model.add(vcardIndv, OBO.ARG_2000029, persUri);
		model.add(vcardIndv, VITRO.mostSpecificType, VCARD4.Individual);

		/*
		 * Process: vcard:hasName part 
		 */
		Resource vcardHasName = model.createResource(persUri.getURI()+"-vcardHasName");
		model.add(vcardIndv, VCARD.hasName, vcardHasName);
		model.add(vcardHasName, RDF.type, OWL.Thing);
		model.add(vcardHasName, RDF.type, VCARD.Individual);
		model.add(vcardHasName, RDF.type, VCARD.Individual);
		model.add(vcardHasName, RDF.type, VCARD.Addressing);
		model.add(vcardHasName, RDF.type, VCARD.Explanatory);
		model.add(vcardHasName, RDF.type, VCARD.Communication);
		model.add(vcardHasName, RDF.type, VCARD.Name);
		model.add(vcardHasName, VITRO.mostSpecificType,VCARD.Name);
		List<LinguisticLabel> fName = person.getFirstName();
		List<LinguisticLabel> lName = person.getLastName();
		VivoReceiverHelper.addLinguisticLabelToResource(person.getFirstName(), vcardHasName, VCARD.givenName, model);
		VivoReceiverHelper.addLinguisticLabelToResource(person.getLastName(), vcardHasName, VCARD.familyName, model);

		/*
		 * Process: title 
		 */
		if (isValid(person.getTitle())) {
			Resource vcardHasTitle = model.createResource(persUri.getURI()+"-vcardHasTitle");
			model.add(vcardIndv, VCARD.hasTitle, vcardHasTitle);
			model.add(vcardHasTitle, RDF.type, OWL.Thing);
			model.add(vcardHasTitle, RDF.type, VCARD.Identification);
			model.add(vcardHasTitle, RDF.type, VCARD.Addressing);
			model.add(vcardHasTitle, RDF.type, VCARD.Communication);
			model.add(vcardHasTitle, RDF.type, VCARD.Geo);
			model.add(vcardHasTitle, RDF.type, VCARD.TimeZone);
			model.add(vcardHasTitle, RDF.type, VCARD.Security);
			model.add(vcardHasTitle, RDF.type, VCARD.Calendar);
			model.add(vcardHasTitle, RDF.type, VCARD.Explanatory);
			model.add(vcardHasTitle, RDF.type, VCARD.Organizational);
			model.add(vcardHasTitle, RDF.type, VCARD.Geographical);
			model.add(vcardHasTitle, RDF.type, VCARD.Title);
			model.add(vcardHasTitle, VITRO.mostSpecificType,VCARD.Title);
			VivoReceiverHelper.addLinguisticLabelToResource(person.getTitle(), vcardHasTitle, VCARD.title, model);
		}
		/*
		 * Process: email as mandatory
		 */
		Resource vcardHasEmail = model.createResource(persUri.getURI()+"-vcardHasEmail");
		model.add(vcardIndv, VCARD.hasEmail, vcardHasEmail);
		model.add(vcardHasEmail, RDF.type, OWL.Thing);
		model.add(vcardHasEmail, RDF.type,VCARD.Addressing);
		model.add(vcardHasEmail, RDF.type,VCARD.Code);
		model.add(vcardHasEmail, RDF.type,VCARD.Communication);
		model.add(vcardHasEmail, RDF.type,VCARD.Email);
		model.add(vcardHasEmail, RDF.type,VCARD.Explanatory);
		model.add(vcardHasEmail, RDF.type,VCARD.Identification);
		model.add(vcardHasEmail, RDF.type,VCARD.Type);
		model.add(vcardHasEmail, RDF.type,VCARD.Work);
		model.add(vcardHasEmail, VITRO.mostSpecificType,VCARD.Email);
		model.add(vcardHasEmail, VITRO.mostSpecificType,VCARD.Work);
		model.add(vcardHasEmail, VCARD.email, person.getEMail());

		/*
		 * Secondary eMail
		 */
		List<String> secEmails = person.getSecondaryMails();
		if (secEmails != null && !secEmails.isEmpty()) {
			Resource vcardHasSecEmail = model.createResource(persUri.getURI()+"-vcardSecEmail");
			model.add(vcardIndv, VCARD.hasEmail, vcardHasSecEmail);
			model.add(vcardHasEmail, RDF.type, OWL.Thing);
			model.add(vcardHasEmail, RDF.type,VCARD.Identification);
			model.add(vcardHasEmail, RDF.type,VCARD.Addressing);
			model.add(vcardHasEmail, RDF.type,VCARD.Explanatory);
			model.add(vcardHasEmail, RDF.type,VCARD.Communication);
			model.add(vcardHasEmail, RDF.type,VCARD.Email);
			model.add(vcardHasEmail, VITRO.mostSpecificType,VCARD.Email);
			model.add(vcardHasEmail, VCARD.email, person.getEMail());
			for (Iterator iterator = secEmails.iterator(); iterator.hasNext();) {
				String secEmail = (String) iterator.next();
				model.add(vcardHasSecEmail, VCARD.email, secEmail);
			}
		}

		/*
		 * Process telephone as optional
		 */
		String telephone = person.getTelephone();
		if (telephone != null && !telephone.isEmpty()) {
			Resource vcardHasTelephone = model.createResource(persUri.getURI()+"-vcardTelephone");
			model.add(vcardIndv, VCARD.hasTelephone, vcardHasTelephone);
			model.add(vcardHasTelephone, RDF.type, OWL.Thing);
			model.add(vcardHasTelephone, RDF.type,VCARD.Addressing);
			model.add(vcardHasTelephone, RDF.type,VCARD.Communication);
			model.add(vcardHasTelephone, RDF.type,VCARD.Explanatory);
			model.add(vcardHasTelephone, RDF.type,VCARD.Identification);
			model.add(vcardHasTelephone, RDF.type,VCARD.Telephone);
			model.add(vcardHasTelephone, VITRO.mostSpecificType,VCARD.Telephone);
			model.add(vcardHasTelephone, VCARD.telephone, telephone);
		}
		/*
		 * Process address as optional
		 */
		List<AddressSchema> addressList = person.getAddress();
		AddressSchema address;
		if (addressList != null && !addressList.isEmpty()) {
			Resource vcardHasAddress = model.createResource(persUri.getURI()+"-vcardAddress");
			model.add(vcardIndv, VCARD.hasAddress, vcardHasAddress);
			model.add(vcardHasAddress, RDF.type, OWL.Thing);
			model.add(vcardHasAddress, RDF.type,VCARD.Address);
			model.add(vcardHasAddress, RDF.type,VCARD.Explanatory);
			model.add(vcardHasAddress, RDF.type,VCARD.Identification);
			model.add(vcardHasAddress, RDF.type,VCARD.Addressing);
			model.add(vcardHasAddress, RDF.type,VCARD.Communication);
			model.add(vcardHasAddress, VITRO.mostSpecificType,VCARD.Address);
			address = addressList.get(0);
			String streetAdress = "";
			if (isValid(address.getOfficeNumber())) {
				streetAdress = address.getOfficeNumber() +",\n "; 
			}
			if (isValid(address.getStreetAddress())) {
				streetAdress += address.getStreetAddress();
			}
			model.add(vcardHasAddress, VCARD.streetAddress,streetAdress);
			if (isValid(address.getCountry())) {
				model.add(vcardHasAddress, VCARD.country, address.getCountry());
			}
			if (isValid(address.getLocality())) {
				model.add(vcardHasAddress, VCARD.locality,address.getLocality());
			}
			if (isValid(address.getRegion())) {
				model.add(vcardHasAddress, VCARD.region,address.getRegion());
			}
			if (isValid(address.getPostalCode())) {
				model.add(vcardHasAddress, VCARD.postalCode,address.getPostalCode());
			}
		}


		try {
			SparqlHelper.updateVIVOWithModel(model);
		} catch (Exception e) {
			return CommandResult.asCommandResult(e.getMessage());
		}
		return CommandResult.asCommandResult(persUri.getURI());
	}
	/**
	 * @param person
	 * @return
	 * @throws IOException
	 */
	public CommandResult _addPerson(PersonWithOfficeInfo person) throws IOException {
		String aType = person.getPersonType();
		String eMail = person.getEMail();
		String uuid = //eMail.split("@")[0];
				eMail.replace("@", "_").replace(".", "_");
		String telephone = person.getTelephone();
		AddressSchema address = person.getAddress().get(0);
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
		queryCore += "        ?vcardIndv obo:ARG_2000029 ?persIRI . \n " ; 
		queryCore += "        ?vcardIndv vcard:hasName ?vcardHasName . \n " ; 
		queryCore += "        ?vcardIndv vcard:hasEmail ?vcardHasEmail . \n " ; 
		//		queryCore += " 	?vcardIndv vcard:hasTitle ?vcardHasTitle . \n " ; 
		queryCore += "        ?vcardIndv vitro:mostSpecificType  vcard:Individual . \n" ; 
		/*
		 * Build vcard:Individual part vcard:hasName
		 */
		queryCore += " ?vcardHasName a owl:Thing , vcard:Identification , vcard:Addressing , vcard:Explanatory , vcard:Communication , vcard:Name . \n " ; 
		queryCore += "       ?vcardHasName vitro:mostSpecificType vcard:Name . \n " ; 

		for (Iterator iterator = fName.iterator(); iterator.hasNext();) {
			LinguisticLabel fNameLabel = (LinguisticLabel) iterator.next();
			queryCore += "       ?vcardHasName vcard:givenName \"" + fNameLabel.getLabel() +"\"@" + fNameLabel.getLanguage() + " . \n";
		}

		for (Iterator iterator2 = lName.iterator(); iterator2.hasNext();) {
			LinguisticLabel lNameLabel = (LinguisticLabel) iterator2.next();
			queryCore += "        ?vcardHasName vcard:familyName \"" + lNameLabel.getLabel() +"\"@" + lNameLabel.getLanguage() + " . \n";  
		}
		/*
		 * Process email as mandatory
		 */
		queryCore += " ?vcardHasEmail a owl:Thing , vcard:Addressing , vcard:Code , vcard:Communication , vcard:Email , vcard:Explanatory , vcard:Identification , vcard:Type , vcard:Work . \n";
		queryCore += "        ?vcardHasEmail vitro:mostSpecificType vcard:Email . \n";
		queryCore += "        ?vcardHasEmail vitro:mostSpecificType vcard:Work  . \n";
		queryCore += "        ?vcardHasEmail vcard:email \"" +eMail+ "\" . \n";
		/*
		 * Secondary eMail
		 */
		if (person.getSecondaryMails()!=null && !person.getSecondaryMails().isEmpty()) {
			queryCore += " ?vcardIndv vcard:hasEmail ?vcardHasSecEmail . \n " ; 
			queryCore += " ?vcardHasSecEmail a owl:Thing , vcard:Identification , vcard:Addressing , vcard:Explanatory , vcard:Communication , vcard:Email .\n";
			queryCore += "        ?vcardHasSecEmail vitro:mostSpecificType vcard:Email . \n";
			List<String> secEmail = person.getSecondaryMails();
			for (Iterator iterator = secEmail.iterator(); iterator.hasNext();) {
				String email = (String) iterator.next();
				queryCore += "        ?vcardHasSecEmail vcard:email \"" +email+ "\" . \n";
			}
		}
		/*
		 * Process telephone as optional
		 */
		if (telephone != null && !telephone.isEmpty()) {
			queryCore += " ?vcardIndv vcard:hasTelephone ?vcardHasTelephone . \n" ; 
			queryCore += " ?vcardHasTelephone a owl:Thing , vcard:Addressing , vcard:Communication , vcard:Explanatory , vcard:Identification , vcard:Telephone . \n";
			queryCore += "        ?vcardHasTelephone vitro:mostSpecificType vcard:Telephone . \n";
			queryCore += "        ?vcardHasTelephone vcard:telephone \"" +telephone+ "\" . \n";  
		}
		/*
		 * Process address as optional
		 */
		if (address != null) {
			queryCore += " ?vcardIndv vcard:hasAddress ?vcardHasAddress . \n" ; 
			queryCore += " ?vcardHasAddress a vcard:Address , vcard:Explanatory , owl:Thing , vcard:Identification , vcard:Addressing , vcard:Communication . \n";
			queryCore += "       ?vcardHasAddress vitro:mostSpecificType vcard:Address . \n";
			if (isValid(address.getCountry())) 
				queryCore += "       ?vcardHasAddress vcard:country-name \"" + address.getCountry() + "\" . \n";
			if (isValid(address.getLocality())) 
				queryCore += "       ?vcardHasAddress vcard:locality \"" + address.getLocality() + "\" . \n";
			if (isValid(address.getRegion())) 
				queryCore += "       ?vcardHasAddress vcard:region \"" + address.getRegion() + "\" . \n";
			if (isValid(address.getPostalCode())) 
				queryCore += "       ?vcardHasAddress vcard:postalCode \"" + address.getPostalCode() + "\" . \n";
			if (isValid(address.getStreetAddress()))
				queryCore += "       ?vcardHasAddress vcard:streetAddress \"" + address.getStreetAddress() + "\" . \n";




			//			List<String> streetList = address.getStreetAddress();
			//			if (streetList!=null && !streetList.isEmpty()) {
			//				Iterator<String> it = streetList.iterator();
			//				String streetLabel = (String) it.next();
			//				queryCore += "       ?vcardHasAddress vcard:streetAddress \""+streetLabel+"\"";
			//				while (it.hasNext()) {
			//					streetLabel = (String) it.next();
			//					queryCore +=  ", \""+ streetLabel+"\"";
			//				}
			//				queryCore += " . \n";
			//			}
			///			List<LinguisticLabel> countryLabel = address.get(0).getCountry();
			//			for (Iterator iterator = countryLabel.iterator(); iterator.hasNext();) {
			//				LinguisticLabel countryName = (LinguisticLabel) iterator.next();
			//				queryCore += "       ?vcardHasAddress vcard:country-name \"" + countryName.getLabel() +"\"@" + countryName.getLanguage() + " . \n";
			//			}

		}

		updateQuery += queryCore + "} \n} WHERE { \n";
		//		updateQuery += "	bind(URI(\"http://localhost:8080/vivo/individual/"+uuid+"-indv\") as ?vivoIndv) . \n";
		updateQuery += "	bind(URI(\"http://localhost:8080/vivo/individual/"+uuid+"-vcard\") as ?vcardIndv) . \n";
		updateQuery += "	bind(URI(\"http://localhost:8080/vivo/individual/"+uuid+"-vcardName\") as ?vcardHasName) . \n";
		updateQuery += "	bind(URI(\"http://localhost:8080/vivo/individual/"+uuid+"-vcardEmail\") as ?vcardHasEmail) . \n";
		if (person.getSecondaryMails()!=null && !person.getSecondaryMails().isEmpty()) {
			updateQuery += "	bind(URI(\"http://localhost:8080/vivo/individual/"+uuid+"-vcardSecEmail\") as ?vcardHasSecEmail) . \n";
		}
		if (telephone != null && !telephone.isEmpty()) {
			updateQuery += "	bind(URI(\"http://localhost:8080/vivo/individual/"+uuid+"-vcardTelephone\") as ?vcardHasTelephone) . \n";
		}
		if (address != null) {
			updateQuery += "	bind(URI(\"http://localhost:8080/vivo/individual/"+uuid+"-vcardAddress\") as ?vcardHasAddress) . \n";	
		}
		updateQuery += "	bind(URI(\"http://localhost:8080/vivo/individual/"+uuid+"\") as ?persIRI) . \n";
		updateQuery += " } " ;
		// System.out.println(updateQuery); System.exit(0); //print and exit
		//		 System.out.println(updateQuery); System.exit(0); //print and exit
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
	private static boolean isValid(String vaToValidate) {
		return (vaToValidate !=null && !vaToValidate.isEmpty());
	}
	public CommandResult addOrganization__(Organization organization) throws IOException {
		String uuid = UUID.randomUUID().toString();
		String orgType = organization.getOrganizationType();
		String orgId = organization.getId();
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
		updateQuery += queryCore + "} } WHERE \n{ <"+LOGIN.getVivoUrl() +"/individual/n> sfnc:hasNewIRI ?orgIRI . 	} " ;
		String bodyValue = 
				"email="+LOGIN.getUserName()+
				"&password="+LOGIN.getPasswd()+ 
				"&update="+updateQuery;
		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = RequestBody.create(mediaType, bodyValue);
		String sparqlUpdateUrl = (new URL(getSiteUrl()+"/api/sparqlUpdate")).toString();
		Request request = new Request.Builder()
				.url(sparqlUpdateUrl)
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
	@Deprecated
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
	@Deprecated
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
				editKeyVar.setDomainUri(FOAF.Person.getURI());
				editKeyVar.setRangeUri(VIVO.Position.getURI()); 
				editKey = VivoReceiverHelper.getEditKey(getHostName()+"/"+getVivoSiteName(), getHttpClient(),editKeyVar);

				HttpUrl url = HttpUrl.parse(getSiteUrl() +"/edit/process").newBuilder()
						.addQueryParameter("orgType", FOAF.Organization.getURI())
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
				.addQueryParameter("domainUri", FOAF.Person.getURI())
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
	public CommandResult ReindexSolr() throws IOException {

		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = RequestBody.create(mediaType, "rebuild=Reconstruire");
		String url = null;
		String referer = null;
		try {
			url = new URI(LOGIN.getVivoUrl()+"/"+LOGIN.getVivoSite() + "/SearchIndex?rebuild=true").toASCIIString();
			referer = new URI(LOGIN.getVivoUrl()+"/"+LOGIN.getVivoSite()+"/SearchIndex").toASCIIString();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Request request = new Request.Builder()
				.url(url)
				.method("POST", body)
				.addHeader("Accept-Language", "fr,fr-FR;q=0.8,en-US;q=0.5,en;q=0.3")
				.addHeader("Accept-Encoding", "gzip, deflate")
				.addHeader("Content-Type", "application/x-www-form-urlencoded")
				.addHeader("Connection", "keep-alive")
				.addHeader("Origin", LOGIN.getVivoUrl())
				.addHeader("Referer", referer)
				.addHeader("Upgrade-Insecure-Requests", "1")
				.build();
		Response response = getHttpClient().newCall(request).execute();
		LOGGER.info("Logout with return code: "+response.code() + " (" +response.message() +  ") ");
		return CommandResult.asCommandResult(response);	
	}
	public CommandResult Ping() throws IOException {
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		String url = null;
		String referer = null;
		try {
			url = new URI(LOGIN.getVivoUrl()+"/"+LOGIN.getVivoSite() + "/termsOfUse").toASCIIString();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Request request = new Request.Builder()
				.url(url)
				.method("GET", null)
				.addHeader("Accept-Language", "fr,fr-FR;q=0.8,en-US;q=0.5,en;q=0.3")
				.addHeader("Accept-Encoding", "gzip, deflate")
				.addHeader("Content-Type", "application/x-www-form-urlencoded")
				.addHeader("Connection", "keep-alive")
				.addHeader("Origin", LOGIN.getVivoUrl())
				.addHeader("Upgrade-Insecure-Requests", "1")
				.build();
		Response response = getHttpClient().newCall(request).execute();
		return CommandResult.asCommandResult(response);	
	}
	public static void main(String[] args) throws NotFoundException, IOException {
		VivoReceiver vr = new VivoReceiver();
		CommandResult result = vr.Ping();
		System.out.println(result);
	}

}
