package ca.uqam.tool.vivoproxy.swagger.api;

import ca.uqam.tool.vivoproxy.swagger.api.*;
import ca.uqam.tool.vivoproxy.swagger.model.*;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import java.util.List;
import ca.uqam.tool.vivoproxy.swagger.model.Person;
import ca.uqam.tool.vivoproxy.swagger.model.PositionOfPerson;

import java.util.List;
import ca.uqam.tool.vivoproxy.swagger.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2021-07-19T10:52:45.494-04:00")
public abstract class PersonApiService {
    public abstract Response createPerson(Person body,SecurityContext securityContext) throws NotFoundException;
    public abstract Response createPositionFor(PositionOfPerson body,SecurityContext securityContext) throws NotFoundException;
    public abstract Response createUsersWithListInput(List<Person> body,SecurityContext securityContext) throws NotFoundException;
}
