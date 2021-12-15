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
export SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"
source $SCRIPT_DIR/00-env.sh
curl -X 'GET' \
  'http://vivo-proxy.ca-central-1.elasticbeanstalk.com/vivo-proxy/indv/byIri?IRI=http%3A%2F%2Fvivoweb.org%2Fontology%2Fdegree%2FacademicDegree76' \
  -H 'accept: text/turtle'
