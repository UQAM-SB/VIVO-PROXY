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

rm -fr $AWS_DEPLOY_HOME/$CNAME ; mkdir -p $AWS_DEPLOY_HOME/$CNAME
cd $VIVO_PROXY_SRC_HOME
mvn -T2C clean generate-sources  install -s settings.xml  -Paws-dev-local

cd  $AWS_DEPLOY_HOME
cp -r .ebextensions .elasticbeanstalk .platform ./$CNAME
echo "Done!"



