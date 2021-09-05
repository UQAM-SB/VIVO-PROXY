package ca.uqam.tool.vivoproxy.swagger.api;

import ca.uqam.tool.vivoproxy.swagger.model.*;
import ca.uqam.tool.vivoproxy.swagger.api.IndvApiService;
import ca.uqam.tool.vivoproxy.swagger.api.factories.IndvApiServiceFactory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;


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


@Path("/indv")


@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2021-09-04T10:22:20.491-04:00[America/New_York]")public class IndvApi  {
   private final IndvApiService delegate;

   public IndvApi(@Context ServletConfig servletContext) {
      IndvApiService delegate = null;

      if (servletContext != null) {
         String implClass = servletContext.getInitParameter("IndvApi.implementation");
         if (implClass != null && !"".equals(implClass.trim())) {
            try {
               delegate = (IndvApiService) Class.forName(implClass).newInstance();
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         } 
      }

      if (delegate == null) {
         delegate = IndvApiServiceFactory.getIndvApi();
      }

      this.delegate = delegate;
   }

    @GET
    @Path("/byIri")
    
    @Produces({ "text/turtle", "text/plain", "application/ld+json", "text/n3", "text/owl-manchester", "text/owl-functional", "application/rdf+xml", "application/owl+xml", "application/json" })
    @Operation(summary = "Get an individual by IRI", description = "", security = {
        @SecurityRequirement(name = "basicAuth")    }, tags={ "Individual" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "text/turtle", schema = @Schema(implementation = String.class))),
        
        @ApiResponse(responseCode = "400", description = "Invalid IRI supplied"),
        
        @ApiResponse(responseCode = "401", description = "Authentication information is missing or invalid"),
        
        @ApiResponse(responseCode = "404", description = "vivo not found") })
    public Response getIndvByIRI(@Parameter(in = ParameterIn.QUERY, description = "Used to obtain information from a VIVO individual by the IRI",required=true) @QueryParam("IRI") String IRI
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.getIndvByIRI(IRI,securityContext);
    }
    @GET
    @Path("/byLabel")
    
    @Produces({ "text/turtle", "text/plain", "application/ld+json", "text/n3", "text/owl-manchester", "text/owl-functional", "application/rdf+xml", "application/owl+xml", "application/json" })
    @Operation(summary = "Get an individual IRI by label (rdfs:label)", description = "", security = {
        @SecurityRequirement(name = "basicAuth")    }, tags={ "Individual" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "text/turtle", schema = @Schema(implementation = String.class))),
        
        @ApiResponse(responseCode = "400", description = "Invalid label supplied"),
        
        @ApiResponse(responseCode = "401", description = "Authentication information is missing or invalid"),
        
        @ApiResponse(responseCode = "404", description = "labelvivo not found") })
    public Response getIndvByLabel(@Parameter(in = ParameterIn.QUERY, description = "Strict string used to obtain information from a VIVO individual",required=true) @QueryParam("Label") String label
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.getIndvByLabel(label,securityContext);
    }
}
