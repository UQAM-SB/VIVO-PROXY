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
aws ec2 describe-instances --filters  "Name=tag:Name,Values=$1" "Name=instance-state-name,Values=running" --query "Reservations[].Instances[].InstanceId" --output=text
