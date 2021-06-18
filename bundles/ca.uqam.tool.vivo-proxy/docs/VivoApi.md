# VivoApi

All URIs are relative to *http://localhost:9090/vivoproxy*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getindividualByIRI**](VivoApi.md#getindividualByIRI) | **GET** /vivo | Get an individual by IRI


<a name="getindividualByIRI"></a>
# **getindividualByIRI**
> Vivo getindividualByIRI(IRI)

Get an individual by IRI



### Example
```java
// Import classes:
//import ca.uqam.tool.vivoproxy.swagger.client.handler.ApiClient;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.ApiException;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.Configuration;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.auth.*;
//import ca.uqam.tool.vivoproxy.swagger.client.api.VivoApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

VivoApi apiInstance = new VivoApi();
String IRI = "IRI_example"; // String | Used to obtain information from a VIVO individual by the IRI
try {
    Vivo result = apiInstance.getindividualByIRI(IRI);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VivoApi#getindividualByIRI");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **IRI** | **String**| Used to obtain information from a VIVO individual by the IRI |

### Return type

[**Vivo**](Vivo.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, text/plain, application/rdf+xml, text/n3, text/turtle, text/funtional, text/manchester, application/owl+xml

