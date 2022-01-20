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
cd $VIVO_PROXY_SRC_HOME
#mvn -T2C clean install -s settings.xml -P$PROFILE -DskipJetty=true
cd $VIVO_PROXY_CLIENT_SRC_HOME
mvn -T2C clean install 


