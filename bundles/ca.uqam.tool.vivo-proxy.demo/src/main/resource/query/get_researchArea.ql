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



select  DISTINCT ?s ?gname ?fname ?researchArea_fr ?researchArea_en
WHERE {
?s a  <http://xmlns.com/foaf/0.1/Person> .
?s rdfs:label ?name_l .
?s obo:ARG_2000028/vcard:hasName/vcard:givenName ?gname_l .
?s obo:ARG_2000028/vcard:hasName/vcard:familyName ?fname_l .
BIND (STR(?name_l)  AS ?name) 
BIND (STR(?gname_l)  AS ?gname) 
BIND (STR(?fname_l)  AS ?fname) 
   OPTIONAL { 
        ?s vivo:hasResearchArea ?ra.    
        ?ra rdfs:label ?researchArea_f .
        FILTER (lang(?researchArea_f) = 'fr-CA') .  }
        BIND (STR(?researchArea_f)  AS ?researchArea_fr) .
    OPTIONAL { 
        ?s vivo:hasResearchArea ?ra.    
        ?ra rdfs:label ?researchArea_e .
        FILTER (lang(?researchArea_e) = 'en-US') . } 
        BIND (STR(?researchArea_e)  AS ?researchArea_en) .
    filter (bound(?researchArea_fr) || bound(?researchArea_en))  
}
ORDER BY ASC(?s)