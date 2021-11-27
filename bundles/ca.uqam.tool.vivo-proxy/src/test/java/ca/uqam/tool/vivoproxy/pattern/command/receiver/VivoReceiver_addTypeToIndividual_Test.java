package ca.uqam.tool.vivoproxy.pattern.command.receiver;

import java.io.IOException;

import ca.uqam.tool.util.credential.LOGIN;
import ca.uqam.tool.vivoproxy.pattern.command.AbstractReceiver;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.swagger.model.IndividualType;

/**
 * @author Michel Héon
 *
 */
public class VivoReceiver_addTypeToIndividual_Test extends AbstractReceiver {

	public static void main (String[] argv) throws IOException
	{
		String username=LOGIN.getUserName();
		String password=LOGIN.getPasswd();
		VivoReceiver vr = new VivoReceiver();
		vr.login(username, password);
		IndividualType indvType = new IndividualType();
		indvType.setIndividualIRI("http://localhost:8080/vivo/individual/n3324");
		indvType.setVivoTypeIRI("http://vivoweb.org/ontology/core#FacultyMember");
		CommandResult resu = vr.addType(indvType);
		System.err.println(resu.getOkhttpResult().body().string());
	}

}
