package ca.uqam.tool.vivoproxy.swagger.api.impl.util;

import java.io.IOException;

import javax.ws.rs.core.Response;

import ca.uqam.tool.vivoproxy.pattern.command.Command;
import ca.uqam.tool.vivoproxy.pattern.command.util.VivoReceiverHelper;
import ca.uqam.tool.vivoproxy.swagger.api.ApiResponseMessage;
import ca.uqam.tool.vivoproxy.swagger.model.ModelAPIResponse;

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
	public static Response buildMessage(Command aCommand) throws IOException {
		com.squareup.okhttp.Response response = aCommand.getCommandResult().getOkhttpResult();
		String model = response.body().string();
		System.out.println(model);
		String newUserIris = VivoReceiverHelper.getUriResponseFromModel(model);
		ModelAPIResponse apiResp = new ModelAPIResponse();
		apiResp.setIrIValue(newUserIris);
		apiResp.setViVOMessage(" return code: " +response.code()+ " "  +response.message());
		apiResp.setApiMessage("\n" + model);
		apiResp.setCode(ApiResponseMessage.OK);
		apiResp.setType(new ApiResponseMessage(ApiResponseMessage.OK,"").getType());
		Response apiResponse = Response.ok().entity(apiResp).build();
		return apiResponse;
	}
}
