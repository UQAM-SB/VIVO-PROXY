package ca.uqam.tool.vivoproxy.swagger.api;

import ca.uqam.tool.vivoproxy.swagger.api.*;
import ca.uqam.tool.vivoproxy.swagger.model.*;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import ca.uqam.tool.vivoproxy.swagger.model.Image;
import ca.uqam.tool.vivoproxy.swagger.model.IndividualType;
import ca.uqam.tool.vivoproxy.swagger.model.LinguisticLabel;
import ca.uqam.tool.vivoproxy.swagger.model.ModelAPIResponse;
import ca.uqam.tool.vivoproxy.swagger.model.Statement;

import java.util.Map;
import java.util.List;
import ca.uqam.tool.vivoproxy.swagger.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2021-12-15T09:09:12.506-05:00[America/New_York]")public abstract class IndvApiService {
    public abstract Response getIndvByIRI( @NotNull String IRI,SecurityContext securityContext) throws NotFoundException;
    public abstract Response getIndvByLabel( @NotNull String label,SecurityContext securityContext) throws NotFoundException;
    public abstract Response indvAddImage(Image body,SecurityContext securityContext) throws NotFoundException;
    public abstract Response indvAddLabel( @NotNull String IRI,List<LinguisticLabel> body,SecurityContext securityContext) throws NotFoundException;
    public abstract Response indvAddStatement(Statement body,SecurityContext securityContext) throws NotFoundException;
    public abstract Response indvAddType(IndividualType body,SecurityContext securityContext) throws NotFoundException;
}
