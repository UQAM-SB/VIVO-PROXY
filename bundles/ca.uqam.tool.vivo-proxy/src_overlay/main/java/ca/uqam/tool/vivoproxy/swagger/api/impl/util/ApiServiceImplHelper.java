package ca.uqam.tool.vivoproxy.swagger.api.impl.util;

import java.io.IOException;

import javax.ws.rs.core.Response;

import ca.uqam.tool.util.credential.VIVO_PROXY_Properties;
import ca.uqam.tool.vivoproxy.pattern.command.Command;
import ca.uqam.tool.vivoproxy.pattern.command.util.VivoReceiverHelper;
import ca.uqam.tool.vivoproxy.swagger.api.ApiResponseMessage;
import ca.uqam.vocab.proxy.model.ModelApiResponse;
import javassist.bytecode.ByteArray;

/**
 * @author Michel Héon; Université du Québec à Montréal
 * @filename ApiServiceImplHelper.java
 * @date 6 oct. 2021
 */
public class ApiServiceImplHelper {

	/**
	 * Return a standard formated message
	 * @param aCommand
	 * @return
	 * @throws IOException
	 */
	public static Response buildMessage(Command aCommand)  {
		ModelApiResponse apiResp = new ModelApiResponse();
		String hostname = "";
		String vivoSiteName =  "";
		String eMessage = "NULL";
		try {
			hostname = aCommand.getCommandResult().getHostName();
			vivoSiteName =  aCommand.getCommandResult().getVivoSiteName();
			if (aCommand.getCommandResult().isOkhttpResult()){
				com.squareup.okhttp.Response response = aCommand.getCommandResult().getOkhttpResult();
				String model;
				model = response.body().string();
				if (response == null ){
					apiResp.setVivoMessage("Okhttp response is NULL");
					apiResp.setCode(ApiResponseMessage.ERROR);
					apiResp.setApiMessage("Hostname = "+hostname + " VIVO Hostname = "+ vivoSiteName);
				} else 	if (response.code() !=200) {
					apiResp.setIrIValue(response.request().urlString());
					apiResp.setVivoMessage(" return code: " +response.code()+ " "  +response.message());
					apiResp.setApiMessage("\n" + model);
					apiResp.setCode(ApiResponseMessage.ERROR);
					apiResp.setType(new ApiResponseMessage(ApiResponseMessage.ERROR,"").getType());
					Response apiResponse = Response.ok().entity(apiResp).build();
					return apiResponse;
				} else {
					String newUserIris = VivoReceiverHelper.getUriResponseFromModel(model);
					apiResp.setIrIValue(newUserIris);
					apiResp.setVivoMessage(" return code: " +response.code()+ " "  +response.message());
					apiResp.setApiMessage("\n" + model);
					apiResp.setCode(ApiResponseMessage.OK);
					apiResp.setType(new ApiResponseMessage(ApiResponseMessage.OK,"").getType());
					Response apiResponse = Response.ok().entity(apiResp).build();
					return apiResponse;
				}
			} else {
				Object obj = aCommand.getCommandResult().getResult();
				String response = "";
				if (obj instanceof String) {
					response = (String) obj;
				} else if (obj instanceof ByteArray) {
					response = ((ByteArray)obj).toString();
				} else {
					response = obj.toString();
				}
				apiResp.setIrIValue(response);
				apiResp.setVivoMessage("return code: 200");
				apiResp.setApiMessage("Send SPARQL to: " + VIVO_PROXY_Properties.getSparqlUpdateURL());
				apiResp.setCode(ApiResponseMessage.OK);
				apiResp.setType(new ApiResponseMessage(ApiResponseMessage.OK,"").getType());
				Response apiResponse = Response.ok().entity(apiResp).build();
				return apiResponse;
			}
		} catch (IOException e) {
			eMessage   = e.getMessage();
		}
		apiResp.setVivoMessage("Response is "+ eMessage);
		apiResp.setCode(ApiResponseMessage.ERROR);
		apiResp.setApiMessage("Hostname = "+hostname + " VIVO Hostname = "+ vivoSiteName);
		Response apiResponse = Response.ok().entity(apiResp).build();
		return apiResponse;
	}
}
