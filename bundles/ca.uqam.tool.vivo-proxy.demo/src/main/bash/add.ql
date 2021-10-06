update=PREFIX  crdc: <http://purl.org/uqam.ca/vocabulary/crdc_ccrd#> 
 PREFIX  ocrer: <http://purl.org/net/OCRe/research.owl#> 
 PREFIX  p3:   <http://vivoweb.org/ontology/vitroAnnotfr_CA#> 
 PREFIX  owl:  <http://www.w3.org/2002/07/owl#> 
 PREFIX  scires: <http://vivoweb.org/ontology/scientific-research#> 
 PREFIX  xsd:  <http://www.w3.org/2001/XMLSchema#> 
 PREFIX  swrlb: <http://www.w3.org/2003/11/swrlb#> 
 PREFIX  skos: <http://www.w3.org/2004/02/skos/core#> 
 PREFIX  rdfs: <http://www.w3.org/2000/01/rdf-schema#> 
 PREFIX  ocresd: <http://purl.org/net/OCRe/study_design.owl#> 
 PREFIX  swo:  <http://www.ebi.ac.uk/efo/swo/> 
 PREFIX  cito: <http://purl.org/spar/cito/> 
 PREFIX  geo:  <http://aims.fao.org/aos/geopolitical.owl#> 
 PREFIX  ocresst: <http://purl.org/net/OCRe/statistics.owl#> 
 PREFIX  dcterms: <http://purl.org/dc/terms/> 
 PREFIX  vivo: <http://vivoweb.org/ontology/core#> 
 PREFIX  text: <http://jena.apache.org/text#> 
 PREFIX  event: <http://purl.org/NET/c4dm/event.owl#> 
 PREFIX  vann: <http://purl.org/vocab/vann/> 
 PREFIX  foaf: <http://xmlns.com/foaf/0.1/> 
 PREFIX  c4o:  <http://purl.org/spar/c4o/> 
 PREFIX  fabio: <http://purl.org/spar/fabio/> 
 PREFIX  swrl: <http://www.w3.org/2003/11/swrl#> 
 PREFIX  vcard: <http://www.w3.org/2006/vcard/ns#> 
 PREFIX  crdc-data: <http://purl.org/uqam.ca/vocabulary/crdc-ccrd/individual#> 
 PREFIX  vitro: <http://vitro.mannlib.cornell.edu/ns/vitro/0.7#> 
 PREFIX  vitro-public: <http://vitro.mannlib.cornell.edu/ns/vitro/public#> 
 PREFIX  rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#> 
 PREFIX  ocresp: <http://purl.org/net/OCRe/study_protocol.owl#> 
 PREFIX  bibo: <http://purl.org/ontology/bibo/> 
 PREFIX  obo:  <http://purl.obolibrary.org/obo/> 
 PREFIX  ro:   <http://purl.obolibrary.org/obo/ro.owl#> 
 PREFIX  sfnc: <http://vivoweb.org/sparql/function#> 
INSERT { GRAPH <> { 
 ?persIRI  a <http://vivoweb.org/ontology/core#Postdoc> , owl:Thing , obo:BFO_0000004 , obo:BFO_0000001 , foaf:Agent , obo:BFO_0000002 , foaf:Person . 
 ?persIRI  vitro:mostSpecificType  <http://vivoweb.org/ontology/core#Postdoc> . 
 ?persIRI obo:ARG_2000028  ?vcardIndv . 
 ?persIRI rdfs:label "Michel Héon"@en-US . 
 ?persIRI rdfs:label "Michel Héon"@fr-CA . 
 ?vcardIndv a vcard:Kind , obo:BFO_0000031 , owl:Thing , obo:IAO_0000030 , obo:BFO_0000002 , obo:ARG_2000379 , obo:BFO_0000001 , vcard:Individual . 
    ?vcardIndv obo:ARG_2000029 ?vivoIndv . 
    ?vcardIndv vcard:hasName ?vcardHasName . 
    ?vcardIndv vitro:mostSpecificType  vcard:Individual . 
  ?vcardHasName a owl:Thing , vcard:Identification , vcard:Addressing , vcard:Explanatory , vcard:Communication , vcard:Name . 
    ?vcardHasName vcard:givenName "Michel"@en-US . 
    ?vcardHasName vcard:givenName "Michel"@fr-CA . 
    ?vcardHasName vcard:familyName "Héon"@en-US . 
    ?vcardHasName vcard:familyName "Héon"@fr-CA . 
} } WHERE { 
 <http://localhost:8080/vivo/individual/n> sfnc:hasNewIRI ?vivoIndv  ; 
    sfnc:hasNewIRI ?vcardIndv ; 
    sfnc:hasNewIRI ?vcardHasName ; 
    sfnc:hasNewIRI ?persIRI . 
 } 