#!/bin/bash 

###################################################################
# Script Name   :
# Description   : Building CSV DataSource from sample-data for futur sending
#                 to VIVO-Proxy
# Args          : 
# Author       	: Michel Héon PhD
# Institution   : Université du Québec à Montréal
# Copyright     : Université du Québec à Montréal (c) 2021
# Email         : heon.michel@uqam.ca
###################################################################

echo build sample.ttl
# Merge and infer data to new knowledge graph
# riot --output=TTL --rdfs=$DEMO_RESOURCE/vivo.nt  $SAMPLE_DATA/i18n/sample-data-i18n.ttl  $SAMPLE_DATA/i18n/sample-data-i18n-fr_CA.ttl $DEMO_RESOURCE/vivo.nt  2>/dev/null > $DEMO_RESOURCE/sample.ttl 

# echo build name_title.tsv
# sparql --data=../resource/sample.ttl --query=$DEMO_RESOURCE/query/get_name_title.ql --results=TSV 2>/dev/null  > $DEMO_RESOURCE/data/name_title.tsv  
echo build organization.tsv
sparql --data=../resource/sample.ttl --query=$DEMO_RESOURCE/query/get_organization.ql --results=text 2>/dev/null  > $DEMO_RESOURCE/data/organization.tsv 

#echo build position.tsv
#sparql --data=../resource/sample.ttl --query=$DEMO_RESOURCE/query/get_position.ql --results=TSV 2>/dev/null  > $DEMO_RESOURCE/data/position.tsv 
#echo build grant.tsv
#sparql --data=../resource/sample.ttl --query=$DEMO_RESOURCE/query/get_grant.ql --results=TSV 2>/dev/null  > $DEMO_RESOURCE/data/grant.tsv 
#echo build research.tsv
#sparql --data=../resource/sample.ttl --query=$DEMO_RESOURCE/query/get_researchArea.ql --results=TSV 2>/dev/null  > $DEMO_RESOURCE/data/research.tsv 
 

