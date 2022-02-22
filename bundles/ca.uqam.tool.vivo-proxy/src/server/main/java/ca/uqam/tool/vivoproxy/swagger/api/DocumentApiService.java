package ca.uqam.tool.vivoproxy.swagger.api;

import ca.uqam.tool.vivoproxy.swagger.api.*;


import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import ca.uqam.vocab.proxy.model.AuthorOfADocument;
import ca.uqam.vocab.proxy.model.Document;
import ca.uqam.vocab.proxy.model.ModelApiResponse;

import java.util.Map;
import java.util.List;
import ca.uqam.tool.vivoproxy.swagger.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2022-02-22T15:03:56.936-05:00[America/New_York]")public abstract class DocumentApiService {
    public abstract Response createDocument(Document body,SecurityContext securityContext) throws NotFoundException;
    public abstract Response documentAddAuthorFor(AuthorOfADocument body,SecurityContext securityContext) throws NotFoundException;
}
