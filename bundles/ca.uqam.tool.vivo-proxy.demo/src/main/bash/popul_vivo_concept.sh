#!/bin/bash

###################################################################
# Script Name   : popul_vivo_concept.sh
# Description   : Adding skos:Concept in VIVO
# Args          : 
# Author       	: Michel Héon PhD
# Institution   : Université du Québec à Montréal
# Copyright     : Université du Québec à Montréal (c) 2021
# Email         : heon.michel@uqam.ca
###################################################################
NBR_RECORDS=$(find $SRC_DEPT_NAME -name "*.xml" | wc -l)

create_concept () {
        PARAM_JSON=$(mktemp --suffix=.json)
        cat << EOF > $PARAM_JSON
            {
              "labels": [
                {
                  "label": "$CONCEPT_EN",
                  "language": "en-US"
                },
                {
                  "label": "$CONCEPT_FR",
                  "language": "fr-CA"
                }
              ]
            }
EOF
        #
        # Popule VIVO est extrait l'IRI de la ressource
        #
        CONCEPT_IRI=$(curl -s -X 'POST' \
          'http://localhost:9090/vivoproxy/concept/createIRI' \
          -H 'accept: application/json' \
          -H 'Content-Type: application/json' \ \
          -d @$PARAM_JSON | json2txt.sh | grep IRI-value | cut -f 2- -d ':'| tr -d '"' | tr -d " ")
        rm $PARAM_JSON
        echo "    ... CREATING concept ($CONCEPT_EN) ($CONCEPT_EN) ($CONCEPT_IRI) done!"
#        popul_image
}
DATA_FILENAME=$DEMO_RESOURCE/data/concept.txt
NBR_RECORDS=$(cat $DATA_FILENAME | sed 1,3d | head -n -1  | wc -l) 
cat $DATA_FILENAME | sed 1,3d | head -n -1 | while IFS=$'|' read -r -a line_array ; do
    ((LOOP_CTR=LOOP_CTR+1))
    #
    # extract attributes for each record
    #
    ID=${line_array[1]}
    KEY=$(echo $ID | tr -d '>' | tr -d '<' | xargs basename )
    CONCEPT_EN=$(echo ${line_array[2]} | tr -d '"')
    CONCEPT_FR=$(echo ${line_array[3]} | tr -d '"')
    #
    # If one of the variables is empty, replace it with the other
    #
    if [ -z "$CONCEPT_EN" ] ;  then CONCEPT_EN=$CONCEPT_FR ;  fi # fill variable in empty
    if [ -z "$CONCEPT_FR" ] ;  then CONCEPT_FR=$CONCEPT_EN ;  fi # fill variable in empty
    
    echo "Processing: ($LOOP_CTR/$NBR_RECORDS) ($CONCEPT_EN) ($CONCEPT_FR)"
    create_concept
done
wait
echo Done popul_vivo_concept.sh
exit