# Configurer les adresses de VIVO
## assigner les valeurs pour une configuration local de VIVO
```
curl -X 'PUT' \
  'http://localhost:9090/vivo-proxy/admin/setVivoProperties' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "vivo-URL": "http://localhost:8080/vivo-uqam",
  "sparql-query-URL": "http://localhost:8080/vivo-uqam/api/sparqlQuery",
  "sparql-update-URL": "http://localhost:8080/vivo-uqam/api/sparqlUpdate",
  "VIVO-admin-login": "vivo@uqam.ca",
  "VIVO-admin-password": "Vivo1234."
}'
```

## Obtenir les valeurs pour une configuration local de VIVO

```
curl -X 'GET' \
  'http://localhost:9090/vivo-proxy/admin/getVivoProperties' \
  -H 'accept: application/json' 
```

## Transférer une image d'un Bucket S3
  
```  
curl -X 'POST' \
  'http://localhost:9090/vivo-proxy/indv/addImage' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "individualIRI": "http://localhost:8080/vivo/individual/n2662",
  "imageURL": "https://photos-uqam.s3.ca-central-1.amazonaws.com/abbondanza.mona.jpg",
  "orig_X": 0,
  "orig_Y": 0,
  "width": 270,
  "height": 270
}'
```
