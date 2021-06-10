package ca.uqam.tool.vivoproxy.pattern.command;

import java.io.File;
import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
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

import ca.uqam.tool.vivoproxy.pattern.command.util.VivoReceiverHelper;

/**
 * @author heon
 *
 */
/**
 * @author heon
 *
 */
/**
 * @author heon
 *
 */
/**
 * @author heon
 *
 */
public class VivoReceiver extends CommandResult {
    public static final String FOAF_PERSON = "http://xmlns.com/foaf/0.1/Person";
    public static final String OBO_RO_0000053 = "http://purl.obolibrary.org/obo/RO_0000053";
    public static final String NEW_INDIVIDUAL_FORM_GENERATOR = "edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators.VIVONewIndividualFormGenerator";
    public static final String VIVO_GRADUATE_STUDENT = "http://vivoweb.org/ontology/core#GraduateStudent";
    public static final String VIVO_COLLEGE = "http://vivoweb.org/ontology/core#College";
    public static final String VIVO_MEMBER_ROLE = "http://vivoweb.org/ontology/core#MemberRole";

    private final static Logger LOGGER = Logger.getLogger(VivoReceiver.class.getName());
    private OkHttpClient httpClient;
    private Response response;
    private CookieManager cookieManager;
    private String hostName="http://192.168.7.23:8080";
    private String vivoSiteName="vivo";
    private String editKey;
    /**
     * @param username
     * @param password
     * @return
     * @throws IOException
     */
    public CommandResult login(String username, String password) throws IOException {
        httpClient = new OkHttpClient();
        cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        getHttpClient().setCookieHandler(cookieManager);     
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "loginName="+username+"&loginPassword="+password+"&loginForm=Connexion");
        Request request = new Request.Builder()
                .url(getHostName()+"/"+getVivoSiteName() + "/authenticate")
                .method("POST", body)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:88.0) Gecko/20100101 Firefox/88.0")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .addHeader("Accept-Language", "fr,fr-FR;q=0.8,en-US;q=0.5,en;q=0.3")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Origin", getHostName())
                .addHeader("Connection", "keep-alive")
                .addHeader("Referer", getHostName()+"/"+getVivoSiteName()+"/")
                .addHeader("Upgrade-Insecure-Requests", "1")
                .build();
        response = getHttpClient().newCall(request).execute();
        LOGGER.info("Login for " + username +" with return code: "+response.code());
        return CommandResult.asCommandResult(this);
    }
    public CommandResult addOrganization(String organisationName, String vivoOrganisationType) throws IOException {
        response = VivoReceiverHelper.gotoAdminPage(getHostName()+"/"+getVivoSiteName(), getHttpClient());
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
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:88.0) Gecko/20100101 Firefox/88.0")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .addHeader("Accept-Language", "fr,fr-FR;q=0.8,en-US;q=0.5,en;q=0.3")
                .addHeader("Connection", "keep-alive")
                .addHeader("Referer", referedUrl.toString())
                //                .addHeader("Referer", "http://192.168.7.23:8080/vivo/editRequestDispatch?typeOfNew=http%3A%2F%2Fvivoweb.org%2Fontology%2Fcore%23College&editForm=edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators.VIVONewIndividualFormGenerator")
                .addHeader("Upgrade-Insecure-Requests", "1")
                .build();
        LOGGER.info("Sending "+ organisationName );
        response = getHttpClient().newCall(request).execute();
        String responseUri = VivoReceiverHelper.getUriResponse(response.body().string());
        LOGGER.info("Adding "+ organisationName + "at uri "+ responseUri+" with return code " + response.code());
        return CommandResult.asCommandResult(responseUri);
    }

    private String getSiteUrl() {
        return getHostName()+"/"+getVivoSiteName();
    }
    public CommandResult addPerson(String firstName, String middleName, String lastName, String vivoPersonType) throws IOException {
        response = VivoReceiverHelper.gotoAdminPage(getHostName()+"/"+getVivoSiteName(), getHttpClient());
        // Get an editKey, a must to have before adding data to VIVO
        editKey = VivoReceiverHelper.getEditKey(getHostName()+"/"+getVivoSiteName(), getHttpClient(), vivoPersonType);
        //
        // Adding to VIVO

        String label = firstName+"+"+middleName+"+"+lastName;
        String bodyValue = "firstName="+firstName+"&middleName="+middleName+"&lastName="+lastName+"&label="+label+"&editKey="+editKey;
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
                        "/editRequestDispatch?typeOfNew="+vivoPersonType+
                        "&editForm=edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators.VIVONewIndividualFormGenerator")
                .addHeader("Upgrade-Insecure-Requests", "1")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        LOGGER.info("Sending "+ label );
        Response response = getHttpClient().newCall(request).execute();
        String responseUri = VivoReceiverHelper.getUriResponse(response.body().string());
        LOGGER.info("Adding "+ label + "at uri "+ responseUri+" with return code " + response.code());
        return CommandResult.asCommandResult(responseUri);
    }    
    public CommandResult addMemberOf(String personUri, String organizationUri, String organizationLabel, String roleLabel, String startField_year,
            String endField_year, String vivoOrganisationType) throws IOException {
        VivoReceiverHelper.gotoPeoplePage(getHostName()+"/"+getVivoSiteName(), getHttpClient(), personUri);
        
        response = VivoReceiverHelper.gotoAdminPage(getHostName()+"/"+getVivoSiteName(), getHttpClient());
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
                .addQueryParameter("domainUri", FOAF_PERSON)
                .addQueryParameter("rangeUri", VIVO_MEMBER_ROLE)
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

    public OkHttpClient getHttpClient() {
        return httpClient;
    }
    public Response getResponse() {
        return response;
    }
    public String getHostName() {
        return hostName;
    }
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
    public String getVivoSiteName() {
        return vivoSiteName;
    }
    public void setVivoSiteName(String vivoSiteName) {
        this.vivoSiteName = vivoSiteName;
    }
}
