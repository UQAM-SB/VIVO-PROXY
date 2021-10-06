package ca.uqam.tool.vivoproxy.swagger.api;

import ca.uqam.tool.vivoproxy.swagger.api.*;
import ca.uqam.tool.vivoproxy.swagger.model.*;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import ca.uqam.tool.vivoproxy.swagger.model.Concept;
import ca.uqam.tool.vivoproxy.swagger.model.LinguisticLabel;
import ca.uqam.tool.vivoproxy.swagger.model.ModelAPIResponse;

import java.util.Map;
import java.util.List;
import ca.uqam.tool.vivoproxy.swagger.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2021-10-06T07:21:56.290-04:00[America/New_York]")public abstract class ConceptApiService {
    public abstract Response createConcept(Concept body,SecurityContext securityContext) throws NotFoundException;
    public abstract Response createConceptWithNewIRI(LinguisticLabel body,SecurityContext securityContext) throws NotFoundException;
}
