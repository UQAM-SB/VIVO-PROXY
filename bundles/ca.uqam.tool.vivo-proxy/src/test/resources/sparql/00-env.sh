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
JENA_HOME="/opt/apache-jena-3.17.0"
export JENA_HOME

JENA_BIN="$JENA_HOME/bin"

PATH=$JENA_BIN:$PATH
export PATH
