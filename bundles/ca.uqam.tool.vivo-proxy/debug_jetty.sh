#!/bin/bash 

###################################################################
# Script Name   :
# Description   :
# Args          : 
# Author       	: Michel Héon	
# Email         : heon.michel@uqam.ca
###################################################################
export MAVEN_OPTS="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=4000,server=y,suspend=n"
mvn -q -T1.5C clean verify jetty:run

