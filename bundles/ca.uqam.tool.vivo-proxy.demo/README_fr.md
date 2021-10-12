# Résumé
Ce répertoire contient les programmes nécessaires à l'évaluation des capacités de VIVO-Proxy. C'est à partir des données i18n extraites du repo `https://github.com/vivo-project/sample-data` que les applications de cette démo sont utilisé pour populer une instance locale de VIVO à partir de VIVO-PROXY

# Utilisation

## Pré requis

- Java 1.8
- Maven
- Linux
- Bash
- Jena 3.17.0
- Image Magik
- curl
- VIVO 1.12.0

## Structure du bundles démo

```
.
├── bin
├── README_en.md
├── README_fr.md
└── src
    └── main
        ├── bash
        │   ├── build_table_datasource.sh
        │   ├── env.sh
        │   ├── get_indv_byLabel.sh
        │   ├── json2txt.sh
        │   ├── popul_vivo_concept.sh
        │   ├── popul_vivo_hasResearchArea.sh
        │   ├── popul_vivo_organization.sh
        │   ├── popul_vivo_person.sh
        │   ├── popul_vivo_position_for.sh
        │   └── upper_first_letter.sh
        ├── java
        └── resource
            ├── data
            │   ├── concept.txt
            │   ├── name_title.tsv
            │   ├── organization.txt
            │   ├── position.txt
            │   └── research_area.txt
            ├── images
            │   ├── n1158.jpg
            │   ├── n1736.jpg
            │   ├── n476.jpg
            │   ├── n725.jpg
            │   └── n733.jpg
            ├── query
            │   ├── get_concept.ql
            │   ├── get_name_title.ql
            │   ├── get_organization.ql
            │   ├── get_position.ql
            │   └── get_researchArea.ql
            ├── sample.ttl
            └── vivo.nt
```
## Structure des API de VIVO-Proxy
 [voir vivo-proxy.yaml à partir du web](https://editor.swagger.io/?url=https://raw.githubusercontent.com/vivo-community/VIVO-PROXY/main/bundles/ca.uqam.tool.vivo-proxy/api/vivo-proxy.yaml) 

## Évaluation rapide
Cette étape consiste à faire une évaluation rapide du fonctionnement de VIVO-PROXY par la réalisation des étapes nécessaires pour créer une personne dans VIVO

### 1) Configurer VIVO-Proxy
```
cd $PROXY_HOME/bundles/ca.uqam.tool.vivo-proxy
./script/start_vivo_proxy.sh
```

Accèder aux api dans le furteur à `http://localhost:9090/`

### 2) Installe et Démarrer VIVO

[Installing VIVO](https://wiki.lyrasis.org/display/VIVODOC112x/Installing+VIVO)

### 3) Créer une personne avec Curl via VIVO-PROXY
Envoyez cette commande dans un script Bash

```
curl -X 'POST' \
  'http://localhost:9090/vivoproxy/person' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "personType": "http://vivoweb.org/ontology/core#FacultyMember",
  "firstName": [
    {
      "label": "Peter",
      "language": "fr-CA"
    },
    {
      "label": "Peter",
      "language": "en-US"
    }
  ],
  "lastName": [
    {
      "label": "Jasper",
      "language": "fr-CA"
    },
    {
      "label": "Jasper",
      "language": "en-US"
    }
  ]
}'
```

## Utiliser VIVO-Proxy démo pour un traitement en lot de données
Cette démonstration consiste à réaliser une population en lot de VIVO à par l'exécution d'un ensemble de scripts pré-programmés

Le flux ETL d'exécution est segmenté selon les étapes suivantes:
```
[sample.ttl] --> générer les tables de données --> pour chaque table; exécuter le script de population --> [VIVO-PROXY] --> [VIVO] 
```

Le détail du flux est représenté ici: 
 
![](demo-workflow.jpg)

### 1) Configuration

`cd ./src/main/bash`

`source env.sh`
