package ca.uqam.tool.vivoproxy.swagger.api;

import ca.uqam.tool.vivoproxy.swagger.api.*;
import ca.uqam.tool.vivoproxy.swagger.model.*;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import ca.uqam.tool.vivoproxy.swagger.model.ModelApiResponse;
import ca.uqam.tool.vivoproxy.swagger.model.VivoProperties;

import java.util.Map;
import java.util.List;
import ca.uqam.tool.vivoproxy.swagger.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2022-01-19T17:23:11.919-05:00[America/New_York]")public abstract class AdminApiService {
    public abstract Response getVivoProperties(SecurityContext securityContext) throws NotFoundException;
    public abstract Response pingVivo(SecurityContext securityContext) throws NotFoundException;
    public abstract Response reindexVIVO(SecurityContext securityContext) throws NotFoundException;
    public abstract Response setVivoProperties(VivoProperties body,SecurityContext securityContext) throws NotFoundException;
}
