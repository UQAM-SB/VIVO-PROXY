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

curl -X 'PUT' \
  'http://localhost:9090/vivo-proxy/admin/setVivoProperties' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{  "vivo-URL": "http://localhost:8080/vivo-uqam",
  "sparql-query-URL": "http://localhost:8080/vivo-uqam/api/sparqlQuery",
  "sparql-update-URL": "http://localhost:8080/vivo-uqam/api/sparqlUpdate",
  "sparql-endPoint-type": "vivo",
  "VIVO-admin-login": "vivo@uqam.ca",
  "VIVO-admin-password": "Vivo1234."
}'

