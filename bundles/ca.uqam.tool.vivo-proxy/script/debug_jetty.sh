#!/bin/bash 

###################################################################
# Script Name   :
# Description   :
# Args          : 
# Author       	: Michel Héon	
# Email         : heon.michel@uqam.ca
###################################################################
SCRIPT_HOME="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null ; pwd )"
cd $SCRIPT_HOME/../
export MAVEN_OPTS="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=4000,server=y,suspend=n"
mvn -T1.5C clean verify jetty:run

