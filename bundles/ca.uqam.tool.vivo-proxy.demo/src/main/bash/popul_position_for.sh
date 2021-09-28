#!/bin/bash

PLUGIN_HOME="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && cd ../../../ ; pwd )"
BASH_HOME="$( cd  $PLUGIN_HOME/src/main/bash ; pwd )"
source $BASH_HOME/env.sh
###################################################################
# Script Name   : add_position_for.sh
# Description   :
# Args          : 
# Author       	: Michel Héon PhD
# Institution   : Université du Québec à Montréal
# Copyright     : Université du Québec à Montréal (c) 2021
# Email         : heon.michel@uqam.ca
###################################################################
TSV_FILENAME=$DEMO_RESOURCE/data/position.tsv
create_position () {
    echo "    PERS_IRI=($PERS_IRI) ORG_IRI=($ORG_IRI)"
    DATA_JSON=$(mktemp --suffix=.json)
    cat << EOF > $DATA_JSON 
        {
          "personIRI": "$PERS_IRI",
          "organisationIRI": "$ORG_IRI",
          "positionTitleLabel": [
            {
              "label": "$POS_TITLE_FR",
              "language": "fr-CA"
            },
            {
              "label": "$POS_TITLE_EN",
              "language": "en-US"
            }
          ],
          "positionTypeIRI": "$POS_IRI_TYPE"
        }
EOF
       POS_IRI=$(curl -s -X 'PUT' \
      'http://localhost:9090/vivoproxy/person/addPositionFor' \
      -H 'accept: application/json' \
      -H 'Content-Type: application/json' \
      -d @$DATA_JSON | json2txt.sh | grep IRI-value | cut -f 2- -d ':'| tr -d '"' | tr -d " ")
      rm $DATA_JSON &
#    POS_IRI = $(curl -s -X 'PUT' \
#      'http://localhost:9090/vivoproxy/person/addPositionFor' \
#      -H 'accept: application/json' \
#      -H 'Content-Type: application/json' \
#      -d @$DATA_JSON | json2txt.sh | grep IRI-value | cut -f 2- -d ':'| tr -d '"' | tr -d " ")
#        cat $DATA_JSON ;rm $DATA_JSON &
}
add_type () {
        TYPE_JSON=$(mktemp --suffix=.json)
        cat << EOF > $TYPE_JSON
        {
          "individualIRI": "$POS_IRI",
          "vivoTypeIRI": "$POS_IRI_TYPE"
        }
EOF
        curl -s -X 'PUT' \
          'http://localhost:9090/vivoproxy/indv/addType' \
          -H 'accept: application/json' \
          -H 'Content-Type: application/json' \
          -d @$TYPE_JSON > /dev/null 
        rm $TYPE_JSON
        echo "    ... ADDING position ($POS_IRI_TYPE) FOR ($NAME) AT ($POS_IRI) is done!"
}

KEY_PR="VOID"
ORG_PR="VOID"
NBR_RECORDS=$(cat $TSV_FILENAME | sed 1,3d | head -n -1 | wc -l) 
cat $TSV_FILENAME | sed 1,3d | head -n -1 | while IFS=$'|' read -r -a line_array ; do
    ((LOOP_CTR=LOOP_CTR+1))
    #
    # extract attributes for each record
    #
    ID=${line_array[1]}
    KEY=$(echo $ID | tr -d '>' | tr -d '<' | xargs basename )
    ORG_KEY=$(echo ${line_array[2]} | tr -d '>' | tr -d '<'  )
    FN=$(echo ${line_array[3]} | tr -d '"' )
    LN=$(echo ${line_array[4]} | tr -d '"' )
    NAME="$FN $LN"
    POS_TITLE_FR=$(echo ${line_array[5]} | tr -d '"' )
    POS_TITLE_EN=$(echo ${line_array[6]} | tr -d '"' )
    ORG_TITLE_FR=$(echo ${line_array[7]} | tr -d '"' )
    ORG_TITLE_EN=$(echo ${line_array[8]} | tr -d '"' )
    POS_IRI_TYPE=$(echo ${line_array[9]} | tr -d '"' | tr -d '>' | tr -d '<')
    echo "Processing ($LOOP_CTR/$NBR_RECORDS) ($KEY) ($FN) ($LN) ($POS_TITLE_FR) ($POS_TITLE_EN) ($ORG_TITLE_FR) ($ORG_TITLE_EN) ($POS_IRI_TYPE)"
#    echo "KEY_PR $KEY_PR KEY $KEY ORG_PR $ORG_PR ORG_KEY $ORG_KEY"
    if [[ "$KEY_PR" != "$KEY" ]]
        then
        # Modify if there is a change of person
        echo "    GET PERSON: $NAME"
        PERS_IRI=$(get_indv_byLabel.sh "$NAME")
  
    fi
    if [[ "$ORG_PR" != "$ORG_KEY" ]]
        then
        echo "    GET ORGANIZATION: $ORG_TITLE_EN"
        ORG_IRI=$(get_indv_byLabel.sh "$ORG_TITLE_EN")
    fi
    if [[ "$KEY_PR" != "$KEY"  || "$ORG_PR" != "$ORG_KEY" ]]
        then
        create_position
        else
        add_type
    fi
      KEY_PR=$KEY
      ORG_PR=$ORG_KEY
    echo "Done $NAME !"
done
echo Done add_position_for.sh
