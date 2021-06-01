# PersonApi

All URIs are relative to *http://localhost:9090/vivoproxy*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createPerson**](PersonApi.md#createPerson) | **POST** /person | Create a person in VIVO
[**getPersonByID**](PersonApi.md#getPersonByID) | **GET** /person/{id} | Get person by ID
[**getPersonByIRI**](PersonApi.md#getPersonByIRI) | **GET** /person/iri | Get person by VIVO IRI


<a name="createPerson"></a>
# **createPerson**
> createPerson(body)

Create a person in VIVO

This can only be done by the logged in person.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.PersonApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

PersonApi apiInstance = new PersonApi();
Person body = new Person(); // Person | Created person object
try {
    apiInstance.createPerson(body);
} catch (ApiException e) {
    System.err.println("Exception when calling PersonApi#createPerson");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Person**](Person.md)| Created person object |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/xml, application/json

<a name="getPersonByID"></a>
# **getPersonByID**
> Person getPersonByID(id)

Get person by ID



### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.PersonApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

PersonApi apiInstance = new PersonApi();
String id = "id_example"; // String | The name that needs to be fetched. Use person1 for testing. 
try {
    Person result = apiInstance.getPersonByID(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PersonApi#getPersonByID");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The name that needs to be fetched. Use person1 for testing.  |

### Return type

[**Person**](Person.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/xml, application/json

<a name="getPersonByIRI"></a>
# **getPersonByIRI**
> Person getPersonByIRI(iri)

Get person by VIVO IRI



### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.PersonApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

PersonApi apiInstance = new PersonApi();
String iri = "iri_example"; // String | The iri that needs to be fetched. Use person1 for testing. 
try {
    Person result = apiInstance.getPersonByIRI(iri);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PersonApi#getPersonByIRI");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **iri** | **String**| The iri that needs to be fetched. Use person1 for testing.  |

### Return type

[**Person**](Person.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/xml, application/json

