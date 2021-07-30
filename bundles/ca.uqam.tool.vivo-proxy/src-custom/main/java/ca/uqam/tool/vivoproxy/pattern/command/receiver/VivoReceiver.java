package ca.uqam.tool.vivoproxy.pattern.command.receiver;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.rdf4j.model.vocabulary.FOAF;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import ca.uqam.tool.vivoproxy.pattern.command.AbstractReceiver;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.pattern.command.receiver.util.EditKeyForPosition;
import ca.uqam.tool.vivoproxy.pattern.command.util.VivoReceiverHelper;
import ca.uqam.tool.vivoproxy.swagger.model.Concept;
import ca.uqam.tool.vivoproxy.swagger.model.ConceptLabel;
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
	public static final String FOAF_PERSON = "http://xmlns.com/foaf/0.1/Person";
	public static final String OBO_RO_0000053 = "http://purl.obolibrary.org/obo/RO_0000053";
	public static final String NEW_INDIVIDUAL_FORM_GENERATOR = "edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators.VIVONewIndividualFormGenerator";

	private OkHttpClient httpClient;
	protected CookieManager cookieManager;
	protected String hostName="http://192.168.7.23:8080";
	protected String vivoSiteName="vivo";
	protected String editKey;


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
	}
	public OkHttpClient getHttpClient() {
		return httpClient;
	}

	public CommandResult login(String username, String password) throws IOException {
		password = password +"";
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = RequestBody.create(mediaType, "loginName="+username+"&loginPassword="+password+"&loginForm=Connexion");
		String url = getHostName()+"/"+getVivoSiteName() + "/robot_authenticate";
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

	protected String getSiteUrl() {
		return getHostName()+"/"+getVivoSiteName();
	}
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
		/*
		 * position edition process
		 *               
		 *    example entry for url part
              http://localhost:8080/vivo/edit/process?
              orgType=http://vivoweb.org/ontology/core#University&
              orgLabel=Harvard+University&orgLabelDisplay=&
              existingOrg=>SUBMITTED+VALUE+WAS+BLANK<&
              positionTitle=Professor&
              positionType=http://vivoweb.org/ontology/core#FacultyPosition&
              startField-year=2017&
              endField-year=2020&
              editKey=55076138&
              submit-Create=Create+Entry
		 *
            example entry for Refere Part
             http://localhost:8080/vivo/editRequestDispatch
             subjectUri=http://localhost:8080/vivo/individual/n128
             predicateUri=http://vivoweb.org/ontology/core#relatedBy
             domainUri=http://xmlns.com/foaf/0.1/Person
             rangeUri=http://vivoweb.org/ontology/core#Position"

		 */
		HttpUrl url = HttpUrl.parse(getSiteUrl() +"/edit/process").newBuilder()
				.addQueryParameter("orgType", body.getVivoOrganisationTypeIRI())
				.addQueryParameter("orgLabel", body.getOrganisationLabel())
				.addQueryParameter("orgLabelDisplay", "")
				.addQueryParameter("existingOrg", ">SUBMITTED VALUE WAS BLANK<")
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

	public CommandResult addMemberOf(String personUri, String organizationUri, String organizationLabel, String roleLabel, String startField_year,
			String endField_year, String vivoOrganisationType) throws IOException {
		VivoReceiverHelper.gotoIndividualPage(getHostName()+"/"+getVivoSiteName(), getHttpClient(), personUri);

		Response response = VivoReceiverHelper.gotoAdminPage(getHostName()+"/"+getVivoSiteName(), getHttpClient());
		// Get an editKey, a must to have before adding data to VIVO
		/* Example
		 * subjectUri=http://localhost:8080/vivo/individual/n4851
		 * predicateUri=http://purl.obolibrary.org/obo/RO_0000053
		 * domainUri=http://xmlns.com/foaf/0.1/Person
		 * rangeUri=http://vivoweb.org/ontology/core#MemberRole
		 * */
		editKey = VivoReceiverHelper.getEditKey(getHostName()+"/"+getVivoSiteName(), getHttpClient(), personUri, OBO_RO_0000053, FOAF_PERSON, vivoOrganisationType);
		/*
			"http://192.168.7.23:8080/vivo/edit/process?
			roleActivityType=http://vivoweb.org/ontology/core#College&
			activityLabel=
			&activityLabelDisplay=Collège de Hull
			&roleToActivityPredicate=
			&existingRoleActivity=http://localhost:8080/vivo/individual/n868
			&roleLabel=Professeur
			&startField-year=2001
			&endField-year=2021
			&editKey=50439720
		 */
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
		/*
		 * http://192.168.7.23:8080/vivo/editRequestDispatch?
		 * subjectUri=http://localhost:8080/vivo/individual/n4851
		 * predicateUri=http://purl.obolibrary.org/obo/RO_0000053s
		 * domainUri=http://xmlns.com/foaf/0.1/Person
		 * rangeUri=http://vivoweb.org/ontology/core#MemberRole
		 */
		HttpUrl refererUrl = HttpUrl.parse(getHostName()+"/"+getVivoSiteName() +"/editRequestDispatch").newBuilder()
				.addQueryParameter("subjectUri", personUri)
				.addQueryParameter("predicateUri", OBO_RO_0000053)
				.addQueryParameter("domainUri", FOAF.PERSON.stringValue())
				.addQueryParameter("rangeUri", VIVO.MemberRole.getURI())
				.build();

		Request request = new Request.Builder()
				/*
                .url("http://192.168.7.23:8080/vivo/edit/process?roleActivityType=http%3A%2F%2Fvivoweb.org%2Fontology%2Fcore%23College&activityLabel=&activityLabelDisplay=Coll%C3%A8ge+de+Hull&roleToActivityPredicate=&existingRoleActivity=http%3A%2F%2Flocalhost%3A8080%2Fvivo%2Findividual%2Fn868&roleLabel=Professeur&startField-year=2001&endField-year=2021&editKey=50439720")
				 *
				 */
				.url(url)
				.method("GET", null)
				.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:88.0) Gecko/20100101 Firefox/88.0")
				.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
				.addHeader("Accept-Language", "fr,fr-FR;q=0.8,en-US;q=0.5,en;q=0.3")
				.addHeader("Connection", "keep-alive")
				//                .addHeader("Referer", "http://192.168.7.23:8080/vivo/editRequestDispatch?subjectUri=http%3A%2F%2Flocalhost%3A8080%2Fvivo%2Findividual%2Fn4851&predicateUri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FRO_0000053&domainUri=http%3A%2F%2Fxmlns.com%2Ffoaf%2F0.1%2FPerson&rangeUri=http%3A%2F%2Fvivoweb.org%2Fontology%2Fcore%23MemberRole")
				.addHeader("Referer", refererUrl.toString())
				.addHeader("Upgrade-Insecure-Requests", "1")
				.build();

		// TODO Auto-generated method stub
		return null;
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
		MIME_Type = MIME_Type;
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

	public static void main (String[] argv) throws IOException
	{
		
		VivoReceiver vr = new VivoReceiver();
		ResourceToResource rToLink = new ResourceToResource();
		rToLink.setObjectIRI("http://purl.org/uqam.ca/vocabulary/expertise#semanticwebee");
		rToLink.setSubjectIRI("http://localhost:8080/vivo/individual/n2935");

		CommandResult resu = vr.addResearchAreaOfafPerson("vivo@uqam.ca", "Vivo1234.", rToLink, SemanticWebMediaType.TEXT_TURTLE.toString());
		System.err.println(resu.getOkhttpResult().body().string());
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
	public CommandResult addPerson(List<Person> personsList) throws IOException {
		List<String> personsUriList = new ArrayList<>();

		for (Iterator iterator = personsList.iterator(); iterator.hasNext();) {
			Person person = (Person) iterator.next();
			CommandResult commandResult = addPerson(person);
			personsUriList.add(VivoReceiverHelper.getUriResponse(commandResult.getOkhttpResult()));
		}
		return CommandResult.asCommandResult(personsUriList);
	}
}
