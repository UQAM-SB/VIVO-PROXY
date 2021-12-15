package ca.uqam.tool.vivoproxy.swagger.api;

import ca.uqam.tool.vivoproxy.swagger.model.*;
import ca.uqam.tool.vivoproxy.swagger.api.OrganizationApiService;
import ca.uqam.tool.vivoproxy.swagger.api.factories.OrganizationApiServiceFactory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import ca.uqam.tool.vivoproxy.swagger.model.ModelAPIResponse;
import ca.uqam.tool.vivoproxy.swagger.model.Organization;

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


@Path("/organization")


@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2021-12-15T09:09:12.506-05:00[America/New_York]")public class OrganizationApi  {
   private final OrganizationApiService delegate;

   public OrganizationApi(@Context ServletConfig servletContext) {
      OrganizationApiService delegate = null;

      if (servletContext != null) {
         String implClass = servletContext.getInitParameter("OrganizationApi.implementation");
         if (implClass != null && !"".equals(implClass.trim())) {
            try {
               delegate = (OrganizationApiService) Class.forName(implClass).newInstance();
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         } 
      }

      if (delegate == null) {
         delegate = OrganizationApiServiceFactory.getOrganizationApi();
      }

      this.delegate = delegate;
   }

    @POST
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @Operation(summary = "Create an organization in VIVO", description = "This can only be done by the logged in VIVO.", security = {
        @SecurityRequirement(name = "basicAuth")    }, tags={ "organization" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK (successfully authenticated)", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ModelAPIResponse.class))),
        
        @ApiResponse(responseCode = "502", description = "OK (successfully authenticated)", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ModelAPIResponse.class))),
        
        @ApiResponse(responseCode = "401", description = "Authentication information is missing or invalid") })
    public Response createOrganization(@Parameter(in = ParameterIn.DEFAULT, description = "Created organization object" ,required=true) Organization body

,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.createOrganization(body,securityContext);
    }
}
