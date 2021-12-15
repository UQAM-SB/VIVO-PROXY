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
source /etc/profile
cd /opt/GIT/VIVO-PROXY/bundles/ca.uqam.tool.vivo-proxy
mvn -T2C clean verify -DskipTests=true -s settings.xml -Paws-dev

cp -r /tmp/resources/vivo/* /opt/vivo/home
chown -R tomcat:tomcat /opt/vivo

