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
select  DISTINCT ?concept ?label_en ?label_fr
WHERE {
?concept a  <http://www.w3.org/2004/02/skos/core#Concept> .
    OPTIONAL { 
        ?concept rdfs:label ?label_f .
        FILTER (lang(?label_f) = 'fr-CA') .  
        BIND (STR(?label_f)  AS ?label_fr) .
    }
    OPTIONAL { 
        ?concept rdfs:label ?label_e .
        FILTER (lang(?label_e) = 'en-US') .  
        BIND (STR(?label_e)  AS ?label_en) .
    }
}
ORDER BY ASC(?concept)