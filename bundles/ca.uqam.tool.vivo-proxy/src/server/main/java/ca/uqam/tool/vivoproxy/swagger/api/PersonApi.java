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

import ca.uqam.tool.vivoproxy.swagger.model.AuthorOfADocument;
import ca.uqam.tool.vivoproxy.swagger.model.ModelAPIResponse;
import ca.uqam.tool.vivoproxy.swagger.model.Person;
import ca.uqam.tool.vivoproxy.swagger.model.PersonWithOfficeInfo;
import ca.uqam.tool.vivoproxy.swagger.model.PositionOfPerson;
import ca.uqam.tool.vivoproxy.swagger.model.ResourceToResource;

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


@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2022-01-13T16:43:41.084-05:00[America/New_York]")public class PersonApi  {
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
    @Produces({ "application/json" })
    @Operation(summary = "Create a person in VIVO", description = "This can only be done by the logged in person.", security = {
        @SecurityRequirement(name = "basicAuth")    }, tags={ "person" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK (successfully authenticated)", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ModelAPIResponse.class))),
        
        @ApiResponse(responseCode = "401", description = "Authentication information is missing or invalid"),
        
        @ApiResponse(responseCode = "405", description = "Invalid input") })
    public Response createPerson(@Parameter(in = ParameterIn.DEFAULT, description = "Created person object" ,required=true) Person body

,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.createPerson(body,securityContext);
    }
    @POST
    @Path("/createWithEmailHasKey")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @Operation(summary = "Create a user in VIVO using the eMail address as an IRI key", description = "This can only be done by the logged in person.", security = {
        @SecurityRequirement(name = "basicAuth")    }, tags={ "person" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK (successfully authenticated)", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ModelAPIResponse.class))),
        
        @ApiResponse(responseCode = "401", description = "Authentication information is missing or invalid"),
        
        @ApiResponse(responseCode = "405", description = "Invalid input") })
    public Response createPersonWithEmail(@Parameter(in = ParameterIn.DEFAULT, description = "Created person object with email" ,required=true) PersonWithOfficeInfo body

,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.createPersonWithEmail(body,securityContext);
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
    @PUT
    @Path("/addDocument")
    @Consumes({ "application/json" })
    @Produces({ "text/turtle", "application/json" })
    @Operation(summary = "Associate a Document to a Person", description = "", security = {
        @SecurityRequirement(name = "basicAuth")    }, tags={ "person" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK (successfully authenticated)", content = @Content(mediaType = "text/turtle", schema = @Schema(implementation = String.class))),
        
        @ApiResponse(responseCode = "401", description = "Authentication information is missing or invalid") })
    public Response personAddDocument(@Parameter(in = ParameterIn.DEFAULT, description = "Author of document" ,required=true) AuthorOfADocument body

,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.personAddDocument(body,securityContext);
    }
    @PUT
    @Path("/addPositionFor")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @Operation(summary = "Add an organizational position for", description = "", security = {
        @SecurityRequirement(name = "basicAuth")    }, tags={ "person" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK (successfully authenticated)", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ModelAPIResponse.class))),
        
        @ApiResponse(responseCode = "401", description = "Authentication information is missing or invalid") })
    public Response personAddOrganisationalPositionTo(@Parameter(in = ParameterIn.DEFAULT, description = "Person that need to be in an organization" ,required=true) PositionOfPerson body

,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.personAddOrganisationalPositionTo(body,securityContext);
    }
    @PUT
    @Path("/addResearchAreaOf")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @Operation(summary = "Create a 'Research Area of' a person", description = "", security = {
        @SecurityRequirement(name = "basicAuth")    }, tags={ "person" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK (successfully authenticated)", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ModelAPIResponse.class))),
        
        @ApiResponse(responseCode = "401", description = "Authentication information is missing or invalid") })
    public Response personAddResearchAreaOf(@Parameter(in = ParameterIn.DEFAULT, description = "Research Area of a person" ,required=true) ResourceToResource body

,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.personAddResearchAreaOf(body,securityContext);
    }
    @PUT
    @Path("/addHasResearchArea")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @Operation(summary = "Create 'has Research Area' for a person", description = "", security = {
        @SecurityRequirement(name = "basicAuth")    }, tags={ "person" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK (successfully authenticated)", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ModelAPIResponse.class))),
        
        @ApiResponse(responseCode = "401", description = "Authentication information is missing or invalid") })
    public Response personHasAddResearchArea(@Parameter(in = ParameterIn.DEFAULT, description = "Research Area of a person" ,required=true) ResourceToResource body

,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.personHasAddResearchArea(body,securityContext);
    }
}
