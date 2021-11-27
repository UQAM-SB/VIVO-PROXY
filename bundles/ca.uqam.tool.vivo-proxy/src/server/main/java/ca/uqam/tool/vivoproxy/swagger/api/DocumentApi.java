package ca.uqam.tool.vivoproxy.swagger.api;

import ca.uqam.tool.vivoproxy.swagger.model.*;
import ca.uqam.tool.vivoproxy.swagger.api.DocumentApiService;
import ca.uqam.tool.vivoproxy.swagger.api.factories.DocumentApiServiceFactory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import ca.uqam.tool.vivoproxy.swagger.model.AuthorOfADocument;
import ca.uqam.tool.vivoproxy.swagger.model.Document;
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


@Path("/document")


@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2021-11-25T14:03:34.331-05:00[America/New_York]")public class DocumentApi  {
   private final DocumentApiService delegate;

   public DocumentApi(@Context ServletConfig servletContext) {
      DocumentApiService delegate = null;

      if (servletContext != null) {
         String implClass = servletContext.getInitParameter("DocumentApi.implementation");
         if (implClass != null && !"".equals(implClass.trim())) {
            try {
               delegate = (DocumentApiService) Class.forName(implClass).newInstance();
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         } 
      }

      if (delegate == null) {
         delegate = DocumentApiServiceFactory.getDocumentApi();
      }

      this.delegate = delegate;
   }

    @POST
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @Operation(summary = "Create a document in VIVO", description = "This can only be done by the logged in person.", security = {
        @SecurityRequirement(name = "basicAuth")    }, tags={ "document" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK (successfully authenticated)", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ModelAPIResponse.class))),
        
        @ApiResponse(responseCode = "401", description = "Authentication information is missing or invalid"),
        
        @ApiResponse(responseCode = "405", description = "Invalid input") })
    public Response createDocument(@Parameter(in = ParameterIn.DEFAULT, description = "Created a document" ,required=true) Document body

,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.createDocument(body,securityContext);
    }
    @PUT
    @Path("/addAuthorFor")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @Operation(summary = "Associate a document to an Author", description = "", security = {
        @SecurityRequirement(name = "basicAuth")    }, tags={ "document" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK (successfully authenticated)", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ModelAPIResponse.class))),
        
        @ApiResponse(responseCode = "401", description = "Authentication information is missing or invalid") })
    public Response documentAddAuthorFor(@Parameter(in = ParameterIn.DEFAULT, description = "Author that need to be add to a document" ,required=true) AuthorOfADocument body

,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.documentAddAuthorFor(body,securityContext);
    }
}
