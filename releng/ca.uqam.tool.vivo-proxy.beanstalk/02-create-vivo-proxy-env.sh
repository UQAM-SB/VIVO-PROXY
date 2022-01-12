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
cd $AWS_DEPLOY_HOME/$CNAME
eb init $CNAME -p 'Tomcat 8.5 with Corretto 8 running on 64bit Amazon Linux 2' -r 'ca-central-1' -k 'Cle-VIVO-Demo'
eb create $AWS_ENV -c $CNAME -p 'Tomcat 8.5 with Corretto 8 running on 64bit Amazon Linux 2' -r 'ca-central-1' -k 'Cle-VIVO-Demo' --single
# EC2_ID=$($SCRIPT_DIR/50-describe-ec2-id-by-name.sh "$AWS_ENV")
# aws ec2 disassociate-address --instance-id $EC2_ID 
# aws ec2 associate-address --instance-id $EC2_ID --public-ip $EC2_PUB_IP
