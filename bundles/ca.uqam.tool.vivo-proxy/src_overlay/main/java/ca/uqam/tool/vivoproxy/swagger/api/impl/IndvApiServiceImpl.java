package ca.uqam.tool.vivoproxy.swagger.api.impl;

import ca.uqam.tool.util.credential.LOGIN;
import ca.uqam.tool.vivoproxy.pattern.command.Command;
import ca.uqam.tool.vivoproxy.pattern.command.CommandFactory;
import ca.uqam.tool.vivoproxy.pattern.command.CommandInvoker;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.pattern.command.receiver.VivoReceiver;
import ca.uqam.tool.vivoproxy.swagger.api.*;
import ca.uqam.tool.vivoproxy.swagger.model.*;


import java.util.Map;
import java.util.List;
import java.io.IOException;
import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2021-09-03T18:27:56.556-04:00[America/New_York]")public class IndvApiServiceImpl extends IndvApiService {
    @Override
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
}
