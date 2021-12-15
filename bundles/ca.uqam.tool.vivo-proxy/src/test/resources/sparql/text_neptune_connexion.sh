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
rsparql --service=https://vivo-demo-db.cluster-c2o1sdzzfasi.ca-central-1.neptune.amazonaws.com:8182/sparql "select ?p ?o where {<http://localhost:8080/vivo/individual/n5947> ?p ?o} limit 10"
