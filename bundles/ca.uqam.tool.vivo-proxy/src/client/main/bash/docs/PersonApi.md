# PersonApi

All URIs are relative to */vivoproxy*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createPerson**](PersonApi.md#createPerson) | **POST** /person | Create a person in VIVO
[**createPositionFor**](PersonApi.md#createPositionFor) | **PUT** /person | Create organizational position for
[**createUsersWithListInput**](PersonApi.md#createUsersWithListInput) | **POST** /person/createWithList | Creates list of users with given input array


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
 - **Accept**: application/json, text/plain, application/rdf+xml, text/n3, text/turtle, text/funtional, text/manchester, application/owl+xml

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

## **createPositionFor**

Create organizational position for



### Example
```bash
 createPositionFor
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**PositionOfPerson**](PositionOfPerson.md) | Person that need to be in an organization |

### Return type

(empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json, application/xml
 - **Accept**: application/json, text/plain, application/rdf+xml, text/n3, text/turtle, text/funtional, text/manchester, application/owl+xml

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

## **createUsersWithListInput**

Creates list of users with given input array



### Example
```bash
 createUsersWithListInput
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**array[Person]**](Person.md) | List of user object |

### Return type

(empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not Applicable
 - **Accept**: application/json, text/plain, application/rdf+xml, text/n3, text/turtle, text/funtional, text/manchester, application/owl+xml

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

