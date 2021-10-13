package ca.uqam.tool.vivoproxy.swagger.api;

import ca.uqam.tool.vivoproxy.swagger.api.*;
import ca.uqam.tool.vivoproxy.swagger.model.*;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import ca.uqam.tool.vivoproxy.swagger.model.AuthorOfADocument;
import ca.uqam.tool.vivoproxy.swagger.model.ModelAPIResponse;
import ca.uqam.tool.vivoproxy.swagger.model.Person;
import ca.uqam.tool.vivoproxy.swagger.model.PositionOfPerson;
import ca.uqam.tool.vivoproxy.swagger.model.ResourceToResource;

import java.util.Map;
import java.util.List;
import ca.uqam.tool.vivoproxy.swagger.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2021-10-07T14:58:04.779-04:00[America/New_York]")public abstract class PersonApiService {
    public abstract Response createPerson(Person body,SecurityContext securityContext) throws NotFoundException;
    public abstract Response createUsersWithListInput(List<Person> body,SecurityContext securityContext) throws NotFoundException;
    public abstract Response personAddDocument(AuthorOfADocument body,SecurityContext securityContext) throws NotFoundException;
    public abstract Response personAddOrganisationalPositionTo(PositionOfPerson body,SecurityContext securityContext) throws NotFoundException;
    public abstract Response personAddResearchAreaOf(ResourceToResource body,SecurityContext securityContext) throws NotFoundException;
    public abstract Response personHasAddResearchArea(ResourceToResource body,SecurityContext securityContext) throws NotFoundException;
}
