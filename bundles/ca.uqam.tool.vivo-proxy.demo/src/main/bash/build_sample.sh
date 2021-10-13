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
export PLUGIN_HOME="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && cd ../../../ ; pwd )"
export BASH_HOME="$( cd  $PLUGIN_HOME/src/main/bash ; pwd )"
source $BASH_HOME/env.sh

echo build sample.ttl
# Merge and infer data to new knowledge graph
riot --output=TTL --rdfs=$DEMO_RESOURCE/vivo.nt  $SAMPLE_DATA/i18n/sample-data-i18n.ttl  $SAMPLE_DATA/i18n/sample-data-i18n-fr_CA.ttl $DEMO_RESOURCE/vivo.nt  2>/dev/null > $DEMO_RESOURCE/sample.ttl 

