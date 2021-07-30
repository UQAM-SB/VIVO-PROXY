# OrganizationApi

All URIs are relative to *http://localhost:9090/vivoproxy*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createOrganization**](OrganizationApi.md#createOrganization) | **POST** /organization | Create an organization in VIVO

<a name="createOrganization"></a>
# **createOrganization**
> String createOrganization(body)

Create an organization in VIVO

This can only be done by the logged in VIVO.

### Example
```java
// Import classes:
//import ca.uqam.tool.vivoproxy.swagger.client.handler.ApiClient;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.ApiException;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.Configuration;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.auth.*;
//import ca.uqam.tool.vivoproxy.swagger.client.api.OrganizationApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

OrganizationApi apiInstance = new OrganizationApi();
Organization body = new Organization(); // Organization | Created organization object
try {
    String result = apiInstance.createOrganization(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OrganizationApi#createOrganization");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Organization**](Organization.md)| Created organization object |

### Return type

**String**

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: text/plain, application/ld+json, text/n3, text/turtle, text/owl-manchester, text/owl-functional, application/rdf+xml, application/owl+xml

