package ca.uqam.tool.vivoproxy.swagger.api.impl;

import ca.uqam.tool.util.credential.LOGIN;
import ca.uqam.tool.vivoproxy.pattern.command.Command;
import ca.uqam.tool.vivoproxy.pattern.command.CommandFactory;
import ca.uqam.tool.vivoproxy.pattern.command.CommandInvoker;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.pattern.command.concrete.AddDocumentCommand;
import ca.uqam.tool.vivoproxy.pattern.command.concrete.AddImageToIndividualCommand;
import ca.uqam.tool.vivoproxy.pattern.command.concrete.AddTypeToIndividualCommand;
import ca.uqam.tool.vivoproxy.pattern.command.receiver.VivoReceiver;
import ca.uqam.tool.vivoproxy.pattern.command.util.VivoReceiverHelper;
import ca.uqam.tool.vivoproxy.swagger.api.*;
import ca.uqam.tool.vivoproxy.swagger.model.*;


import java.util.Map;
import java.util.logging.Logger;
import java.util.List;
import java.io.IOException;
import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
/**
 * @author Michel Héon; Université du Québec à Montréal
 *
 */
/**
 * @author heon
 *
 */
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2021-09-03T18:27:56.556-04:00[America/New_York]")
public class IndvApiServiceImpl extends IndvApiService { 
	private final static Logger LOGGER = Logger.getLogger(IndvApiServiceImpl.class.getName());

	/* (non-Javadoc)
	 * @see ca.uqam.tool.vivoproxy.swagger.api.IndvApiService#getIndvByLabel(java.lang.String, javax.ws.rs.core.SecurityContext)
	 */
	public Response getIndvByLabel( @NotNull String label, SecurityContext securityContext) throws NotFoundException {
		try {
			CommandFactory cf = CommandFactory.getInstance();
			VivoReceiver session = new VivoReceiver();
			CommandInvoker invoker = new CommandInvoker();
			Command sparqlDescribeByLabelCommand = cf.createSparqlDescribeByLabelCommand(LOGIN.getUserName(), LOGIN.getPasswd(), label,	"text/plain");
			invoker.register(sparqlDescribeByLabelCommand);
			CommandResult resu;
			resu = invoker.execute();
			com.squareup.okhttp.Response response = (com.squareup.okhttp.Response) sparqlDescribeByLabelCommand.getCommandResult().getOkhttpResult();
			String message = response.body().string();
			return Response.ok().entity(new VivoProxyResponseMessage(VivoProxyResponseMessage.OK, message)).build();
		} catch (IOException e) {
			return Response.serverError()
					.entity(new VivoProxyResponseMessage(VivoProxyResponseMessage.ERROR, e.getMessage())).build();
		}    
	}
	/* (non-Javadoc)
	 * @see ca.uqam.tool.vivoproxy.swagger.api.IndvApiService#getIndvByIRI(java.lang.String, javax.ws.rs.core.SecurityContext)
	 */
	public Response getIndvByIRI( @NotNull String IRI, SecurityContext securityContext) throws NotFoundException {
		try {
			CommandFactory cf = CommandFactory.getInstance();
			VivoReceiver session = new VivoReceiver();
			CommandInvoker invoker = new CommandInvoker();
			Command sparqlDescribeCommand = cf.createSparqlDescribeCommand(LOGIN.getUserName(), LOGIN.getPasswd(), IRI,	"text/plain");
			invoker.register(sparqlDescribeCommand);
			CommandResult resu;
			resu = invoker.execute();
			com.squareup.okhttp.Response response = (com.squareup.okhttp.Response) sparqlDescribeCommand.getCommandResult().getOkhttpResult();
			String message = response.body().string();
			return Response.ok().entity(new VivoProxyResponseMessage(VivoProxyResponseMessage.OK, message)).build();
		} catch (IOException e) {
			return Response.serverError()
					.entity(new VivoProxyResponseMessage(VivoProxyResponseMessage.ERROR, e.getMessage())).build();
		}
	}

	/* (non-Javadoc)
	 * @see ca.uqam.tool.vivoproxy.swagger.api.IndvApiService#indvAddImage(ca.uqam.tool.vivoproxy.swagger.model.Image, javax.ws.rs.core.SecurityContext)
	 */
	public Response indvAddImage(Image body, SecurityContext securityContext) throws NotFoundException {
		try {

			CommandFactory cf = CommandFactory.getInstance();
			VivoReceiver session = new VivoReceiver();
			CommandInvoker invoker = new CommandInvoker();
			/*
			 * Create commands
			 */
			Command loginCommand = cf.createLogin(LOGIN.getUserName(), LOGIN.getPasswd());
			AddImageToIndividualCommand addImageToIndividualCommand = (AddImageToIndividualCommand) cf.createAddImageToIndividualCommand(body);
			Command logOutCommand = cf.createLogout();

			/*
			 * Register commands
			 */
			invoker.register(loginCommand);
			invoker.register(addImageToIndividualCommand);
			invoker.register(logOutCommand);

			/*
			 * Execute commands
			 */
			CommandResult result =invoker.execute();
			/*
			 * Retreive and manage response
			 */
			com.squareup.okhttp.Response response = addImageToIndividualCommand.getCommandResult().getOkhttpResult();
			String newrIri = VivoReceiverHelper.getUriResponse(response.body().string());
			LOGGER.info("Adding image for individual at "+ newrIri+" with return code " + response.code());
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
	@Override
	public Response indvAddType(IndividualType body, SecurityContext securityContext) throws NotFoundException {
		try {
			CommandFactory cf = CommandFactory.getInstance();
			VivoReceiver session = new VivoReceiver();
			CommandInvoker invoker = new CommandInvoker();
			/*
			 * Create commands
			 */
			Command loginCommand = cf.createLogin(LOGIN.getUserName(), LOGIN.getPasswd());
			AddTypeToIndividualCommand addTypeToIndividualCommand = (AddTypeToIndividualCommand) cf.createAddTypeToIndividual(body);
			Command logOutCommand = cf.createLogout();

			/*
			 * Register commands
			 */
			invoker.register(loginCommand);
			invoker.register(addTypeToIndividualCommand);
			invoker.register(logOutCommand);

			/*
			 * Execute commands
			 */
			CommandResult result =invoker.execute();
			/*
			 * Retreive and manage response
			 */
			com.squareup.okhttp.Response response = addTypeToIndividualCommand.getCommandResult().getOkhttpResult();
			String newrIri = VivoReceiverHelper.getUriResponse(response.body().string());
			LOGGER.info("Adding type for individual at "+ newrIri+" with return code " + response.code());
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

}
