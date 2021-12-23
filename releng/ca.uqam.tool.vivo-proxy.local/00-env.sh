#!/bin/bash 

###################################################################
# Script Name   : env.sh
# Description   : Déclaration des variables d'environnement nécessaires
#               : au fonctionnement des scripts
# Args          : 
# Author       	: Michel Héon PhD
# Institution   : Université du Québec à Montréal
# Copyright     : Université du Québec à Montréal (c) 2021
# Email         : heon.michel@uqam.ca
###################################################################
export PATH=$PATH:~/.local/bin:./
export SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"  # variable contenant le nom du répertoire de ce script

export VIVO_PROXY_SRC_HOME=$(cd $SCRIPT_DIR/../../bundles/ca.uqam.tool.vivo-proxy ; pwd) # Emplacement du plugin de vivo-proxy
export VIVO_PROXY_CLIENT_SRC_HOME=$(cd $SCRIPT_DIR/../../bundles/ca.uqam.tool.vivo-proxy ; pwd) # Emplacement du plugin de vivo-proxy

export VIVO_PROXY_TARGET=$(cd $VIVO_PROXY_SRC_HOME/target/ca.uqam.tool.vivo-proxy-1.0.1-SNAPSHOT ; pwd) # Emplacement du target à déployer
export PROFILE=localhost-jetty     #l'un des profiles disponibles dans settings.xml
# export PROFILE=localhost