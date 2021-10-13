# Résumé
> Ce répertoire contient les programmes nécessaires à l'évaluation des capacités de VIVO-Proxy. C'est à partir des données i18n extraites du repo `https://github.com/vivo-project/sample-data` que les applications de cette démo sont utilisé pour populer une instance locale de VIVO à partir de VIVO-PROXY

___
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
> L'arborescence ci-dessous présente la répartition des différents composants nécessaires à l'exécution de la démo de VIVO-PROXY.
Le répertoire `bash` contiens un ensemble de scripts utilisés dans la démo. 
Le répertoire `java` est actuellement vide, mais il pourra contenir éventuellement du code nécessaire à la démo
Les répertoires `resources/data`, `resources/images`, `resources/query` contiennent respectivement: les tableaux produits par le script `build_table_datasource.sh`, les images utilisées pour chaque personne et les requêtes d'extraction des données provenant du [sample-data du projet VIVO sur github](https://github.com/vivo-project/sample-data) servant à produire `sample.ttl`

```
.
├── bin
├── README_en.md
├── README_fr.md
└── src
    └── main
        ├── bash
        │   ├── build_sample.sh
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
## Signature des API de VIVO-Proxy
 [voir vivo-proxy.yaml à partir du web](https://editor.swagger.io/?url=https://raw.githubusercontent.com/vivo-community/VIVO-PROXY/main/bundles/ca.uqam.tool.vivo-proxy/api/vivo-proxy.yaml) 

___
# Évaluation rapide
> Cette section consiste à présenter les étapes nécessaires pour faire une évaluation rapide du fonctionnement de VIVO-PROXY. 
L'évaluation rapide consiste à créer une personne dans VIVO par la soumission d'une commande ***curl*** dont les données concernant la personne sont encapsulées dans une structure ***json***

## 1) Configurer VIVO-Proxy
```
cd $PROXY_HOME/bundles/ca.uqam.tool.vivo-proxy
./script/start_vivo_proxy.sh
```
> Accèder aux api de VIVO-PROXY depuis l'adresse  `http://localhost:9090/` dans un fureteur web
## 2) Installer et Démarrer VIVO
> allez à : [Lyrasis-DOC Installing VIVO](https://wiki.lyrasis.org/display/VIVODOC112x/Installing+VIVO)
## 3) Créer une personne avec Curl via VIVO-PROXY
> Envoyez cette commande dans un script ***bash***
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
___
# Utiliser VIVO-Proxy démo pour un traitement en lot des données
> Cette démonstration consiste à réaliser le chargement en lot des données dans VIVO à par l'exécution d'un ensemble de scripts pré-programmés
Le flux ETL d'exécution est segmenté selon les étapes suivantes:
```
[sample.ttl] --> générer les tables de données --> pour chaque table; exécuter le script de population --> [VIVO-PROXY] --> [VIVO] 
```
## Flux d'exécution de la démo
Le détail du flux est représenté ici: 
 
![](demo-workflow.jpg)

- La première étape consiste à créer la fichier `sample.ttl` à partir des graphes de connaissances issus de [Vivo Project Sample Data](https://github.com/vivo-project/sample-data). Dans le processus ETL, le fichier `sample.ttl` représente la source de données d'origine d'où sont extraites les données.
- La deuxième étape consiste à transformer les données de `sample.ttl` sous une forme tabulaire représentant la structure de données des formulaires de VIVO. Par exemple: people, organization, concepts, etc.
- La troisième étape consiste à transformer les données de la forme tabulaire en format ***json*** prêt à être soumis à VIVO-PROXY pour emmagasinage dans VIVO

## Exécution
> Notes: assurez-vous que VIVO et VIVO-PROXY soient démarrés

```
# 1- Créer le fichier sample.ttl
cd $VIVO-PROXY_HOME/bundles/ca.uqam.tool.vivo-proxy.demo/src/main/bash
bash$ ./build_sample.sh
build sample.ttl

# 2- Créer les tables
bash$ ./build_tables_datasource.sh
build name_title.tsv
build organization.txt
build position.txt
build concept.txt
build research.txt
build_tables_datasource.sh.sh DONE! 

# 3 Chargement des données dans VIVO via VIVO-PROXY
# 3 a) les personnes et leur photo
bash$ ./popul_vivo_person.sh
    ... CREATING person (Claudia Martinez) (http://localhost:8080/vivo/individual/n4854) done!
    ... processing photo (1/14) Claudia Martinez http://localhost:8080/vivo/individual/n4854 n1158  (560x600) (560,600)
    ... processing photo (1/14) Claudia Martinez http://localhost:8080/vivo/individual/n4854 n1158  Done !
Processing: (1/14) n1158 <http://localhost:8080/vivo_i18n/individual/n1158> http://vivoweb.org/ontology/core#NonFacultyAcademic Claudia Martinez
    ... ADDING person type (http://vivoweb.org/ontology/core#Postdoc) to (Claudia Martinez) at (http://localhost:8080/vivo/individual/n4854) is done!
Processing: (2/14) n1158 <http://localhost:8080/vivo_i18n/individual/n1158> http://vivoweb.org/o ...

# 3 b) les organisations
bash$ ./popul_vivo_organization.sh
    ... CREATING organisation (http://vivoweb.org/ontology/core#AcademicDepartment) OR (Physique)/(Physics) AT (http://localhost:8080/vivo/individual/n4071) done!
Processing: (1/33) KEY=n1927 ID= <http://localhost:8080/vivo_i18n/individual/n1927>  TYPE=http://vivoweb.org/ontology/core#AcademicDepartment (Physique) (Physics)
    ... ADDING organisation (http://vivoweb.org/ontology/core#Department) FOR (Physique)/(Physics) AT (http://localhost:8080/vivo/individual/n4071) is done!
Processing: (2/33) KEY=n1927 ID= <http://localhost:8080/vivo_i18n/individual/n1927>  TYPE=http://vivoweb.org/ontology/core#Department (Physique) (Physics)
    ... ADDING organisation (http://xmlns.com/foaf/0.1/Organization) FOR (Physique) .....
    
# 3 c) les concepts (domaines d'expertise)
bash$ ./popul_vivo_concept.sh 
Processing: (1/7) (Derrida) (Derrida)
    ... CREATING concept (Derrida) (Derrida) (http://localhost:8080/vivo/individual/n7224) done!
Processing: (2/7) (Civil War Reconstruction) (Reconstruction de la guerre civile)
    ... CREATING concept (Civil War Reconstruction) (Civil War Reconstruction) (http://localhost:8080/vivo/individual/n6053) done!
Processing: (3/7) (Rhetoric) (Rhétorique) ...

# 3 d) Associer une position aux personnes
bash$ ./popul_vivo_position_for.sh 
Processing (1/23) (n1158) (Claudia) (Martinez) (Chercheur Associé) (Postdoctoral Researcher) (Physique) (Physics) (http://vivoweb.org/ontology/core#NonFacultyAcademicPosition)
    GET PERSON: Claudia Martinez
    GET ORGANIZATION: Physics
    PERS_IRI=(http://localhost:8080/vivo/individual/n4854) ORG_IRI=(http://localhost:8080/vivo/individual/n4071)
Done Claudia Martinez !
Processing (2/23) (n1158) (Claudia) (Martinez) (Chercheur Associé) (Postdoctoral Researcher) (Physique) (Physics) (http://vivoweb.org/ontology/core#Position) ...

# 3 e) Associer un domaine de recherche aux personnes
bash$ ./popul_vivo_hasResearchArea.sh 
Processing: (1/6) (Patricia Roberts) (Electracy) (Electracy)
    GET PERSON: Patricia Roberts
    GET AREA: Electracy
    CREATING hasResearchArea (Patricia Roberts) at (http://localhost:8080/vivo/individual/n7689) for (Electracy) at (http://localhost:8080/vivo/individual/n5017)  
    CREATING hasResearchArea (Patricia Roberts) at (http://localhost:8080/vivo/individual/n7689) for (Electracy) at (http://localhost:8080/vivo/individual/n7689)  ... done!
Processing: (2/6) (Patricia Roberts) (Political discourse) (Discours politique) ...

```
## Résultat partiel

> Le tableau ci-dessous présente une illustration du résultat par une saisie d'écran de VIVO
On  y constate que le nom, la photo, le domaine d'expertise ainsi que le titre d'une des personnes de `sample.ttl` sont adéquatement présentés pour le Français et l'Anglais

| Patricia Roberts (Français)  | Patricia Roberts (Anglais) |
|------------------------------|:--------------------------:|
|    ![](VIVO-pat_fr.png)      |  ![](VIVO-pat_en.png)      |

___
# Conclusion
> Cette page avait pour but de présenter la démonstration de l'utilisation de VIVO-PROXY afin de produire une alimentation en LOT des données dans VIVO. 
Après avoir présenté les pré requis nécessaires à son exécution, la démonstration s'est poursuivi par une évaluation rapide des capacités de VIVO-PROXY, pour ensuite se poursuivre par une évaluation plus étendue et profonde des apis qui componsent VIVO-PROXY


