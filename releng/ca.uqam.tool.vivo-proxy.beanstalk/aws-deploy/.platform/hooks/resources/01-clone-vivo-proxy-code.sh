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

mkdir -p /opt/GIT
cd /opt/GIT
[[ -d VIVO-PROXY ]] || git clone https://github.com/UQAM-SB/VIVO-PROXY.git -b dev
