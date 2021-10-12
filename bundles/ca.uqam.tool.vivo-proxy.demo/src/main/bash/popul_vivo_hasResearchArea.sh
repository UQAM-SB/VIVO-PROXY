#!/bin/bash 

###################################################################
# Script Name   : popul_vivo_hasResearchArea.sh
# Description   : Populate expertise for people
# Args          : 
# Author       	: Michel Héon PhD
# Institution   : Université du Québec à Montréal
# Copyright     : Université du Québec à Montréal (c) 2021
# Email         : heon.michel@uqam.ca
###################################################################
PLUGIN_HOME="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && cd ../../../ ; pwd )"
BASH_HOME="$( cd  $PLUGIN_HOME/src/main/bash ; pwd )"
source $BASH_HOME/env.sh
DATA_FILENAME=$DEMO_RESOURCE/data/research_area.txt
create_researchArea () {
    DATA_JSON=$(mktemp --suffix=.json)
    cat << EOF > $DATA_JSON 
     {
          "subject-IRI": "$PERS_IRI",
          "object-IRI": "$RE_IRI"
       }
EOF
      HRE_IRI=$(curl -s -X 'PUT'  'http://localhost:9090/vivoproxy/person/addHasResearchArea' \
      -H 'accept: application/json' \
      -H 'Content-Type: application/json' \
      -d @$DATA_JSON  | json2txt.sh | grep IRI-value | cut -f 2- -d ':' | tr -d '"' | cut -f 1 -d ';' | tr -d " ")
      rm $DATA_JSON &
       echo "    CREATING hasResearchArea ($NAME) at ($PERS_IRI) for ($RE_FR) at ($HRE_IRI)  ... done!"
}
KEY_PR="VOID"
NBR_RECORDS=$(cat $DATA_FILENAME | sed 1,3d | head -n -1 | wc -l) 
cat $DATA_FILENAME | sed 1,3d | head -n -1 | while IFS=$'|' read -r -a line_array ; do
    ((LOOP_CTR=LOOP_CTR+1))
    #
    # extract attributes for each record
    #
    ID=${line_array[1]}
    KEY=$(echo $ID | tr -d '>' | tr -d '<' | xargs basename )
    FN=$(echo ${line_array[2]} | tr -d '"' )
    LN=$(echo ${line_array[3]} | tr -d '"' )
    NAME="$FN $LN"
    RE_FR=$(echo ${line_array[4]} | tr -d '"')
    RE_EN=$(echo ${line_array[5]} | tr -d '"')
    #
    # If one of the variables is empty, replace it with the other
    #
    if [ -z "$RE_EN" ] ;  then RE_EN=$RE_FR ;  fi # fill variable in empty
    if [ -z "$RE_FR" ] ;  then RE_FR=$RE_EN ;  fi # fill variable in empty
    
    echo "Processing: ($LOOP_CTR/$NBR_RECORDS) ($NAME) ($RE_EN) ($RE_FR)"
    if [[ "$KEY_PR" != "$KEY" ]]
        then
        # Modify if there is a change of person
        echo "    GET PERSON: $NAME"
        PERS_IRI=$(get_indv_byLabel.sh "$NAME")
    fi
        echo "    GET AREA: $RE_EN"
    RE_IRI=$(get_indv_byLabel.sh "$RE_EN")
    echo "    CREATING hasResearchArea ($NAME) at ($PERS_IRI) for ($RE_FR) at ($RE_IRI)  "
    create_researchArea
    KEY_PR=$KEY
done

