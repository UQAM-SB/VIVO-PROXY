package ca.uqam.tool.vivoproxy.swagger.api.impl;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import ca.uqam.tool.util.credential.LOGIN;
import ca.uqam.tool.vivoproxy.pattern.command.Command;
import ca.uqam.tool.vivoproxy.pattern.command.CommandFactory;
import ca.uqam.tool.vivoproxy.pattern.command.CommandInvoker;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.pattern.command.concrete.PingCommand;
import ca.uqam.tool.vivoproxy.pattern.command.concrete.ReindexSolrCommand;
import ca.uqam.tool.vivoproxy.pattern.command.receiver.VivoReceiver;
import ca.uqam.tool.vivoproxy.swagger.api.AdminApiService;
import ca.uqam.tool.vivoproxy.swagger.api.ApiResponseMessage;
import ca.uqam.tool.vivoproxy.swagger.api.NotFoundException;
import ca.uqam.tool.vivoproxy.swagger.model.ModelAPIResponse;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2021-12-19T15:35:48.156-05:00[America/New_York]")
public class AdminApiServiceImpl extends AdminApiService {
	private static final String SearchIndex = null;
	public Response pingVivo( SecurityContext securityContext) throws NotFoundException {
		
		CommandFactory cf = CommandFactory.getInstance();
		VivoReceiver session = new VivoReceiver();
		String hostname = session.getHostName();
		CommandInvoker invoker = new CommandInvoker();  
		invoker.register(new PingCommand());
		CommandResult invokerResult = null;
		try {
			invokerResult = invoker.execute();
			com.squareup.okhttp.Response response = invokerResult.getOkhttpResult();
			if (response.body().string().contains("/vivo/termsOfUse"))
			{
				ModelAPIResponse apiResp = new ModelAPIResponse();
				apiResp.setCode(ApiResponseMessage.OK);
				apiResp.setType(new ApiResponseMessage(ApiResponseMessage.OK,"").getType());
				apiResp.setIrIValue(LOGIN.getVivoUrl()+"/"+LOGIN.getVivoSite()+"/termsOfUse");
				apiResp.setApiMessage(String.valueOf(response.code()));
				Response apiResponse = Response.ok().entity(apiResp).build();
				apiResp.setViVOMessage("Pong");
				return apiResponse;
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
			ModelAPIResponse apiResp = new ModelAPIResponse();
			apiResp.setCode(ApiResponseMessage.ERROR);
			apiResp.setType(e.getClass().toString());
			apiResp.setApiMessage(e.getMessage());
			apiResp.setIrIValue(LOGIN.getVivoUrl()+"/"+LOGIN.getVivoSite()+"/termsOfUse");
			try {
				com.squareup.okhttp.Response result = invokerResult.getOkhttpResult();
				apiResp.viVOMessage(result.toString());
			} catch (Exception e2) {
			}
			Response apiResponse = Response.serverError().entity(apiResp).build();
			return apiResponse;
		}
		ModelAPIResponse apiResp = new ModelAPIResponse();
		com.squareup.okhttp.Response result = invokerResult.getOkhttpResult();
		apiResp.setCode(ApiResponseMessage.ERROR);
		apiResp.setIrIValue(LOGIN.getVivoUrl()+"/"+LOGIN.getVivoSite());
		try {
			apiResp.setType(String.valueOf(result.code()));
			apiResp.viVOMessage(result.toString());
		} catch (Exception e) {
		}
		Response apiResponse = Response.serverError().entity(apiResp).build();
		return apiResponse;
	}
	@Override
	public Response reindexVIVO( SecurityContext securityContext) throws NotFoundException {
		com.squareup.okhttp.Response reindexResponse;
		/*
		 * Login to vivo
		 */
		CommandFactory cf = CommandFactory.getInstance();
		VivoReceiver session = new VivoReceiver();
		String hostname = session.getHostName();
		CommandInvoker invoker = new CommandInvoker();  
		Command loginCommand = cf.createLogin(LOGIN.getUserName(), LOGIN.getPasswd());
		ReindexSolrCommand reindexCommand = new ReindexSolrCommand();
		cf.createLogout();
		invoker.register(loginCommand);
		invoker.register(reindexCommand);
		invoker.register(cf.createLogout());
		CommandResult invokerResult = null;
		try {
			invokerResult = invoker.execute();
			com.squareup.okhttp.Response response = reindexCommand.getCommandResult().getOkhttpResult();
			if (response.body().string().contains("SearchIndex?status=true"))
			{
				ModelAPIResponse apiResp = new ModelAPIResponse();
				apiResp.setCode(ApiResponseMessage.OK);
				apiResp.setType(new ApiResponseMessage(ApiResponseMessage.OK,"").getType());
				apiResp.setIrIValue(LOGIN.getVivoUrl()+"/"+LOGIN.getVivoSite()+"/SearchIndex?rebuild=true");
				apiResp.setApiMessage(String.valueOf(response.code()));
				Response apiResponse = Response.ok().entity(apiResp).build();
				apiResp.setViVOMessage("SearchIndex?status=true");
				return apiResponse;
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
			ModelAPIResponse apiResp = new ModelAPIResponse();
			apiResp.setCode(ApiResponseMessage.ERROR);
			apiResp.setType(e.getClass().toString());
			apiResp.setApiMessage(e.getMessage());
			apiResp.setIrIValue(LOGIN.getVivoUrl()+"/"+LOGIN.getVivoSite());
			try {
				com.squareup.okhttp.Response result = invokerResult.getOkhttpResult();
				apiResp.viVOMessage(result.toString());
			} catch (Exception e2) {
			}
			Response apiResponse = Response.serverError().entity(apiResp).build();
			return apiResponse;
		}
		ModelAPIResponse apiResp = new ModelAPIResponse();
		com.squareup.okhttp.Response result = reindexCommand.getCommandResult().getOkhttpResult();
		apiResp.setCode(ApiResponseMessage.ERROR);
		apiResp.setIrIValue(LOGIN.getVivoUrl()+"/"+LOGIN.getVivoSite());
		try {
			apiResp.setType(String.valueOf(result.code()));
			apiResp.viVOMessage(result.toString());
		} catch (Exception e) {
		}
		Response apiResponse = Response.serverError().entity(apiResp).build();
		return apiResponse;

	}
	public static void main(String[] args) throws NotFoundException {
		AdminApiServiceImpl adminApiService = new AdminApiServiceImpl();
		Response result = adminApiService.pingVivo(null);
		System.out.println(result);
	}

}
