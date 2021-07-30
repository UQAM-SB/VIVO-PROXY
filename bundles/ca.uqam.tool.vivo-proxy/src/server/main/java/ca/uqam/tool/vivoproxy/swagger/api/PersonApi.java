package ca.uqam.tool.vivoproxy.swagger.api;

import ca.uqam.tool.vivoproxy.swagger.model.*;
import ca.uqam.tool.vivoproxy.swagger.api.PersonApiService;
import ca.uqam.tool.vivoproxy.swagger.api.factories.PersonApiServiceFactory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import ca.uqam.tool.vivoproxy.swagger.model.Person;
import ca.uqam.tool.vivoproxy.swagger.model.PositionOfPerson;

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


@Path("/person")


@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2021-07-29T16:22:48.953-04:00[America/New_York]")public class PersonApi  {
   private final PersonApiService delegate;

   public PersonApi(@Context ServletConfig servletContext) {
      PersonApiService delegate = null;

      if (servletContext != null) {
         String implClass = servletContext.getInitParameter("PersonApi.implementation");
         if (implClass != null && !"".equals(implClass.trim())) {
            try {
               delegate = (PersonApiService) Class.forName(implClass).newInstance();
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         } 
      }

      if (delegate == null) {
         delegate = PersonApiServiceFactory.getPersonApi();
      }

      this.delegate = delegate;
   }

    @POST
    
    @Consumes({ "application/json" })
    @Produces({ "text/plain", "application/ld+json", "text/n3", "text/turtle", "text/owl-manchester", "text/owl-functional", "application/rdf+xml", "application/owl+xml" })
    @Operation(summary = "Create a person in VIVO", description = "This can only be done by the logged in person.", security = {
        @SecurityRequirement(name = "basicAuth")    }, tags={ "person" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK Successful operation", content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))),
        
        @ApiResponse(responseCode = "401", description = "Authentication information is missing or invalid"),
        
        @ApiResponse(responseCode = "405", description = "Invalid input") })
    public Response createPerson(@Parameter(in = ParameterIn.DEFAULT, description = "Created person object" ,required=true) Person body

,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.createPerson(body,securityContext);
    }
    @PUT
    
    @Consumes({ "application/json" })
    @Produces({ "text/plain", "application/ld+json", "text/n3", "text/turtle", "text/owl-manchester", "text/owl-functional", "application/rdf+xml", "application/owl+xml" })
    @Operation(summary = "Create organizational position for", description = "", security = {
        @SecurityRequirement(name = "basicAuth")    }, tags={ "person" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK (successfully authenticated)", content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))),
        
        @ApiResponse(responseCode = "401", description = "Authentication information is missing or invalid"),
        
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))) })
    public Response createPositionFor(@Parameter(in = ParameterIn.DEFAULT, description = "Person that need to be in an organization" ,required=true) PositionOfPerson body

,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.createPositionFor(body,securityContext);
    }
    @POST
    @Path("/createWithList")
    @Consumes({ "application/json" })
    @Produces({ "text/plain", "application/ld+json", "text/n3", "text/turtle", "text/owl-manchester", "text/owl-functional", "application/rdf+xml", "application/owl+xml" })
    @Operation(summary = "Creates list of users with given input array", description = "", security = {
        @SecurityRequirement(name = "basicAuth")    }, tags={ "person" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK (successfully authenticated)", content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))),
        
        @ApiResponse(responseCode = "405", description = "Validation exception"),
        
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))) })
    public Response createUsersWithListInput(@Parameter(in = ParameterIn.DEFAULT, description = "List of user object" ,required=true) List<Person> body

,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.createUsersWithListInput(body,securityContext);
    }
}
