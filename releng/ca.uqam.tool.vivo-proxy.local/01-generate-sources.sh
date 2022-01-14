#!/bin/bash 

###################################################################
# Script Name   :
# Description   :
# Args          : 
# Author       	: Michel Héon	
# Email         : heon.michel@uqam.ca
###################################################################
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null ; pwd )"
source $SCRIPT_DIR/00-env.sh
cd $VIVO_PROXY_SRC_HOME
mvn -T2C clean generate-sources package -DskipGen=false -s settings.xml --activate-profiles $PROFILE
# Compilation vivo-proxy-client
# cd $VIVO_PROXY_CLIENT_SRC_HOME
# mvn -T2C clean generate-sources package 

