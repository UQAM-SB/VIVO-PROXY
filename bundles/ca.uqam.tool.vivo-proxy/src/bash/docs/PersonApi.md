# PersonApi

All URIs are relative to */vivoproxy*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createPerson**](PersonApi.md#createPerson) | **POST** /person | Create a person in VIVO
[**getPersonByID**](PersonApi.md#getPersonByID) | **GET** /person/{id} | Get person by ID
[**getPersonByIRI**](PersonApi.md#getPersonByIRI) | **GET** /person/iri | Get person by VIVO IRI


## **createPerson**

Create a person in VIVO

This can only be done by the logged in person.

### Example
```bash
 createPerson
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Person**](Person.md) | Created person object |

### Return type

(empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not Applicable
 - **Accept**: application/xml, application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

## **getPersonByID**

Get person by ID



### Example
```bash
 getPersonByID id=value
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **string** | The name that needs to be fetched. Use person1 for testing. |

### Return type

[**Person**](Person.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not Applicable
 - **Accept**: application/xml, application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

## **getPersonByIRI**

Get person by VIVO IRI



### Example
```bash
 getPersonByIRI  iri=value
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **iri** | **string** | The iri that needs to be fetched. Use person1 for testing. |

### Return type

[**Person**](Person.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not Applicable
 - **Accept**: application/xml, application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

