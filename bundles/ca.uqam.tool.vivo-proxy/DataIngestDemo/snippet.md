# This file contains a set of commands to validate the Swagger API execution of VIVO-PROXY





curl -X 'POST' 'http://localhost:9090/vivoproxy/person' -H 'accept: text/turtle'  -H 'Content-Type: application/json' -d @michelPerson.json

n6353


curl -X 'POST' 'http://localhost:9090/vivoproxy/organization' -H 'accept: text/turtle'  -H 'Content-Type: application/json' -d @UnivQcMtl.json

n4394
//
// edit michelPositionUnivQcMtl.json to ajust IRI
// 
curl -X 'PUT'  'http://localhost:9090/vivoproxy/person' -H 'accept: text/turtle' -H 'Content-Type: application/json' -d @michelPositionUnivQcMtl.json


client.sh -d @michelPerson.json --host http://localhost:9090 --content-type json --accept 'text/turtle' createPerson
client.sh -d @UnivQcMtl.json --host http://localhost:9090 --content-type json --accept 'text/turtle' createOrganization
client.sh -d @michelPositionUnivQcMtl.json --host http://localhost:9090 --content-type json --accept 'text/turtle' createPositionFor


<http://localhost:8080/vivo/individual/n1571> 

curl --location --request POST 'http://localhost:8080/vivo/robot_authenticate' \
--header 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8' \
--header 'Accept-Language: fr,fr-FR;q=0.8,en-US;q=0.5,en;q=0.3' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Origin: http://localhost:8080' \
--header 'Connection: keep-alive' \
--header 'Referer: http://localhost:8080/vivo/' \
--header 'Upgrade-Insecure-Requests: 1' \
--data-urlencode 'loginName=vivo@uqam.ca' \
--data-urlencode 'loginPassword=Vivo1234.' \
--data-urlencode 'loginForm=Log+in'