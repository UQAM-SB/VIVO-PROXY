package ca.uqam.tool.vivoproxy.pattern.command;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import ca.uqam.tool.vivoproxy.pattern.command.receiver.VivoReceiver;
import ca.uqam.tool.vivoproxy.pattern.command.util.VivoReceiverHelper;
import ca.uqam.vivo.vocabulary.VIVO;

public class TstVivoLorin {

	public static void main(String[] args) throws IOException {
//	    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//	    logging.setLevel(HttpLoggingInterceptor.Level.BODY);
		CookieManager cookieManager = new CookieManager();
		cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
		OkHttpClient httpClient = new OkHttpClient();
		httpClient.setCookieHandler(cookieManager);  
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = RequestBody.create(mediaType, "loginName=vivo@uqam.ca&loginPassword=Vivo1234.&loginForm=Login");
		Request request = new Request.Builder()
				.url("http://localhost:8080/vivo/authenticate")
				.method("POST", body)
				.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
				.addHeader("Accept-Language", "fr,fr-FR;q=0.8,en-US;q=0.5,en;q=0.3")
				.addHeader("Content-Type", "application/x-www-form-urlencoded")
				.addHeader("Origin", "http://localhost:8080")
				.addHeader("Connection", "keep-alive")
				.addHeader("Referer", "http://localhost:8080/vivo/login")
				.addHeader("Upgrade-Insecure-Requests", "1")
				.build();
		Response response = httpClient.newCall(request).execute();
		System.out.println("Request header-1 = \n" + request.headers());
		System.out.println("Response header-1 = \n" + response.headers());
		System.out.println("with return code: "+response.code() + " " +response.message());
		System.out.println("***********");

		//	
		
		request = new Request.Builder()
				  .url("http://localhost:8080/vivo/editRequestDispatch?typeOfNew=http%3A%2F%2Fxmlns.com%2Ffoaf%2F0.1%2FPerson&editForm=edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators.VIVONewIndividualFormGenerator")
				  .method("GET", null)
				  .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
				  .addHeader("Accept-Language", "fr,fr-FR;q=0.8,en-US;q=0.5,en;q=0.3")
				  .addHeader("Connection", "keep-alive")
				  .addHeader("Referer", "http://localhost:8080/vivo/siteAdmin")
				  .addHeader("Upgrade-Insecure-Requests", "1")
				  .build();
		response = httpClient.newCall(request).execute();
//		System.out.println("Response header = \n" + response.headers());
		String editKey = VivoReceiverHelper.getEditKey(VivoReceiver.getHostName()+"/"+VivoReceiver.getVivoSiteName(), httpClient, VIVO.GraduateStudent.getURI());
//

		System.out.println("Request header = \n" + request.headers());
		System.out.println("Response header = \n" + response.headers());
		System.out.println("with return code: "+response.code());
		System.out.println("edit key"+editKey);
	}

}
