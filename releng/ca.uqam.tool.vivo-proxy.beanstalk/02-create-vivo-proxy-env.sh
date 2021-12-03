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
cd $VIVO_PROXY_TARGET
eb init vivo-proxy -p 'Tomcat 8.5 with Corretto 8 running on 64bit Amazon Linux 2' -r 'ca-central-1' -k 'Cle-VIVO-Demo'
eb create $AWS_ENV -c $CNAME -p 'Tomcat 8.5 with Corretto 8 running on 64bit Amazon Linux 2' -r 'ca-central-1' -k 'Cle-VIVO-Demo'