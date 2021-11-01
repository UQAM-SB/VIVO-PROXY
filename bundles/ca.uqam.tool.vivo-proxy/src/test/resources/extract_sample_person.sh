#!/bin/bash 

###################################################################
# Script Name   :
# Description   : Extract all persons from vivo sample graph
# Args          : 
# Author       	: Michel Héon PhD
# Institution   : Université du Québec à Montréal
# Copyright     : Université du Québec à Montréal (c) 2021
# Email         : heon.michel@uqam.ca
###################################################################
    TMP_GRAPH=$(mktemp --suffix=.nt)
    TMP_RQ=$(mktemp --suffix=.RQ)
    cat graphs/*.nt > $TMP_GRAPH
    cat <<  EOF > $TMP_RQ
        construct {
    	?s a ?c .
    	?s vcard:givenName ?gn .
    	?s vcard:familyName ?fn .
    }  WHERE {
        ?s a  <http://xmlns.com/foaf/0.1/Person> .
        ?s a ?c .
        ?s rdfs:label ?l .
        ?s obo:ARG_2000028/vcard:hasName/vcard:givenName ?gn .
        ?s obo:ARG_2000028/vcard:hasName/vcard:familyName ?fn .
        filter regex(str(?c),"vivoweb|foaf") 
    }
EOF
    sparql --data=graphs/sample-data-orig-infer.ttl --query=@$TMP_RQ --results=CSV 
    rm $TMP_GRAPH $TMP_RQ