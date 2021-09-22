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
    if test -f "$HOME_PHOTOS/$BN.jpg"; then
        imSize=$(identify $HOME_PHOTOS/$BN.jpg | cut -f 3 -d ' ')
        imWith=$(echo $imSize | cut -f 1 -d 'x')
        imHeight=$(echo $imSize | cut -f 2 -d 'x')
        echo "    ... processing photo ($LOOP_CTR/$NBR_FILE) $NOM_COMPLET $USER_IRI $BN  ($imSize) ($imWith,$imHeight)"
        DATA_JSON=$(mktemp --suffix=.json)
        cat << EOF > $DATA_JSON 
        {
          "individualIRI": "$USER_IRI",
          "imageURL": "$HOME_PHOTOS/$BN.jpg",
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
          -d @$DATA_JSON > /dev/null ; rm $DATA_JSON &
        echo "    ... processing photo ($LOOP_CTR/$NBR_FILE) $NOM_COMPLET $USER_IRI $BN  Done !"
    else 
        echo "    ... NO photo ($LOOP_CTR/$NBR_FILE) $NOM_COMPLET $USER_IRI $BN"
    fi
}
cat $DEMO_RESOURCE/data/name_title.tsv |  sed 1,1d | tr -d '"' | dos2unix | grep . | while read line ; do
    ((LOOP_CTR=LOOP_CTR+1))
    (
        FN=$(echo $line | cut -f 3 | sed 's/_/ /g' | upper_first_letter.sh)
        LN=$(echo $line | cut -f 4 | sed 's/_/ /g' | upper_first_letter.sh)
        NAME="$FN $LN"
        echo "Processing: ($LOOP_CTR/$NBR_FILE) ($FN | $LN | $NAME) ..."
        PARAM_JSON=$(mktemp --suffix=.json)
        cat << EOF > $PARAM_JSON
            {
             "personType": "http://vivoweb.org/ontology/core#FacultyMember",
             "firstName": "$PRENOM",
             "lastName": "$NOM"
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
        echo "    ... adding person ($LOOP_CTR/$NBR_FILE) ($BN) ($USER_IRI) done!"
        popul_image
       ) &
        ((j=j+1))
        if [ $j = "5" ]
        then
            wait; ((j=0)) ; echo 
        else
            sleep 0.3
        fi 
done
wait
echo Done popul_vivo_person.sh
exit
