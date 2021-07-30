package ca.uqam.tool.vivoproxy.swagger.api;

import ca.uqam.tool.vivoproxy.swagger.model.*;
import ca.uqam.tool.vivoproxy.swagger.api.VivoApiService;
import ca.uqam.tool.vivoproxy.swagger.api.factories.VivoApiServiceFactory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import ca.uqam.tool.vivoproxy.swagger.model.Vivo;

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


@Path("/vivo")


@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2021-07-29T16:22:48.953-04:00[America/New_York]")public class VivoApi  {
   private final VivoApiService delegate;

   public VivoApi(@Context ServletConfig servletContext) {
      VivoApiService delegate = null;

      if (servletContext != null) {
         String implClass = servletContext.getInitParameter("VivoApi.implementation");
         if (implClass != null && !"".equals(implClass.trim())) {
            try {
               delegate = (VivoApiService) Class.forName(implClass).newInstance();
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         } 
      }

      if (delegate == null) {
         delegate = VivoApiServiceFactory.getVivoApi();
      }

      this.delegate = delegate;
   }

    @GET
    
    
    @Produces({ "application/json", "text/plain", "application/rdf+xml", "text/n3", "text/turtle", "text/funtional", "text/manchester", "application/owl+xml" })
    @Operation(summary = "Get an individual by IRI", description = "", security = {
        @SecurityRequirement(name = "basicAuth")    }, tags={ "vivo" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Vivo.class))),
        
        @ApiResponse(responseCode = "400", description = "Invalid personname supplied"),
        
        @ApiResponse(responseCode = "401", description = "Authentication information is missing or invalid"),
        
        @ApiResponse(responseCode = "404", description = "person not found") })
    public Response getindividualByIRI(@Parameter(in = ParameterIn.QUERY, description = "Used to obtain information from a VIVO individual by the IRI",required=true) @QueryParam("IRI") String IRI
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.getindividualByIRI(IRI,securityContext);
    }
}
