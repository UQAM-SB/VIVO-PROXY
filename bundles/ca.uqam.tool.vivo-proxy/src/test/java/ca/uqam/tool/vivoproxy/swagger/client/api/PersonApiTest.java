/**
 * 
 */
package ca.uqam.tool.vivoproxy.swagger.client.api;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Response;

import ca.uqam.tool.vivoproxy.swagger.client.handler.ApiCallback;
import ca.uqam.tool.vivoproxy.swagger.client.handler.ApiClient;
import ca.uqam.tool.vivoproxy.swagger.client.handler.ApiException;
import ca.uqam.tool.vivoproxy.swagger.client.handler.ApiResponse;
import ca.uqam.tool.vivoproxy.swagger.client.handler.Configuration;
import ca.uqam.tool.vivoproxy.swagger.client.handler.auth.*;
import ca.uqam.tool.vivoproxy.swagger.client.model.Person;
import ca.uqam.vivo.vocabulary.VIVO;
import ca.uqam.tool.vivoproxy.swagger.client.api.PersonApi;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
/**
 * @author heon
 * https://github.com/swagger-api/swagger-codegen/blob/master/samples/client/petstore/java/okhttp-gson/src/test/java/io/swagger/client/api/PetApiTest.java
 *
 */
public class PersonApiTest {

	private final String YOUR_USERNAME = "vivo@uqam.ca";
	private final String YOUR_PASSWORD = "Vivo1234.";

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	@Test
	public void testcreatePerson() throws ApiException, IOException, InterruptedException {
		ApiClient defaultClient = Configuration.getDefaultApiClient();

		// Configure HTTP basic authorization: basicAuth
		HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
		basicAuth.setUsername(YOUR_USERNAME);
		basicAuth.setPassword(YOUR_PASSWORD);
		final Map<String, Object> result = new HashMap<String, Object>();
		PersonApi apiInstance = new PersonApi();
		Person person = new Person(); // Person | Created person object
		person.setFirstName("firstName ");
		person.setMiddleName("middleName ");
		person.setLastName("lastName ");
		person.setPersonType(VIVO.FacultyMember.toString() );
		
		System.out.println(person);
		Call call = apiInstance.createPersonAsync(person, new ApiCallback<String>(){
				@Override
				public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
					System.out.println("onFailure "+ e.getMessage() + " "+ e.getCode());
				}

				@Override
				public void onSuccess(String person, int statusCode, Map<String, List<String>> responseHeaders) {
					result.put("person", person);
					System.out.println("onSuccess");
				}

				@Override
				public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {
					System.out.println("onUploadProgress " + bytesWritten + " "+ contentLength);
				}

				@Override
				public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {
					System.out.println("onDownloadProgress"+ bytesRead + " "+ contentLength);
				}
			});
		while (call.isExecuted()) {
			Thread.sleep(10000);
		}
//		try {
//			ApiResponse<Void> rep = apiInstance.createPersonWithHttpInfo(person);
//			Set<String> keys = rep.getHeaders().keySet();
//			for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
//				String key = (String) iterator.next();
//				List<String> values = rep.getHeaders().get(key);
//				for (Iterator iterator2 = values.iterator(); iterator2.hasNext();) {
//					String value = (String) iterator2.next();
//					System.out.println(key + ": "+ value);
//				}
//			}
//		} catch (ApiException e) {
//			System.err.println("Exception when calling PersonApi#createPerson");
//			e.printStackTrace();
//		} 
		System.out.println("Done !");
	}

	/**
	 * Test method for {@link ca.uqam.tool.vivoproxy.swagger.client.api.PersonApi#createUsersWithListInput(java.util.List)}.
	 * @throws ApiException 
	 */
	@Test
	public void testCreateUsersWithListInput() throws ApiException {
		ApiClient defaultClient = Configuration.getDefaultApiClient();

		// Configure HTTP basic authorization: basicAuth
		HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
		basicAuth.setUsername(YOUR_USERNAME);
		basicAuth.setPassword(YOUR_PASSWORD);

		List<Person> personsList = new ArrayList<>();
		for (int i = 0; i < 15; i++) {
			Person person = new Person();
			person.setFirstName("firstName "+i);
			person.setMiddleName("middleName "+i);
			person.setLastName("lastName "+i);
			person.setPersonType(VIVO.FacultyMember.toString());
			personsList.add(person);
		}

		PersonApi apiInstance = new PersonApi();
			apiInstance.createUsersWithListInput(personsList);
//		final Map<String, Object> result = new HashMap<String, Object>();
//		try {
//			apiInstance.createUsersWithListInputAsync(personsList, new ApiCallback<Void>(){
//				@Override
//				public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
//					result.put("error", e.getMessage());
//				}
//
//				@Override
//				public void onSuccess(Void pet, int statusCode, Map<String, List<String>> responseHeaders) {
//					result.put("pet", pet);
//				}
//
//				@Override
//				public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {
//					//empty
//				}
//
//				@Override
//				public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {
//					//empty
//				}
//			});
			System.out.println("done!");
//		} catch (ApiException e) {
//			System.err.println("Exception when calling PersonApi#createUsersWithListInput");
//			e.printStackTrace();
//		}	
	}

}
