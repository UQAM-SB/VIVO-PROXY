package ca.uqam.tool.vivoproxy.swagger.api;

import ca.uqam.tool.vivoproxy.swagger.api.*;


import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import ca.uqam.vocab.proxy.model.Concept;
import ca.uqam.vocab.proxy.model.ModelApiResponse;

import java.util.Map;
import java.util.List;
import ca.uqam.tool.vivoproxy.swagger.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2022-02-22T15:03:56.936-05:00[America/New_York]")public abstract class ConceptApiService {
    public abstract Response createConcept(Concept body,SecurityContext securityContext) throws NotFoundException;
    public abstract Response createConceptWithNewIRI(Concept body,SecurityContext securityContext) throws NotFoundException;
}
