#!/bin/bash

###################################################################
# Script Name   : popul_vivo_person.sh
# Description   : Ajoute les personnes dans VIVO
# Args          : 
# Author       	: Michel Héon PhD
# Institution   : Université du Québec à Montréal
# Copyright     : Université du Québec à Montréal (c) 2021
# Email         : heon.michel@uqam.ca
###################################################################
NBR_FILE=$(find $SRC_DEPT_NAME -name "*.xml" | wc -l)


popul_image () {
    if test -f "$HOME_PHOTOS/$KEY.jpg"; then
        imSize=$(identify $HOME_PHOTOS/$KEY.jpg | cut -f 3 -d ' ')
        imWith=$(echo $imSize | cut -f 1 -d 'x')
        imHeight=$(echo $imSize | cut -f 2 -d 'x')
        echo "    ... processing photo ($LOOP_CTR/$NBR_FILE) $NAME $USER_IRI $KEY  ($imSize) ($imWith,$imHeight)"
        IMG_JSON=$(mktemp --suffix=.json)
        cat << EOF > $IMG_JSON 
        {
          "individualIRI": "$USER_IRI",
          "imageURL": "$HOME_PHOTOS/$KEY.jpg",
          "orig_X": 0,
          "orig_Y": 0,
          "width": $imWith,
          "height": $imHeight
        }
EOF
        curl -s -X 'PUT' \
          'http://localhost:9090/vivoproxy/indv/addImage' \
          -H 'accept: application/json' \
          -H 'Content-Type: application/json' \
          -d @$IMG_JSON > /dev/null ; rm $IMG_JSON &
        echo "    ... processing photo ($LOOP_CTR/$NBR_FILE) $NAME $USER_IRI $KEY  Done !"
    else 
        echo "    ... NO photo ($LOOP_CTR/$NBR_FILE) $NAME $USER_IRI $KEY"
    fi
}
create_organization () {
        PARAM_JSON=$(mktemp --suffix=.json)
        cat << EOF > $PARAM_JSON
        {
          "organizationType": "",
          "name": "Harvard University"
        }
        
EOF
        #
        # Popule VIVO est extrait l'IRI de la ressource
        #
        USER_IRI=$(curl -s -X 'POST' \
          'http://localhost:9090/vivoproxy/person' \
          -H 'accept: application/json' \
          -H 'Content-Type: application/json' \
          -d @$PARAM_JSON | json2txt.sh | grep IRI-value | cut -f 2- -d ':'| tr -d '"' | tr -d " ")
        rm $PARAM_JSON
        echo "    ... adding person ($NAME) ($USER_IRI) done!"
#        popul_image
}
add_type_to_person () {
        TYPE_JSON=$(mktemp --suffix=.json)
        cat << EOF > $TYPE_JSON
        {
          "individualIRI": "$USER_IRI",
          "vivoTypeIRI": "$TYPE"
        }
EOF
        curl -s -X 'PUT' \
          'http://localhost:9090/vivoproxy/indv/addType' \
          -H 'accept: application/json' \
          -H 'Content-Type: application/json' \
          -d @$TYPE_JSON > /dev/null 
        rm $TYPE_JSON
        echo "    ... adding person type ($TYPE) to ($NAME) at ($USER_IRI) is done!"
   
}
#cat $DEMO_RESOURCE/data/name_title.tsv |  sed 1,1d | tr -d '"' | dos2unix | grep . | while read line ; do
KEY_PR="VOID"
NBR_FILE=$(cat $DEMO_RESOURCE/data/organization.tsv |  sed 1,1d  | wc -l) 
cat $DEMO_RESOURCE/data/organization.tsv | sed 1,3d | head -n -1 | while IFS=$'|' read -r -a line_array ; do
    ((LOOP_CTR=LOOP_CTR+1))
#    (
        ID=${line_array[1]}
        KEY=$(echo $ID | tr -d '>' | tr -d '<' | xargs basename )
        TYPE=$(echo ${line_array[2]} | tr -d '>' | tr -d '<')
        NAME_FR=$(echo ${line_array[3]} | tr -d '"' )
        NAME_EN=$(echo ${line_array[4]} | tr -d '"' )
        if [[ "$KEY_PR" != "$KEY" ]]
        then
            echo SAME
#          create_person
#          popul_image
        else    
            echo NOT-SAME
#          add_type_to_person    
        fi
        echo "Processing: ($LOOP_CTR/$NBR_FILE) KEY=$KEY ID=$ID TYPE=$TYPE ($NAME_FR) ($NAME_EN)"
        KEY_PR=$KEY
done
wait
echo Done popul_vivo_person.sh
exit

