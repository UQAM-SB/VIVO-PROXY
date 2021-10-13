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



select  DISTINCT ?s ?rel ?gname ?fname  ?position_fr ?position_en ?orgLabel_fr ?orgLabel_en ?posType
WHERE {
?s a  <http://xmlns.com/foaf/0.1/Person> .
?s rdfs:label ?name_l .
?s obo:ARG_2000028/vcard:hasName/vcard:givenName ?gname_l .
?s obo:ARG_2000028/vcard:hasName/vcard:familyName ?fname_l .
BIND (STR(?name_l)  AS ?name) 
BIND (STR(?gname_l)  AS ?gname) 
BIND (STR(?fname_l)  AS ?fname) 

    OPTIONAL { 
        ?s <http://vivoweb.org/ontology/core#relatedBy> ?rb .
        ?rb a <http://vivoweb.org/ontology/core#Position> .   
        ?rb a ?posType .
        ?rb rdfs:label ?position_f .
        FILTER (lang(?position_f) = 'fr-CA') .  
        BIND (STR(?position_f)  AS ?position_fr) .
    }
    OPTIONAL { 
        ?s <http://vivoweb.org/ontology/core#relatedBy> ?rb .
        ?rb a <http://vivoweb.org/ontology/core#Position> .   
        ?rb a ?posType .
        ?rb rdfs:label ?position_e .
        FILTER (lang(?position_e) = 'en-US') . 
        BIND (STR(?position_e)  AS ?position_en) .
    } 
    OPTIONAL { 
        ?rb <http://vivoweb.org/ontology/core#relates> ?rel .
        ?rel a <http://xmlns.com/foaf/0.1/Organization> .
        ?rel rdfs:label ?orgLabel_f .
        FILTER (lang(?orgLabel_f) = 'fr-CA') .  
        BIND (STR(?orgLabel_f)  AS ?orgLabel_fr) .
    }
    OPTIONAL { 
        ?rb <http://vivoweb.org/ontology/core#relates> ?rel .
        ?rel a <http://xmlns.com/foaf/0.1/Organization> .
        ?rel rdfs:label ?orgLabel_e .
        FILTER (lang(?orgLabel_e) = 'en-US') .  
        BIND (STR(?orgLabel_e)  AS ?orgLabel_en) .
    }
    FILTER (REGEX(STR(?posType),"Position")) .
    FILTER (COALESCE(?position_fr, ?position_en)) 
}
ORDER BY ASC(?s)