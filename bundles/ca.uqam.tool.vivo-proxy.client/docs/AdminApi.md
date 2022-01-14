# AdminApi

All URIs are relative to *http://localhost:9090/vivo-proxy*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getVivoProperties**](AdminApi.md#getVivoProperties) | **GET** /admin/getVivoProperties | Get remote VIVO properties
[**pingVivo**](AdminApi.md#pingVivo) | **GET** /admin/ping | Ping VIVO instance
[**reindexVIVO**](AdminApi.md#reindexVIVO) | **GET** /admin/reindex | Send reindex SOLR index signal to VIVO instance
[**setVivoProperties**](AdminApi.md#setVivoProperties) | **PUT** /admin/setVivoProperties | Set remote VIVO properties

<a name="getVivoProperties"></a>
# **getVivoProperties**
> VivoProperties getVivoProperties()

Get remote VIVO properties

### Example
```java
// Import classes:
//import ca.uqam.tool.vivoproxy.swagger.client.handler.ApiClient;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.ApiException;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.Configuration;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.auth.*;
//import ca.uqam.tool.vivoproxy.swagger.client.api.AdminApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AdminApi apiInstance = new AdminApi();
try {
    VivoProperties result = apiInstance.getVivoProperties();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AdminApi#getVivoProperties");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**VivoProperties**](VivoProperties.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="pingVivo"></a>
# **pingVivo**
> ModelApiResponse pingVivo()

Ping VIVO instance

### Example
```java
// Import classes:
//import ca.uqam.tool.vivoproxy.swagger.client.handler.ApiClient;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.ApiException;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.Configuration;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.auth.*;
//import ca.uqam.tool.vivoproxy.swagger.client.api.AdminApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AdminApi apiInstance = new AdminApi();
try {
    ModelApiResponse result = apiInstance.pingVivo();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AdminApi#pingVivo");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**ModelApiResponse**](ModelApiResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="reindexVIVO"></a>
# **reindexVIVO**
> ModelApiResponse reindexVIVO()

Send reindex SOLR index signal to VIVO instance

### Example
```java
// Import classes:
//import ca.uqam.tool.vivoproxy.swagger.client.handler.ApiClient;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.ApiException;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.Configuration;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.auth.*;
//import ca.uqam.tool.vivoproxy.swagger.client.api.AdminApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AdminApi apiInstance = new AdminApi();
try {
    ModelApiResponse result = apiInstance.reindexVIVO();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AdminApi#reindexVIVO");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**ModelApiResponse**](ModelApiResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="setVivoProperties"></a>
# **setVivoProperties**
> VivoProperties setVivoProperties(body)

Set remote VIVO properties

### Example
```java
// Import classes:
//import ca.uqam.tool.vivoproxy.swagger.client.handler.ApiClient;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.ApiException;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.Configuration;
//import ca.uqam.tool.vivoproxy.swagger.client.handler.auth.*;
//import ca.uqam.tool.vivoproxy.swagger.client.api.AdminApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AdminApi apiInstance = new AdminApi();
VivoProperties body = new VivoProperties(); // VivoProperties | VIVO properties
try {
    VivoProperties result = apiInstance.setVivoProperties(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AdminApi#setVivoProperties");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**VivoProperties**](VivoProperties.md)| VIVO properties |

### Return type

[**VivoProperties**](VivoProperties.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

