#!/bin/bash 

###################################################################
# Script Name   :
# Description   :
# Args          : 
# Author       	: Michel Héon PhD
# Institution   : Université du Québec à Montréal
# Copyright     : Université du Québec à Montréal (c) 2022
# Email         : heon.michel@uqam.ca
###################################################################
export SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"
source $SCRIPT_DIR/00-env.sh

#
# Create Persone
#
curl -X 'POST' \
  'http://localhost:9090/vivo-proxy/person/createWithEmailHasKey' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "personType": "http://vivoweb.org/ontology/core#Librarian",
  "id": "jasper.peter",
  "displayName": [
    {
      "label": "Peter Jasper",
      "language": "fr-CA"
    },
    {
      "label": "Peter Jasper",
      "language": "en-US"
    }
  ],
  "firstName": [
    {
      "label": "Peter",
      "language": "fr-CA"
    },
    {
      "label": "Peter",
      "language": "en-US"
    }
  ],
  "lastName": [
    {
      "label": "Jasper",
      "language": "fr-CA"
    },
    {
      "label": "Jasper",
      "language": "en-US"
    }
  ],
  "title": [
    {
      "label": "Professeur",
      "language": "fr-CA"
    },
    {
      "label": "Professor",
      "language": "en-US"
    }
  ],
  "secondaryTitle": [
    {
      "label": "semantic web",
      "language": "fr-CA"
    }
  ],
  "eMail": "jasper.peter@example.org",
  "secondaryMails": [
    "jaspers2.peter@example.org",
    "jaspers3.peter@example.org"
  ],
  "telephone": "+1 (514) 987-3000 ext. 0000",
  "organization-unit-id": "6010",
  "address": [
    {
      "officeNumber": "Bur. 201",
      "streetAddress": "Faculté des sciences; Case postale 8888, succ. Centre-ville",
      "locality": "Montréal",
      "region": "Québec",
      "country": "Canada",
      "postalCode": "H3C-3P8"
    }
  ]
}'

curl -X 'POST' \
  'http://localhost:9090/vivo-proxy/indv/addImage' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "individualIRI": "http://purl.org/vivo.uqam.ca/data/people#jasper_peter_example_org",
  "imageURL": "https://photos-uqam.s3.ca-central-1.amazonaws.com/abbondanza_mona_uqam_ca.jpg",
  "orig_X": 0,
  "orig_Y": 0,
  "width": 270,
  "height": 270
}'



