package ca.uqam.tool.vivoproxy.swagger.api.impl;

import ca.uqam.tool.util.credential.LOGIN;
import ca.uqam.tool.vivoproxy.pattern.command.Command;
import ca.uqam.tool.vivoproxy.pattern.command.CommandFactory;
import ca.uqam.tool.vivoproxy.pattern.command.CommandInvoker;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.pattern.command.concrete.AddConceptCommand;
import ca.uqam.tool.vivoproxy.pattern.command.concrete.AddDocumentCommand;
import ca.uqam.tool.vivoproxy.pattern.command.receiver.VivoReceiver;
import ca.uqam.tool.vivoproxy.pattern.command.util.VivoReceiverHelper;
import ca.uqam.tool.vivoproxy.swagger.api.*;
import ca.uqam.tool.vivoproxy.swagger.model.*;
import ca.uqam.tool.vivoproxy.util.SemanticWebMediaType;

import java.util.Map;
import java.util.logging.Logger;
import java.util.List;
import java.io.IOException;
import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2021-09-05T10:56:47.302-04:00[America/New_York]")
public class DocumentApiServiceImpl extends DocumentApiService {
	private static final String YOUR_PASSWD = LOGIN.getPasswd(); 
	private static final String YOUR_LOGIN = LOGIN.getUserName();
	private final static Logger LOGGER = Logger.getLogger(DocumentApiServiceImpl.class.getName());

    public Response createDocument(Document document, SecurityContext securityContext) throws NotFoundException {
		try {
			
			CommandFactory cf = CommandFactory.getInstance();
			VivoReceiver session = new VivoReceiver();
			CommandInvoker invoker = new CommandInvoker();
			/*
			 * Create commands
			 */
			Command loginCommand = cf.createLogin(LOGIN.getUserName(), LOGIN.getPasswd());
			AddDocumentCommand addDocumentCommand = (AddDocumentCommand) cf.createAddDocumentCommand(document);
			Command logOutCommand = cf.createLogout();

			/*
			 * Register commands
			 */
			invoker.register(loginCommand);
			invoker.register(addDocumentCommand);
			invoker.register(logOutCommand);

			/*
			 * Execute commands
			 */
			CommandResult result =invoker.execute();
			/*
			 * Retreive and manage response
			 */
			com.squareup.okhttp.Response response = addDocumentCommand.getCommandResult().getOkhttpResult();
			String newrIri = VivoReceiverHelper.getUriResponse(response.body().string());
			LOGGER.info("Creating user at uri "+ newrIri+" with return code " + response.code());
			ModelAPIResponse apiResp = new ModelAPIResponse();
			apiResp.setIrIValue(newrIri);
			apiResp.setViVOMessage(" return code: " +response.code()+ " "  +response.message());
			apiResp.setCode(ApiResponseMessage.OK);
			apiResp.setType(new ApiResponseMessage(ApiResponseMessage.OK,"").getType());
			Response apiResponse = Response.ok().entity(apiResp).build();
			return apiResponse;
		} catch (IOException e) {
			throw new NotFoundException(-1, e.getMessage());
		}
    }
    public Response documentAddAuthorFor(AuthorOfADocument body, SecurityContext securityContext) throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "Not Implemented")).build();
    }

}
