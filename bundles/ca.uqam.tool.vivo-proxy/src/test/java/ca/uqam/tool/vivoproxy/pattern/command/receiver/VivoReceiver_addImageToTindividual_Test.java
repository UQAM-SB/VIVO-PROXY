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
		image.setIndividualIRI("http://vivo-uqam.ca-central-1.elasticbeanstalk.com/individual?uri=http%3A%2F%2Fpurl.org%2Fvivo.uqam.ca%2Fdata%2Fpeople%23abdallah_chahrazad_uqam_ca");
		image.setIndividualIRI("http://purl.org/vivo.uqam.ca/data/people#abdallah_chahrazad_uqam_ca");
		image.setImageURL("/home/heon/01-SPRINT/2021-11-12-SPRINT-08/VivoStudioWS2/VS-GIT/vivo.uqam.data.src/00-DATA_SRC/Photos/2021-09-16/photos_nom/abbondanza.mona.jpg");
		image.setOrigX(0);
		image.setOrigY(0);
		image.setHeight(270);
		image.setWidth(270);
		CommandResult resu = vr.addImageToIndividual(image);
		System.err.println(resu.getOkhttpResult().body().string());
	}
}
