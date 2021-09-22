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



select  DISTINCT ?s ?name  ?gname ?fname ?vtitle_fr ?vtitle_en ?type

WHERE {
?s a  <http://xmlns.com/foaf/0.1/Person> .
?s a ?type .
?s rdfs:label ?name_l .
?s obo:ARG_2000028/vcard:hasName/vcard:givenName ?gname_l .
?s obo:ARG_2000028/vcard:hasName/vcard:familyName ?fname_l .
BIND (STR(?name_l)  AS ?name) 
BIND (STR(?gname_l)  AS ?gname) 
BIND (STR(?fname_l)  AS ?fname) 
FILTER (regex(STR(?type), "core|Person")) .  
OPTIONAL { 
    ?s obo:ARG_2000028/vcard:hasTitle/vcard:title ?vtitle_f .
    FILTER (lang(?vtitle_f) = 'fr-CA') .  
    BIND (STR(?vtitle_f)  AS ?vtitle_fr) .
    }
OPTIONAL { 
    ?s obo:ARG_2000028/vcard:hasTitle/vcard:title ?vtitle_e .
    FILTER (lang(?vtitle_e) = 'en-US') .  
    BIND (STR(?vtitle_e)  AS ?vtitle_en) .
    }
}