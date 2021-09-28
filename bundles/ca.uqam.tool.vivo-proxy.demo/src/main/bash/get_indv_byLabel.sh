#!/bin/bash

###################################################################
# Script Name   : get_indv_byLabel.sh
# Description   : Get IRI individual by sending a label
# Args          : "a label to find" 
# Author       	: Michel Héon PhD
# Institution   : Université du Québec à Montréal
# Copyright     : Université du Québec à Montréal (c) 2021
# Email         : heon.michel@uqam.ca
###################################################################
INDV_ONTO=$(mktemp --suffix=.nt)

curl -s -G "http://localhost:9090/vivoproxy/indv/byLabel" --data-urlencode "Label=$1" -H 'accept: text/plain'  > $INDV_ONTO 

sparql --data=$INDV_ONTO -results=CSV "\
    prefix rdfs:  <http://www.w3.org/2000/01/rdf-schema#> \
    prefix obo:   <http://purl.obolibrary.org/obo/> \
    prefix vivo:  <http://vivoweb.org/ontology/core#> \
    prefix rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \
    select DISTINCT ?s \
    where { \
    ?s ?p ?o \
    FILTER(regex(STR(?s),\"individual\")) \
    } LIMIT 1"  2>/dev/null |  sed 1,1d | tr -d '"' | dos2unix
rm $INDV_ONTO


