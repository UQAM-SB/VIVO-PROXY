#!/bin/bash

###################################################################
# Script Name   : popul_vivo_organization.sh
# Description   : Creating organisation to VIVO from table
#               : organization.tsv 
# Args          : 
# Author       	: Michel Héon PhD
# Institution   : Université du Québec à Montréal
# Copyright     : Université du Québec à Montréal (c) 2021
# Email         : heon.michel@uqam.ca
###################################################################
NBR_RECORDS=$(find $SRC_DEPT_NAME -name "*.xml" | wc -l)
create_organization () {
        PARAM_JSON=$(mktemp --suffix=.json)
        cat << EOF > $PARAM_JSON
            {
              "organizationType": "$TYPE",
              "names": [
                {
                  "label": "$NAME_FR",
                  "language": "fr-CA"
                },
                {
                  "label": "$NAME_EN",
                  "language": "en-US"
                }
              ]
            }        
EOF
        #
        # Populates VIVO with an organization and extracts the IRI of the resource
        #
        ORG_IRI=$(curl -s -X 'POST' \
              'http://localhost:9090/vivoproxy/organization' \
              -H 'accept: application/json' \
              -H 'Content-Type: application/json' \
              -d @$PARAM_JSON | json2txt.sh | grep IRI-value | cut -f 2- -d ':' | tr -d '"' | cut -f 1 -d ';' | tr -d " ")
        rm $PARAM_JSON
        echo "    ... CREATING organisation ($TYPE) OR ($NAME_FR)/($NAME_EN) AT ($ORG_IRI) done!"
#        popul_image
}
add_type () {
        TYPE_JSON=$(mktemp --suffix=.json)
        cat << EOF > $TYPE_JSON
        {
          "individualIRI": "$ORG_IRI",
          "vivoTypeIRI": "$TYPE"
        }
EOF
        curl -s -X 'PUT' \
          'http://localhost:9090/vivoproxy/indv/addType' \
          -H 'accept: application/json' \
          -H 'Content-Type: application/json' \
          -d @$TYPE_JSON > /dev/null 
        rm $TYPE_JSON
        echo "    ... ADDING organisation ($TYPE) FOR ($NAME_FR)/($NAME_EN) AT ($ORG_IRI) is done!"
}

KEY_PR="VOID"
DATA_FILENAME=$DEMO_RESOURCE/data/organization.txt
NBR_RECORDS=$(cat $DATA_FILENAME | sed 1,3d | head -n -1  | wc -l) 
cat $DATA_FILENAME | sed 1,3d | head -n -1 | while IFS=$'|' read -r -a line_array ; do
    ((LOOP_CTR=LOOP_CTR+1))
    ID=${line_array[1]}
    KEY=$(echo $ID | tr -d '>' | tr -d '<' | xargs basename )
    TYPE=$(echo ${line_array[2]} | tr -d '>' | tr -d '<')
    NAME_FR=$(echo ${line_array[3]} | tr -d '"' )
    NAME_EN=$(echo ${line_array[4]} | tr -d '"' )
    if [ -z "$NAME_FR" ] ;  then NAME_FR=$NAME_EN ;  fi # fill variable in empty
    if [ -z "$NAME_EN" ] ;  then NAME_EN=$NAME_FR ;  fi # fill variable in empty
    if [[ "$KEY_PR" != "$KEY" ]]
    then # creation processing for the first organization record
        create_organization
    else # updating type information for previous organization   
          add_type  
    fi
    echo "Processing: ($LOOP_CTR/$NBR_RECORDS) KEY=$KEY ID=$ID TYPE=$TYPE ($NAME_FR) ($NAME_EN)"
    KEY_PR=$KEY
done
wait
echo Done popul_vivo_person.sh
exit

