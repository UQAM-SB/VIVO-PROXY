package ca.uqam.tool.vivoproxy.pattern.command.receiver;

import java.io.File;
import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.apache.commons.lang3.NotImplementedException;
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
import ca.uqam.tool.vivoproxy.swagger.model.ConceptLabel;
import ca.uqam.tool.vivoproxy.swagger.model.Document;
import ca.uqam.tool.vivoproxy.swagger.model.Image;
import ca.uqam.tool.vivoproxy.swagger.model.Person;
import ca.uqam.tool.vivoproxy.swagger.model.PositionOfPerson;
import ca.uqam.tool.vivoproxy.swagger.model.ResourceToResource;
import ca.uqam.tool.vivoproxy.util.SemanticWebMediaType;
import ca.uqam.vivo.vocabulary.VIVO;

/**
 * @author Michel Héon
 *
 */
public class VivoReceiver extends AbstractReceiver {
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

	private OkHttpClient httpClient;
	protected CookieManager cookieManager;
	protected String hostName="http://192.168.7.23:8080";
	protected String vivoSiteName="vivo";
	protected String editKey;


	/**
	 * 
	 */
	private final static Logger LOGGER = Logger.getLogger(VivoReceiver.class.getName());


	public static void tst_add_doc_main (String[] argv) throws IOException
	{
		String username=LOGIN.getUserName();
		String password=LOGIN.getPasswd();
		VivoReceiver vr = new VivoReceiver();
		Document document = new Document();
		document.docTypeIRI("http://purl.org/ontology/bibo/Book");
		document.title("Web sémantique et modélisation V2");
		vr.login(username, password);
		CommandResult resu = vr.addDocument(document);

		//		Person person = new Person();
		//		person.firstName("toto");
		//		person.lastName("last");
		//		person.setPersonType("http://vivoweb.org/ontology/core#FacultyMember");
		//		CommandResult resu = vr.addPerson(person);
		System.err.println(resu.getOkhttpResult().body().string());
	}


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

	/**
	 * @param organisationName
	 * @param vivoOrganisationType
	 * @return
	 * @throws IOException
	 */
	public CommandResult addOrganization(String organisationName, String vivoOrganisationType) throws IOException {
		Response response = VivoReceiverHelper.gotoAdminPage(getHostName()+"/"+getVivoSiteName(), getHttpClient());
		editKey = VivoReceiverHelper.getEditKey(getHostName()+"/"+getVivoSiteName(), getHttpClient(), vivoOrganisationType);
		HttpUrl url = HttpUrl.parse(getSiteUrl() +"/edit/process").newBuilder()
				.addQueryParameter("label", organisationName)
				.addQueryParameter("editKey", editKey)
				.build();
		HttpUrl referedUrl = HttpUrl.parse(getSiteUrl() +"/editRequestDispatch").newBuilder()
				.addQueryParameter("editForm", NEW_INDIVIDUAL_FORM_GENERATOR)
				.addQueryParameter("typeOfNew", vivoOrganisationType)
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
		LOGGER.info("Sending "+ organisationName );
		Response orgResp = getHttpClient().newCall(request).execute();
		//		String responseUri = VivoReceiverHelper.getUriResponse(orgResp.body().string());
		//		LOGGER.info("Adding "+ organisationName + "at uri "+ responseUri+" with return code " + response.code());
		return CommandResult.asCommandResult(orgResp);
		//        return CommandResult.asCommandResult(responseUri);
	}

	/* (non-Javadoc)
	 * @see ca.uqam.tool.vivoproxy.pattern.command.CommandResult#getSiteUrl()
	 */
	protected String getSiteUrl() {
		return getHostName()+"/"+getVivoSiteName();
	}
	/**
	 * @param person
	 * @return
	 * @throws IOException
	 */
	public CommandResult addPerson(Person person) throws IOException {
		Response response = VivoReceiverHelper.gotoAdminPage(getHostName()+"/"+getVivoSiteName(), getHttpClient());
		LOGGER.info(getHostName()+"/"+getVivoSiteName() + "with return code " + response.code());
		// Get an editKey, a must to have before adding data to VIVO
		editKey = VivoReceiverHelper.getEditKey(getHostName()+"/"+getVivoSiteName(), getHttpClient(), person.getPersonType());
		//
		// Adding to VIVO
		String label = (person.getFirstName() != null ? person.getFirstName() + "+" : "")
				+ (person.getMiddleName() != null ? person.getMiddleName() + "+" : "")
				+ (person.getLastName() != null ? person.getLastName() : "");
		String bodyValue = (person.getFirstName() != null ? "firstName=" + person.getFirstName() : "")
				+ (person.getMiddleName() != null ? "&middleName=" + person.getMiddleName() : "")
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

	public CommandResult setPositionOfPerson(PositionOfPerson body) throws IOException{
		/* 
		 * Goto individual page
		 */
		Response response = VivoReceiverHelper.gotoIndividualPage(getHostName()+"/"+getVivoSiteName(), getHttpClient(), body.getPersonIRI());
		/*
		 * Get the Edit Key for this operation
		 */
		EditKeyForPosition editKeyVar = new EditKeyForPosition();
		editKeyVar.setSubjectUri(body.getPersonIRI()); 
		editKeyVar.setPredicateUri(VIVO.relatedBy.getURI());
		editKeyVar.setDomainUri(FOAF.PERSON.stringValue());
		editKeyVar.setRangeUri(VIVO.Position.getURI());
		editKey = VivoReceiverHelper.getEditKey(getHostName()+"/"+getVivoSiteName(), getHttpClient(),editKeyVar);

		HttpUrl url = HttpUrl.parse(getSiteUrl() +"/edit/process").newBuilder()
				.addQueryParameter("orgType", body.getVivoOrganisationTypeIRI())
				.addQueryParameter("orgLabel", "")
				.addQueryParameter("orgLabelDisplay", body.getOrganisationLabel())
				.addQueryParameter("existingOrg", body.getOrganisationIRI())
				.addQueryParameter("positionTitle", body.getPositionTitleLabel())
				.addQueryParameter("positionType", body.getPositionTypeIRI())
				.addQueryParameter("startField-year", body.getStartFieldYear())
				.addQueryParameter("endField-year", body.getEndFieldYear())
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
		response = getHttpClient().newCall(request).execute();
		return CommandResult.asCommandResult(response);       

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
	 * @param username
	 * @param passwd
	 * @param concept
	 * @param MIME_Type
	 * @return
	 * @throws IOException
	 */
	public CommandResult addConcept(String username, String passwd, Concept concept, String MIME_Type) throws IOException{
		String iri = concept.getIRI();
		String updateConceptQuery = ""
				+ "INSERT DATA  { GRAPH <> { ";
		List<ConceptLabel> labels = concept.getLabels();
		for (Iterator iterator = labels.iterator(); iterator.hasNext();) {
			ConceptLabel conceptLabel = (ConceptLabel) iterator.next();
			String subject = "<" +iri +"> ";
			String predicate = "<http://www.w3.org/2000/01/rdf-schema#label> ";
			String object = "\"" +conceptLabel.getLabel() +"\"@"+conceptLabel.getLanguage() + " . ";
			updateConceptQuery+= subject;
			updateConceptQuery+= predicate;
			updateConceptQuery+= object;
		}		
		updateConceptQuery += "<" +iri +">  <http://www.w3.org/1999/02/22-rdf-syntax-ns#type>  <http://www.w3.org/2004/02/skos/core#Concept> . }}" ;

		String bodyValue = 
				"email="+username+
				"&password="+passwd+
				"&update="+updateConceptQuery;
		System.out.println(bodyValue);
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
	 * @param login
	 * @param passwd
	 * @param label
	 * @param MIME_Type
	 * @return
	 * @throws IOException
	 */
	public CommandResult DescribeByLabel(String login, String passwd, String label, String MIME_Type) throws IOException {
		String describeQuery = this.SparqlPrefix + " describe ?s \n"+
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
