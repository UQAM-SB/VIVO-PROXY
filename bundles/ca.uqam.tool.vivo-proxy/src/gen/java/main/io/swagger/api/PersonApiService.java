package io.swagger.api;

import io.swagger.api.*;
import io.swagger.model.*;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import io.swagger.model.Person;

import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2021-05-31T17:14:53.469-04:00")
public abstract class PersonApiService {
    public abstract Response createPerson(Person body,SecurityContext securityContext) throws NotFoundException;
    public abstract Response getPersonByID(String id,SecurityContext securityContext) throws NotFoundException;
    public abstract Response getPersonByIRI( @NotNull String iri,SecurityContext securityContext) throws NotFoundException;
}
