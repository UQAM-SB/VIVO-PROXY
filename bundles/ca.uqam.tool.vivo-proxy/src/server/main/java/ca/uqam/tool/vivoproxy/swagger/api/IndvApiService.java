package ca.uqam.tool.vivoproxy.swagger.api;

import ca.uqam.tool.vivoproxy.swagger.api.*;


import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import ca.uqam.vocab.proxy.model.Image;
import ca.uqam.vocab.proxy.model.IndividualType;
import ca.uqam.vocab.proxy.model.LinguisticLabel;
import ca.uqam.vocab.proxy.model.ModelApiResponse;
import ca.uqam.vocab.proxy.model.Statement;

import java.util.Map;
import java.util.List;
import ca.uqam.tool.vivoproxy.swagger.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2022-02-22T15:03:56.936-05:00[America/New_York]")public abstract class IndvApiService {
    public abstract Response getIndvByIRI( @NotNull String IRI,SecurityContext securityContext) throws NotFoundException;
    public abstract Response getIndvByLabel( @NotNull String label,SecurityContext securityContext) throws NotFoundException;
    public abstract Response indvAddImage(Image body,SecurityContext securityContext) throws NotFoundException;
    public abstract Response indvAddLabel( @NotNull String IRI,List<LinguisticLabel> body,SecurityContext securityContext) throws NotFoundException;
    public abstract Response indvAddStatement(Statement body,SecurityContext securityContext) throws NotFoundException;
    public abstract Response indvAddType(IndividualType body,SecurityContext securityContext) throws NotFoundException;
}
