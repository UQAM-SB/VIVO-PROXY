package ca.uqam.tool.vivoproxy.swagger.api;

import ca.uqam.tool.vivoproxy.swagger.model.*;
import ca.uqam.tool.vivoproxy.swagger.api.ConceptApiService;
import ca.uqam.tool.vivoproxy.swagger.api.factories.ConceptApiServiceFactory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import ca.uqam.tool.vivoproxy.swagger.model.Concept;

import java.util.Map;
import java.util.List;
import ca.uqam.tool.vivoproxy.swagger.api.NotFoundException;

import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.servlet.ServletConfig;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;
import javax.validation.constraints.*;


@Path("/concept")


@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2021-07-30T06:22:19.556-04:00[America/New_York]")public class ConceptApi  {
   private final ConceptApiService delegate;

   public ConceptApi(@Context ServletConfig servletContext) {
      ConceptApiService delegate = null;

      if (servletContext != null) {
         String implClass = servletContext.getInitParameter("ConceptApi.implementation");
         if (implClass != null && !"".equals(implClass.trim())) {
            try {
               delegate = (ConceptApiService) Class.forName(implClass).newInstance();
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         } 
      }

      if (delegate == null) {
         delegate = ConceptApiServiceFactory.getConceptApi();
      }

      this.delegate = delegate;
   }

    @POST
    
    @Consumes({ "application/json" })
    @Produces({ "text/plain", "application/ld+json", "text/n3", "text/turtle", "text/owl-manchester", "text/owl-functional", "application/rdf+xml", "application/owl+xml" })
    @Operation(summary = "Create a concept(skos:Concept) in VIVO", description = "This can only be done by the logged in person.", security = {
        @SecurityRequirement(name = "basicAuth")    }, tags={ "concept" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK Successful operation", content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))),
        
        @ApiResponse(responseCode = "401", description = "Authentication information is missing or invalid"),
        
        @ApiResponse(responseCode = "405", description = "Invalid input") })
    public Response createConcept(@Parameter(in = ParameterIn.DEFAULT, description = "Created skos concept" ,required=true) Concept body

,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.createConcept(body,securityContext);
    }
}
