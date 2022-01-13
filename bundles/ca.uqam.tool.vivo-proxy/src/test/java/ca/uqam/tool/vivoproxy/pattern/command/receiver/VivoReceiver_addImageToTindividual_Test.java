package ca.uqam.tool.vivoproxy.pattern.command.receiver;

import java.io.IOException;

import ca.uqam.tool.util.credential.LOGIN;
import ca.uqam.tool.vivoproxy.pattern.command.AbstractReceiver;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.swagger.model.Concept;
import ca.uqam.tool.vivoproxy.swagger.model.Image;
import ca.uqam.tool.vivoproxy.swagger.model.LinguisticLabel;

/**
 * @author Michel Héon
 *
 */
public class VivoReceiver_addImageToTindividual_Test extends AbstractReceiver {
	public static void main (String[] argv) throws Exception
	{
		VivoReceiver vr = new VivoReceiver();
		vr.login(LOGIN.getUserName(), LOGIN.getPasswd());
		
		Image image = new Image();
		image.setIndividualIRI("http://localhost:8080/vivo/individual/n2662");
		image.setImageURL("https://photos-uqam.s3.ca-central-1.amazonaws.com/abbondanza.mona.jpg");
//		image.setImageURL("/home/heon/01-SPRINT/2021-11-12-SPRINT-08/VivoStudioWS2/VS-GIT/vivo.uqam.data.src/00-DATA_SRC/Photos/2021-09-16/photos_nom/abbondanza.mona.jpg");
		image.setOrigX(0);
		image.setOrigY(0);
		image.setHeight(270);
		image.setWidth(270);
		CommandResult resu = vr.addImageToIndividual(image);
		System.err.println(resu.getResult());
	}
}
