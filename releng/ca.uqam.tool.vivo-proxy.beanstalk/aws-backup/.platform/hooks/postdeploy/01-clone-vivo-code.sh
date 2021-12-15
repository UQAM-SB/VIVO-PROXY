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
[[ -d Vitro ]] || git clone https://github.com/vivo-project/Vitro.git Vitro -b rel-1.12.1-RC
[[ -d VIVO ]] || git clone https://github.com/vivo-project/VIVO.git VIVO -b rel-1.12.1-RC
[[ -d Vitro-languages ]] || git clone https://github.com/vivo-project/Vitro-languages.git Vitro-languages -b rel-1.12.1-RC
[[ -d VIVO-languages ]] || git clone https://github.com/vivo-project/VIVO-languages.git VIVO-languages -b rel-1.12.1-RC
[[ -d vitro-languages-uqam ]] || git clone https://bitbucket@bitbucket.org/vivo-workspace/vitro-languages-uqam.git
[[ -d vitro-uqam ]] || git clone https://bitbucket@bitbucket.org/vivo-workspace/vitro-uqam.git
[[ -d vivo-installer-uqam ]] || git clone https://bitbucket@bitbucket.org/vivo-workspace/vivo-installer-uqam.git
[[ -d vivo-languages-uqam ]] || git clone https://bitbucket@bitbucket.org/vivo-workspace/vivo-languages-uqam.git
[[ -d vivo-uqam ]] || git clone https://bitbucket@bitbucket.org/vivo-workspace/vivo-uqam.git