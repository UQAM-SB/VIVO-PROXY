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

    public static String getEditKey(String siteUrl, OkHttpClient httpClient, String vivoPersonType) throws IOException {
        HttpUrl url = HttpUrl.parse(siteUrl +"/editRequestDispatch").newBuilder()
                .addQueryParameter("typeOfNew", vivoPersonType)
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
