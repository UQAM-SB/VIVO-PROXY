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
prefix vivo:  <http://vivoweb.org/ontology/core#>
prefix vcard: <http://www.w3.org/2006/vcard/ns#>



select  DISTINCT ?s ?name  ?position_fr ?position_en
WHERE {
?s a  <http://xmlns.com/foaf/0.1/Person> .
?s rdfs:label ?name .
    OPTIONAL { 
        ?s vivo:relatedBy ?rb .
        ?rb a vivo:Position .   
        ?rb rdfs:label ?position_fr .
        FILTER (lang(?position_fr) = 'fr-CA') .  }
    OPTIONAL { 
        ?s vivo:relatedBy ?rb .
        ?rb a vivo:Position .   
        ?rb rdfs:label ?position_en .
        FILTER (lang(?position_en) = 'en-US') . } 
}
ORDER BY ASC(?s)