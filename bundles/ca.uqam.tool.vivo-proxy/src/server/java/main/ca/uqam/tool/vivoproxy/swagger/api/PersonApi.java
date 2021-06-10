package ca.uqam.tool.vivoproxy.swagger.api;

import ca.uqam.tool.vivoproxy.swagger.model.*;
import ca.uqam.tool.vivoproxy.swagger.api.PersonApiService;
import ca.uqam.tool.vivoproxy.swagger.api.factories.PersonApiServiceFactory;

import io.swagger.annotations.ApiParam;
import io.swagger.jaxrs.*;

import ca.uqam.tool.vivoproxy.swagger.model.Person;

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


@io.swagger.annotations.Api(description = "the person API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2021-06-10T18:06:13.178-04:00")
public class PersonApi  {
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
    
    
    @Produces({ "application/xml", "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Create a person in VIVO", notes = "This can only be done by the logged in person.", response = Void.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "basicAuth")
    }, tags={ "person", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK (successfully authenticated)", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Authentication information is missing or invalid", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 200, message = "successful operation", response = Void.class) })
    public Response createPerson(@ApiParam(value = "Created person object" ,required=true) Person body
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.createPerson(body,securityContext);
    }
    @GET
    @Path("/{id}")
    
    @Produces({ "application/xml", "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Get person by ID", notes = "", response = Person.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "basicAuth")
    }, tags={ "person", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "successful operation", response = Person.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Invalid personname supplied", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Authentication information is missing or invalid", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "person not found", response = Void.class) })
    public Response getPersonByID(@ApiParam(value = "The name that needs to be fetched. Use person1 for testing. ",required=true) @PathParam("id") String id
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.getPersonByID(id,securityContext);
    }
    @GET
    @Path("/iri")
    
    @Produces({ "application/xml", "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Get person by VIVO IRI", notes = "", response = Person.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "basicAuth")
    }, tags={ "person", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "successful operation", response = Person.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Invalid personname supplied", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Authentication information is missing or invalid", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "person not found", response = Void.class) })
    public Response getPersonByIRI(@ApiParam(value = "The iri that needs to be fetched. Use person1 for testing. ",required=true) @QueryParam("iri") String iri
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.getPersonByIRI(iri,securityContext);
    }
}
