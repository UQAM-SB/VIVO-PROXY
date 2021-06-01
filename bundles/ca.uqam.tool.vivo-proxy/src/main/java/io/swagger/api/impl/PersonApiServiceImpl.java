package io.swagger.api.impl;

import java.security.Principal;
import java.util.logging.Logger;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import io.swagger.api.ApiResponseMessage;
import io.swagger.api.NotFoundException;
import io.swagger.api.PersonApiService;
import io.swagger.model.Person;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2021-05-31T16:36:49.351-04:00")
public class PersonApiServiceImpl extends PersonApiService {
    private final static Logger LOGGER = Logger.getLogger(PersonApiServiceImpl.class.getName());

    @Override
    public Response createPerson(Person body, SecurityContext securityContext) throws NotFoundException {
        // do some magic!
        LOGGER.info("Creating: "+body.toString());
        String sch = securityContext.getAuthenticationScheme();
        Principal up = securityContext.getUserPrincipal();
        
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    @Override
    public Response getPersonByID(String id, SecurityContext securityContext) throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    @Override
    public Response getPersonByIRI( @NotNull String iri, SecurityContext securityContext) throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
}
