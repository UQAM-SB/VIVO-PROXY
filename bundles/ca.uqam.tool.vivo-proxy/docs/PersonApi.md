# PersonApi

All URIs are relative to *http://localhost:9090/vivoproxy*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createPerson**](PersonApi.md#createPerson) | **POST** /person | Create a person in VIVO
[**createPositionFor**](PersonApi.md#createPositionFor) | **PUT** /person | Create organizational position for
[**createUsersWithListInput**](PersonApi.md#createUsersWithListInput) | **POST** /person/createWithList | Creates list of users with given input array


<a name="createPerson"></a>
# **createPerson**
> createPerson(body)

Create a person in VIVO

This can only be done by the logged in person.

### Example
```java
// Import classes:
//import ca.uqam.tool.vivoproxy.swagger.client.handler.ApiClient;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.ApiException;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.Configuration;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.auth.*;
//import ca.uqam.tool.vivoproxy.swagger.client.api.PersonApi;

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
 - **Accept**: application/json, text/plain, application/rdf+xml, text/n3, text/turtle, text/funtional, text/manchester, application/owl+xml

<a name="createPositionFor"></a>
# **createPositionFor**
> createPositionFor(body)

Create organizational position for



### Example
```java
// Import classes:
//import ca.uqam.tool.vivoproxy.swagger.client.handler.ApiClient;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.ApiException;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.Configuration;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.auth.*;
//import ca.uqam.tool.vivoproxy.swagger.client.api.PersonApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

PersonApi apiInstance = new PersonApi();
PositionOfPerson body = new PositionOfPerson(); // PositionOfPerson | Person that need to be in an organization
try {
    apiInstance.createPositionFor(body);
} catch (ApiException e) {
    System.err.println("Exception when calling PersonApi#createPositionFor");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**PositionOfPerson**](PositionOfPerson.md)| Person that need to be in an organization |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json, application/xml
 - **Accept**: application/json, text/plain, application/rdf+xml, text/n3, text/turtle, text/funtional, text/manchester, application/owl+xml

<a name="createUsersWithListInput"></a>
# **createUsersWithListInput**
> createUsersWithListInput(body)

Creates list of users with given input array



### Example
```java
// Import classes:
//import ca.uqam.tool.vivoproxy.swagger.client.handler.ApiClient;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.ApiException;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.Configuration;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.auth.*;
//import ca.uqam.tool.vivoproxy.swagger.client.api.PersonApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

PersonApi apiInstance = new PersonApi();
List<Person> body = Arrays.asList(new Person()); // List<Person> | List of user object
try {
    apiInstance.createUsersWithListInput(body);
} catch (ApiException e) {
    System.err.println("Exception when calling PersonApi#createUsersWithListInput");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**List&lt;Person&gt;**](Person.md)| List of user object |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, text/plain, application/rdf+xml, text/n3, text/turtle, text/funtional, text/manchester, application/owl+xml

