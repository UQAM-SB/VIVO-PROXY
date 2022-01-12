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
export VIVO_PROXY_TARGET=$(cd $VIVO_PROXY_SRC_HOME/target/ca.uqam.tool.vivo-proxy-1.0.1-SNAPSHOT ; pwd) # Emplacement du target à déployer

export CNAME=vivo-proxy # Prefix de l'URL de déploiement
export AWS_ENV=${CNAME}-env  # Nom de l'environement de déploiement
export AWS_DEPLOY_HOME=$SCRIPT_DIR/aws-deploy-from-local
export EC2_PUB_IP=15.223.109.99    
