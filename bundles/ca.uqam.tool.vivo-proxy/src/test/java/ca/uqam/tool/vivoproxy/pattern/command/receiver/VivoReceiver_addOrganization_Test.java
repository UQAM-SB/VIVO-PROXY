package ca.uqam.tool.vivoproxy.pattern.command.receiver;

import java.io.IOException;

import ca.uqam.tool.util.credential.LOGIN;
import ca.uqam.tool.vivoproxy.pattern.command.AbstractReceiver;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.swagger.model.AuthorOfADocument;
import ca.uqam.tool.vivoproxy.swagger.model.Concept;
import ca.uqam.tool.vivoproxy.swagger.model.LinguisticLabel;
import ca.uqam.tool.vivoproxy.swagger.model.Organization;

/**
 * @author Michel Héon
 *
 */
public class VivoReceiver_addOrganization_Test extends AbstractReceiver {
	public static void main (String[] argv) throws IOException
	{
		String username=LOGIN.getUserName();
		String password=LOGIN.getPasswd();
		VivoReceiver vr = new VivoReceiver();
		vr.login(username, password);
		Organization org = new Organization();
		LinguisticLabel cLabelFr = new LinguisticLabel();
		LinguisticLabel cLabelEn = new LinguisticLabel();
		LinguisticLabel cLabelEnCA = new LinguisticLabel();
		cLabelFr.setLabel("Presses de l'Université de Californie");
		cLabelFr.language("fr-CA");
		cLabelEn.setLabel("University of California Press");
		cLabelEn.language("en-US");
		cLabelEnCA.setLabel("University of California Press (en_CA)");
		cLabelEnCA.language("en-CA");
		org.addNamesItem(cLabelEn);
		org.addNamesItem(cLabelFr);
		org.addNamesItem(cLabelEnCA);
		org.setOrganizationType("http://vivoweb.org/ontology/core#Publisher");
		CommandResult resu = vr.addOrganization(org);
		System.err.println(resu.getOkhttpResult().body().string());
	}
}
