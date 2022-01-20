#!/bin/bash

###################################################################
# Script Name   :
# Description   :
# Args          : 
# Author       	: Michel Héon	
# Email         : heon.michel@uqam.ca
###################################################################
SCRIPT_HOME="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null ; pwd )"
source $SCRIPT_HOME/00-env.sh
cd $VIVO_PROXY_SRC_HOME
export MAVEN_OPTS="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=4000,server=y,suspend=n"
echo "Déploiement $PROFILE"
mvn -T2C jetty:run --activate-profiles $PROFILE -DskipJetty=false -s settings.xml  | grep -v WARNING
# mvnDebug jetty:run

