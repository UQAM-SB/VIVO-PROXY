package ca.uqam.tool.vivoproxy.pattern.command.receiver;

import java.io.IOException;

import org.eclipse.rdf4j.model.vocabulary.FOAF;

import ca.uqam.tool.util.credential.LOGIN;
import ca.uqam.tool.vivoproxy.pattern.command.AbstractReceiver;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.pattern.command.util.VivoReceiverHelper;
import ca.uqam.tool.vivoproxy.swagger.model.AuthorOfADocument;
import ca.uqam.tool.vivoproxy.swagger.model.Concept;
import ca.uqam.tool.vivoproxy.swagger.model.LinguisticLabel;
import ca.uqam.tool.vivoproxy.swagger.model.Organization;
import ca.uqam.tool.vivoproxy.swagger.model.PositionOfPerson;
import ca.uqam.vivo.vocabulary.VIVO;

/**
 * @author Michel Héon; Université du Québec à Montréal
 * @filename VivoReceiver_setPositionToOrganisation_Test.java
 * @date 27 sept. 2021
 */
public class VivoReceiver_setPositionToOrganisation_Test extends AbstractReceiver {
	public static void main (String[] argv) throws IOException
	{
		String username=LOGIN.getUserName();
		String password=LOGIN.getPasswd();
		VivoReceiver vr = new VivoReceiver();
		vr.login(username, password);
		PositionOfPerson pos = new PositionOfPerson();
		pos.setPersonIRI("http://localhost:8080/vivo/individual/n962");
		pos.setPositionTypeIRI(VIVO.Position.getURI());
		pos.setOrganisationIRI("http://localhost:8080/vivo/individual/n4508");
		pos.setVivoOrganisationTypeIRI(FOAF.ORGANIZATION.stringValue());
		LinguisticLabel cLabelFr = new LinguisticLabel();
		cLabelFr.setLabel("Directeur");
		cLabelFr.setLanguage("fr-CA");
		LinguisticLabel cLabelEN = new LinguisticLabel();
		cLabelEN.setLabel("Director");
		cLabelEN.setLanguage("en-US");
		LinguisticLabel cLabelENCA = new LinguisticLabel();
		cLabelENCA.setLabel("Canadian Director");
		cLabelENCA.setLanguage("en-CA");
		pos.addPositionTitleLabelItem(cLabelFr);
		pos.addPositionTitleLabelItem(cLabelEN);
		pos.addPositionTitleLabelItem(cLabelENCA);
		CommandResult resu = vr.addPositionOfPerson(pos);
		System.err.println(resu.getResult());
	}
}
