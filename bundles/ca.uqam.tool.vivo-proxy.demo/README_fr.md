# Résumé
Ce répertoire contient les programmes nécessaires à l'évaluation des capacités de VIVO-Proxy. C'est à partir des données i18n extraites du repo `https://github.com/vivo-project/sample-data` que les applications de cette démo sont utilisé pour populer une instance locale de VIVO à partir de VIVO-PROXY

# Structure du bunble démo
```
.
├── bin
├── README_fr.md
└── src
    └── main
        ├── bash
        │   ├── build_table_datasource.sh
        │   ├── env.sh
        │   ├── json2txt.sh
        │   ├── popul_vivo_opganization.sh
        │   ├── popul_vivo_person.sh
        │   └── upper_first_letter.sh
        ├── java
        └── resource
            ├── data
            │   ├── grant.tsv
            │   ├── name_title.tsv
            │   ├── organization.tsv
            │   ├── position.tsv
            │   └── research.tsv
            ├── images
            │   ├── n1158.jpg
            │   ├── n1736.jpg
            │   ├── n476.jpg
            │   ├── n725.jpg
            │   └── n733.jpg
            ├── query
            │   ├── get_grant.ql
            │   ├── get_name_title.ql
            │   ├── get_organization.ql
            │   ├── get_position.ql
            │   └── get_researchArea.ql
            ├── sample.ttl
            └── vivo.nt
```
