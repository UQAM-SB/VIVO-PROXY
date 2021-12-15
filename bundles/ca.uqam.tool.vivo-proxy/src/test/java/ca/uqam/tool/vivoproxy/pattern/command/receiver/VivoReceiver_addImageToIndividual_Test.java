package ca.uqam.tool.vivoproxy.pattern.command.receiver;

import java.io.IOException;

import ca.uqam.tool.util.credential.LOGIN;
import ca.uqam.tool.vivoproxy.pattern.command.AbstractReceiver;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.swagger.model.Image;

/**
 * @author Michel Héon
 *
 */
public class VivoReceiver_addImageToIndividual_Test extends AbstractReceiver {


	public static void main (String[] argv) throws Exception
	{
		String username=LOGIN.getUserName();
		String password=LOGIN.getPasswd();
		VivoReceiver vr = new VivoReceiver();
		vr.login(username, password);
		Image image = new Image();
		image.setImageURL("/home/heon/01-SPRINT/2021-08-01-SPRINT-07/VivoStudioWS/VS-GIT/VIVO-PROXY/bundles/ca.uqam.tool.vivo-proxy/src/test/resources/images/Aaron-Braun.jpg");
		image.setIndividualIRI("http://localhost:8080/vivo/individual/n6089");
		CommandResult resu = vr.addImageToIndividual(image);
		System.err.println(resu.getOkhttpResult().body().string());
		System.err.println(image.toString());
	}

}
