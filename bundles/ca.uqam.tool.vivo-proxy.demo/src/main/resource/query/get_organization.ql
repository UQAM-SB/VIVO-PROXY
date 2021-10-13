prefix fts: <tag:stardog:api:search:>
prefix crdc-data: <http://purl.org/uqam.ca/vocabulary/crdc-ccrd/individual#> 
prefix crdc-ccrd: <http://purl.org/uqam.ca/vocabulary/crdc-ccrd#> 
prefix owl: <http://www.w3.org/2002/07/owl#> 
prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> 
prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> 
prefix skos: <http://www.w3.org/2004/02/skos/core#> 
prefix skos2: <http://www.w3.org/2008/05/skos#> 
prefix terms: <http://purl.org/dc/terms/> 
prefix xsd: <http://www.w3.org/2001/XMLSchema#> 
prefix obo:   <http://purl.obolibrary.org/obo/>
prefix vcard: <http://www.w3.org/2006/vcard/ns#>

select DISTINCT ?s ?type ?name_fr ?name_en
WHERE {
?s a  <http://xmlns.com/foaf/0.1/Organization> .
?s a ?type .
FILTER (regex(STR(?type), "core|Organization")) . 
OPTIONAL { 
    ?s rdfs:label ?name_f .
    FILTER (lang(?name_f) = 'fr-CA') .  
    BIND (STR(?name_f)  AS ?name_fr) .
#    BIND (IF(BOUND(?name_en), ?name_en, "__" ) AS ?name_en2) .
    }
OPTIONAL { 
    ?s rdfs:label ?name_e .
    FILTER (lang(?name_e) = 'en-US') .  
    BIND (STR(?name_e)  AS ?name_en) .
#    BIND ( IF(BOUND(?name_fr), ?name_fr, "__")  AS ?name_fr2) .
    }
}
ORDER BY ASC(?s)