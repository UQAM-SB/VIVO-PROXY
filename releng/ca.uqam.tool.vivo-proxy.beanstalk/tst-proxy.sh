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
  'http://vivo-proxy.ca-central-1.elasticbeanstalk.com/vivo-proxy/organization' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "UQAM",
  "organizationType": "http://vivoweb.org/ontology/core#University",
  "names": [
    {
      "label": "Université du Québec à Montréal",
      "language": "fr-CA"
    },
    {
      "label": "University Of Québec in Montreal",
      "language": "en-US"
    }
  ],
  "overview": [
    {
      "label": "L’Université du Québec à Montréal (UQAM) est une université publique de langue française dont le rayonnement est international. L’originalité et les caractéristiques propres de ses programmes, sa recherche de pointe souvent axée sur les préoccupations sociales ainsi que ses innovations en création ont contribué à bâtir sa renommée.\nL’université offre de la formation sur le campus montréalais et dans ses campus régionaux situés dans la grande région métropolitaine de Montréal.",
      "language": "fr-CA"
    },
    {
      "label": "The Université du Québec à Montréal (UQAM) is a French-language public university with an international reputation. The originality and specific characteristics of its programs, its cutting-edge research often focused on social concerns, and its creative innovations have helped build its reputation.\nThe university offers training on the Montreal campus and on its regional campuses located in the greater Montreal area.",
      "language": "en-US"
    },
    {
      "label": "The Université du Québec à Montréal (UQAM) is a French-language public university with an international reputation. The originality and specific characteristics of its programs, its cutting-edge research often focused on social concerns, and its creative innovations have helped build its reputation.\nThe university offers training on the Montreal campus and on its regional campuses located in the greater Montreal area.",
      "language": "en-CA"
    }
  ]
}'