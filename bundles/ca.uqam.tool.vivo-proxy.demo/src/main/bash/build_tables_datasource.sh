#!/bin/bash 

###################################################################
# Script Name   : build_tables_datasource.sh.sh
# Description   : Building CSV DataSource from sample-data for 
                : futur sending to VIVO-Proxy
# Args          : 
# Author       	: Michel Héon PhD
# Institution   : Université du Québec à Montréal
# Copyright     : Université du Québec à Montréal (c) 2021
# Email         : heon.michel@uqam.ca
###################################################################
export PLUGIN_HOME="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && cd ../../../ ; pwd )"
export BASH_HOME="$( cd  $PLUGIN_HOME/src/main/bash ; pwd )"
source $BASH_HOME/env.sh

echo build name_title.tsv
sparql --data=../resource/sample.ttl --query=$DEMO_RESOURCE/query/get_name_title.ql --results=TSV 2>/dev/null  > $DEMO_RESOURCE/data/name_title.tsv
echo build organization.txt
sparql --data=../resource/sample.ttl --query=$DEMO_RESOURCE/query/get_organization.ql --results=text 2>/dev/null  > $DEMO_RESOURCE/data/organization.txt
echo build position.txt
sparql --data=../resource/sample.ttl --query=$DEMO_RESOURCE/query/get_position.ql --results=text 2>/dev/null  > $DEMO_RESOURCE/data/position.txt 
echo build concept.txt
sparql --data=../resource/sample.ttl --query=$DEMO_RESOURCE/query/get_concept.ql --results=text 2>/dev/null  > $DEMO_RESOURCE/data/concept.txt 
echo build research.txt
sparql --data=../resource/sample.ttl --query=$DEMO_RESOURCE/query/get_researchArea.ql --results=text 2>/dev/null  > $DEMO_RESOURCE/data/research_area.txt 
echo " build_tables_datasource.sh DONE!"
