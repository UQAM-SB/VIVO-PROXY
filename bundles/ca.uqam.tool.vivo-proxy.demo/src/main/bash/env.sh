#!/bin/bash 

###################################################################
# Script Name   : env.sh
# Description   : setup environment variables
# Args          : 
# Author       	: Michel Héon PhD
# Institution   : Université du Québec à Montréal
# Copyright     : Université du Québec à Montréal (c) 2021
# Email         : heon.michel@uqam.ca
###################################################################
export PLUGIN_HOME="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && cd ../../../ ; pwd )"
export BASH_HOME="$( cd  $PLUGIN_HOME/src/main/bash ; pwd )"
export PATH=$BASH_HOME:$PATH
export GIT_HOME=$(cd $PLUGIN_HOME; cd ../../../; pwd)
export SAMPLE_DATA=$(cd $GIT_HOME/sample-data ; pwd)
export DEMO_RESOURCE="$(cd $PLUGIN_HOME/src/main/resource ; pwd )"
export HOME_PHOTOS="$(cd $DEMO_RESOURCE/images ; pwd )"

alias cd_bash="cd $BASH_HOME"
alias cd_res="cd $DEMO_RESOURCE"
alias cd_img="cd $HOME_PHOTOS"




