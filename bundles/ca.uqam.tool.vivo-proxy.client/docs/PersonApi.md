# PersonApi

All URIs are relative to *http://vivo-proxy.ca-central-1.elasticbeanstalk.com/vivo-proxy*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createPerson**](PersonApi.md#createPerson) | **POST** /person | Create a person in VIVO
[**createPersonWithEmail**](PersonApi.md#createPersonWithEmail) | **POST** /person/createWithEmailHasKey | Create a user in VIVO using the eMail address as an IRI key
[**createUsersWithListInput**](PersonApi.md#createUsersWithListInput) | **POST** /person/createWithList | Creates list of users with given input array
[**personAddDocument**](PersonApi.md#personAddDocument) | **PUT** /person/addDocument | Associate a Document to a Person
[**personAddOrganisationalPositionTo**](PersonApi.md#personAddOrganisationalPositionTo) | **PUT** /person/addPositionFor | Add an organizational position for
[**personAddResearchAreaOf**](PersonApi.md#personAddResearchAreaOf) | **PUT** /person/addResearchAreaOf | Create a &#x27;Research Area of&#x27; a person
[**personHasAddResearchArea**](PersonApi.md#personHasAddResearchArea) | **PUT** /person/addHasResearchArea | Create &#x27;has Research Area&#x27; for a person

<a name="createPerson"></a>
# **createPerson**
> ModelAPIResponse createPerson(body)

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
    ModelAPIResponse result = apiInstance.createPerson(body);
    System.out.println(result);
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

[**ModelAPIResponse**](ModelAPIResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createPersonWithEmail"></a>
# **createPersonWithEmail**
> ModelAPIResponse createPersonWithEmail(body)

Create a user in VIVO using the eMail address as an IRI key

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
PersonWithOfficeInfo body = new PersonWithOfficeInfo(); // PersonWithOfficeInfo | Created person object with email
try {
    ModelAPIResponse result = apiInstance.createPersonWithEmail(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PersonApi#createPersonWithEmail");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**PersonWithOfficeInfo**](PersonWithOfficeInfo.md)| Created person object with email |

### Return type

[**ModelAPIResponse**](ModelAPIResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createUsersWithListInput"></a>
# **createUsersWithListInput**
> String createUsersWithListInput(body)

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
    String result = apiInstance.createUsersWithListInput(body);
    System.out.println(result);
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

**String**

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: text/plain, application/ld+json, text/n3, text/turtle, text/owl-manchester, text/owl-functional, application/rdf+xml, application/owl+xml

<a name="personAddDocument"></a>
# **personAddDocument**
> String personAddDocument(body)

Associate a Document to a Person

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
AuthorOfADocument body = new AuthorOfADocument(); // AuthorOfADocument | Author of document
try {
    String result = apiInstance.personAddDocument(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PersonApi#personAddDocument");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**AuthorOfADocument**](AuthorOfADocument.md)| Author of document |

### Return type

**String**

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: text/turtle, application/json

<a name="personAddOrganisationalPositionTo"></a>
# **personAddOrganisationalPositionTo**
> ModelAPIResponse personAddOrganisationalPositionTo(body)

Add an organizational position for

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
    ModelAPIResponse result = apiInstance.personAddOrganisationalPositionTo(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PersonApi#personAddOrganisationalPositionTo");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**PositionOfPerson**](PositionOfPerson.md)| Person that need to be in an organization |

### Return type

[**ModelAPIResponse**](ModelAPIResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="personAddResearchAreaOf"></a>
# **personAddResearchAreaOf**
> ModelAPIResponse personAddResearchAreaOf(body)

Create a &#x27;Research Area of&#x27; a person

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
ResourceToResource body = new ResourceToResource(); // ResourceToResource | Research Area of a person
try {
    ModelAPIResponse result = apiInstance.personAddResearchAreaOf(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PersonApi#personAddResearchAreaOf");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ResourceToResource**](ResourceToResource.md)| Research Area of a person |

### Return type

[**ModelAPIResponse**](ModelAPIResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="personHasAddResearchArea"></a>
# **personHasAddResearchArea**
> ModelAPIResponse personHasAddResearchArea(body)

Create &#x27;has Research Area&#x27; for a person

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
ResourceToResource body = new ResourceToResource(); // ResourceToResource | Research Area of a person
try {
    ModelAPIResponse result = apiInstance.personHasAddResearchArea(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PersonApi#personHasAddResearchArea");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ResourceToResource**](ResourceToResource.md)| Research Area of a person |

### Return type

[**ModelAPIResponse**](ModelAPIResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

