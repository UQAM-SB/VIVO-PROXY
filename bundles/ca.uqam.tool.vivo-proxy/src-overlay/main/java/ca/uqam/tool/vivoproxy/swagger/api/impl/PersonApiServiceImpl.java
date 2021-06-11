package ca.uqam.tool.vivoproxy.swagger.api.impl;

import ca.uqam.tool.vivoproxy.swagger.api.*;
import ca.uqam.tool.vivoproxy.swagger.model.*;

import ca.uqam.tool.vivoproxy.swagger.model.Person;

import java.util.List;
import ca.uqam.tool.vivoproxy.swagger.api.NotFoundException;

import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2021-06-11T05:18:52.086-04:00")
public class PersonApiServiceImpl extends PersonApiService {
    @Override
    public Response createPerson(Person body, SecurityContext securityContext) throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "overlay magic!")).build();
    }
    @Override
    public Response getPersonByID(String id, SecurityContext securityContext) throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "overlay magic!")).build();
    }
    @Override
    public Response getPersonByIRI( @NotNull String iri, SecurityContext securityContext) throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "overlay magic!")).build();
    }
}
