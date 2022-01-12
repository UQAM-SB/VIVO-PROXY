package ca.uqam.tool.vivoproxy.swagger.api;

import ca.uqam.tool.vivoproxy.swagger.model.*;
import ca.uqam.tool.vivoproxy.swagger.api.AdminApiService;
import ca.uqam.tool.vivoproxy.swagger.api.factories.AdminApiServiceFactory;

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


@Path("/admin")


@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2021-12-20T10:43:43.879-05:00[America/New_York]")public class AdminApi  {
   private final AdminApiService delegate;

   public AdminApi(@Context ServletConfig servletContext) {
      AdminApiService delegate = null;

      if (servletContext != null) {
         String implClass = servletContext.getInitParameter("AdminApi.implementation");
         if (implClass != null && !"".equals(implClass.trim())) {
            try {
               delegate = (AdminApiService) Class.forName(implClass).newInstance();
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         } 
      }

      if (delegate == null) {
         delegate = AdminApiServiceFactory.getAdminApi();
      }

      this.delegate = delegate;
   }

    @GET
    @Path("/ping")
    
    @Produces({ "application/json" })
    @Operation(summary = "Ping VIVO instance", description = "", security = {
        @SecurityRequirement(name = "basicAuth")    }, tags={ "admin" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ModelAPIResponse.class))),
        
        @ApiResponse(responseCode = "400", description = "vivo not found"),
        
        @ApiResponse(responseCode = "401", description = "Authentication information is missing or invalid"),
        
        @ApiResponse(responseCode = "404", description = "vivo not found") })
    public Response pingVivo(@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.pingVivo(securityContext);
    }
    @GET
    @Path("/reindex")
    
    @Produces({ "application/json" })
    @Operation(summary = "Send reindex SOLR index signal to VIVO instance", description = "", security = {
        @SecurityRequirement(name = "basicAuth")    }, tags={ "admin" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ModelAPIResponse.class))),
        
        @ApiResponse(responseCode = "400", description = "vivo not found"),
        
        @ApiResponse(responseCode = "401", description = "Authentication information is missing or invalid"),
        
        @ApiResponse(responseCode = "404", description = "vivo not found") })
    public Response reindexVIVO(@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.reindexVIVO(securityContext);
    }
}