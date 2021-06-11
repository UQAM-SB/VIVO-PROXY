package ca.uqam.tool.vivoproxy.swagger.api;

import ca.uqam.tool.vivoproxy.swagger.api.*;
import ca.uqam.tool.vivoproxy.swagger.model.*;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import ca.uqam.tool.vivoproxy.swagger.model.Vivo;

import java.util.List;
import ca.uqam.tool.vivoproxy.swagger.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2021-06-11T16:42:46.963-04:00")
public abstract class VivoApiService {
    public abstract Response getindividualByIRI( @NotNull String IRI,SecurityContext securityContext) throws NotFoundException;
}
