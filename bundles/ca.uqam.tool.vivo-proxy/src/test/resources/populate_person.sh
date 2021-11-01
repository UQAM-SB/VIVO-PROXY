#!/bin/bash 

###################################################################
# Script Name   :
# Description   :
# Args          : 
# Author       	: Michel Héon PhD
# Institution   : Université du Québec à Montréal
# Copyright     : Université du Québec à Montréal (c) 2021
# Email         : heon.michel@uqam.ca
###################################################################

curl -X 'POST' \
  'http://localhost:9090/vivoproxy/person' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "personType": "http://vivoweb.org/ontology/core#FacultyMember",
  "firstName": "Peters",
  "lastName": "Jasper",
  "middleName": "I"
}'

curl -X 'POST' \
  'http://localhost:9090/vivoproxy/person' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "personType": "http://vivoweb.org/ontology/core#FacultyMember",
  "firstName": "Emily",
  "lastName": "Stevens"
}'

curl -X 'POST' \
  'http://localhost:9090/vivoproxy/document' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "docTypeIRI": "http://vivoweb.org/ontology/core#ConferencePaper",
  "title": "Les écrans, des machines de vision"
}'

http://localhost:8080/vivo/individual/n6089
http://localhost:8080/vivo/individual/n3427
http://localhost:8080/vivo/individual/n2421
curl -X 'PUT' \
  'http://localhost:9090/vivoproxy/person/addDocument' \
  -H 'accept: text/turtle' \
  -H 'Content-Type: application/json' \
  -d '{
  "documentIRI": "http://localhost:8080/vivo/individual/n2421",
  "personIRI": "http://localhost:8080/vivo/individual/n6089"
}'


