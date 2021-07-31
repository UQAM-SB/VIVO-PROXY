# VIVO-PROXY Client side API Demo

This space contains a scenario (several use-cases)for using VIVO-PROXY in bash mode


## Prerequisites

- Make sure that VIVO is accessible from `http://localhost:8080/vivo`
- Make sure that VIVO-PROXY is accessible from `http://localhost:9090`
- have access to the CURL command
- executing commands from this working directory: `VIVO-PROXY/bundles/ca.uqam.tool.vivo-proxy.client/dataIngestDemo`


## Use-cases:

### Adding a person in VIVO

##### The data model of aPerson.json

```json
{
  "personType": "http://vivoweb.org/ontology/core#FacultyMember",
  "firstName": "Peters",
  "lastName": "Jasper",
  "middleName": "I"
}
```

##### Send de person data model of VIVO-PROXY

```bash
curl -X 'POST' 'http://localhost:9090/vivoproxy/person'   -H 'accept: application/json'   -H 'Content-Type: application/json' -d @aPerson.json 
```