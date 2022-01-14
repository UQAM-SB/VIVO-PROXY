# DocumentApi

All URIs are relative to *http://localhost:9090/vivo-proxy*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createDocument**](DocumentApi.md#createDocument) | **POST** /document | Create a document in VIVO
[**documentAddAuthorFor**](DocumentApi.md#documentAddAuthorFor) | **PUT** /document/addAuthorFor | Associate a document to an Author

<a name="createDocument"></a>
# **createDocument**
> ModelApiResponse createDocument(body)

Create a document in VIVO

This can only be done by the logged in person.

### Example
```java
// Import classes:
//import ca.uqam.tool.vivoproxy.swagger.client.handler.ApiClient;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.ApiException;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.Configuration;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.auth.*;
//import ca.uqam.tool.vivoproxy.swagger.client.api.DocumentApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

DocumentApi apiInstance = new DocumentApi();
Document body = new Document(); // Document | Created a document
try {
    ModelApiResponse result = apiInstance.createDocument(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DocumentApi#createDocument");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Document**](Document.md)| Created a document |

### Return type

[**ModelApiResponse**](ModelApiResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="documentAddAuthorFor"></a>
# **documentAddAuthorFor**
> ModelApiResponse documentAddAuthorFor(body)

Associate a document to an Author

### Example
```java
// Import classes:
//import ca.uqam.tool.vivoproxy.swagger.client.handler.ApiClient;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.ApiException;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.Configuration;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.auth.*;
//import ca.uqam.tool.vivoproxy.swagger.client.api.DocumentApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

DocumentApi apiInstance = new DocumentApi();
AuthorOfADocument body = new AuthorOfADocument(); // AuthorOfADocument | Author that need to be add to a document
try {
    ModelApiResponse result = apiInstance.documentAddAuthorFor(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DocumentApi#documentAddAuthorFor");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**AuthorOfADocument**](AuthorOfADocument.md)| Author that need to be add to a document |

### Return type

[**ModelApiResponse**](ModelApiResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

