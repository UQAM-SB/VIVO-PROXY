# IndividualApi

All URIs are relative to *http://vivo-proxy.ca-central-1.elasticbeanstalk.com/vivo-proxy*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getIndvByIRI**](IndividualApi.md#getIndvByIRI) | **GET** /indv/byIri | Get an individual by IRI
[**getIndvByLabel**](IndividualApi.md#getIndvByLabel) | **GET** /indv/byLabel | Get an individual IRI by label (rdfs:label)
[**indvAddImage**](IndividualApi.md#indvAddImage) | **PUT** /indv/addImage | Associate an image to an individual
[**indvAddLabel**](IndividualApi.md#indvAddLabel) | **PUT** /indv/addLabel | Add a label to an individual
[**indvAddStatement**](IndividualApi.md#indvAddStatement) | **PUT** /indv/addStatement | Adding statement
[**indvAddType**](IndividualApi.md#indvAddType) | **PUT** /indv/addType | Associate a type to an individual

<a name="getIndvByIRI"></a>
# **getIndvByIRI**
> String getIndvByIRI(IRI)

Get an individual by IRI

### Example
```java
// Import classes:
//import ca.uqam.tool.vivoproxy.swagger.client.handler.ApiClient;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.ApiException;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.Configuration;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.auth.*;
//import ca.uqam.tool.vivoproxy.swagger.client.api.IndividualApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

IndividualApi apiInstance = new IndividualApi();
String IRI = "IRI_example"; // String | Used to obtain information from a VIVO individual by the IRI
try {
    String result = apiInstance.getIndvByIRI(IRI);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IndividualApi#getIndvByIRI");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **IRI** | **String**| Used to obtain information from a VIVO individual by the IRI |

### Return type

**String**

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: text/turtle, text/plain, application/ld+json, text/n3, text/owl-manchester, text/owl-functional, application/rdf+xml, application/owl+xml, application/json

<a name="getIndvByLabel"></a>
# **getIndvByLabel**
> String getIndvByLabel(label)

Get an individual IRI by label (rdfs:label)

### Example
```java
// Import classes:
//import ca.uqam.tool.vivoproxy.swagger.client.handler.ApiClient;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.ApiException;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.Configuration;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.auth.*;
//import ca.uqam.tool.vivoproxy.swagger.client.api.IndividualApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

IndividualApi apiInstance = new IndividualApi();
String label = "label_example"; // String | Strict string used to obtain information from a VIVO individual
try {
    String result = apiInstance.getIndvByLabel(label);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IndividualApi#getIndvByLabel");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **label** | **String**| Strict string used to obtain information from a VIVO individual |

### Return type

**String**

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: text/turtle, text/plain, application/ld+json, text/n3, text/owl-manchester, text/owl-functional, application/rdf+xml, application/owl+xml, application/json

<a name="indvAddImage"></a>
# **indvAddImage**
> ModelAPIResponse indvAddImage(body)

Associate an image to an individual

### Example
```java
// Import classes:
//import ca.uqam.tool.vivoproxy.swagger.client.handler.ApiClient;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.ApiException;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.Configuration;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.auth.*;
//import ca.uqam.tool.vivoproxy.swagger.client.api.IndividualApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

IndividualApi apiInstance = new IndividualApi();
Image body = new Image(); // Image | image associated to an individual
try {
    ModelAPIResponse result = apiInstance.indvAddImage(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IndividualApi#indvAddImage");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Image**](Image.md)| image associated to an individual |

### Return type

[**ModelAPIResponse**](ModelAPIResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="indvAddLabel"></a>
# **indvAddLabel**
> ModelAPIResponse indvAddLabel(IRI, body)

Add a label to an individual

### Example
```java
// Import classes:
//import ca.uqam.tool.vivoproxy.swagger.client.handler.ApiClient;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.ApiException;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.Configuration;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.auth.*;
//import ca.uqam.tool.vivoproxy.swagger.client.api.IndividualApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

IndividualApi apiInstance = new IndividualApi();
String IRI = "IRI_example"; // String | The IRI of the individual from which a label is added
List<LinguisticLabel> body = Arrays.asList(new LinguisticLabel()); // List<LinguisticLabel> | Linguistic labels to add
try {
    ModelAPIResponse result = apiInstance.indvAddLabel(IRI, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IndividualApi#indvAddLabel");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **IRI** | **String**| The IRI of the individual from which a label is added |
 **body** | [**List&lt;LinguisticLabel&gt;**](LinguisticLabel.md)| Linguistic labels to add | [optional]

### Return type

[**ModelAPIResponse**](ModelAPIResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="indvAddStatement"></a>
# **indvAddStatement**
> ModelAPIResponse indvAddStatement(body)

Adding statement

### Example
```java
// Import classes:
//import ca.uqam.tool.vivoproxy.swagger.client.handler.ApiClient;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.ApiException;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.Configuration;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.auth.*;
//import ca.uqam.tool.vivoproxy.swagger.client.api.IndividualApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

IndividualApi apiInstance = new IndividualApi();
Statement body = new Statement(); // Statement | Adding a triple to the graph
try {
    ModelAPIResponse result = apiInstance.indvAddStatement(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IndividualApi#indvAddStatement");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Statement**](Statement.md)| Adding a triple to the graph |

### Return type

[**ModelAPIResponse**](ModelAPIResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="indvAddType"></a>
# **indvAddType**
> ModelAPIResponse indvAddType(body)

Associate a type to an individual

### Example
```java
// Import classes:
//import ca.uqam.tool.vivoproxy.swagger.client.handler.ApiClient;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.ApiException;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.Configuration;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.auth.*;
//import ca.uqam.tool.vivoproxy.swagger.client.api.IndividualApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

IndividualApi apiInstance = new IndividualApi();
IndividualType body = new IndividualType(); // IndividualType | Adding type associated to an existing individual
try {
    ModelAPIResponse result = apiInstance.indvAddType(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IndividualApi#indvAddType");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**IndividualType**](IndividualType.md)| Adding type associated to an existing individual |

### Return type

[**ModelAPIResponse**](ModelAPIResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

