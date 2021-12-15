package ca.uqam.tool.vivoproxy.pattern.command.receiver;

import java.io.IOException;

import ca.uqam.tool.util.credential.LOGIN;
import ca.uqam.tool.vivoproxy.pattern.command.AbstractReceiver;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.swagger.model.LinguisticLabel;
import ca.uqam.tool.vivoproxy.swagger.model.PositionOfPerson;
import ca.uqam.vocab.vivo.VIVO;

/**
 * @author Michel Héon; Université du Québec à Montréal
 * @filename VivoReceiver_setPositionToOrganisation_Test.java
 * @date 27 sept. 2021
 */
public class VivoReceiver_setPositionToOrganisation_Test extends AbstractReceiver {
	public static void main (String[] argv) throws Exception
	{
		String username=LOGIN.getUserName();
		String password=LOGIN.getPasswd();
		VivoReceiver vr = new VivoReceiver();
		vr.login(username, password);
		PositionOfPerson pos = new PositionOfPerson();
		pos.setPersonIRI("http://localhost:8080/vivo/individual/n1002");
		pos.setPositionTypeIRI(VIVO.NonAcademicPosition.getURI());
		pos.setOrganisationIRI("http://localhost:8080/vivo/individual/n816");
		LinguisticLabel cLabelFr = new LinguisticLabel();
		cLabelFr.setLabel("Directeur");
		cLabelFr.setLanguage("fr-CA");
		LinguisticLabel cLabelEN = new LinguisticLabel();
		cLabelEN.setLabel("Director");
		cLabelEN.setLanguage("en-US");
		LinguisticLabel cLabelENCA = new LinguisticLabel();
		cLabelENCA.setLabel("Canadian Director");
		cLabelENCA.setLanguage("en-CA");
		pos.setEndFieldYear("2021-01-01T00:00:00");
		pos.setStartFieldYear("2020-01-01T00:00:00");
		pos.addPositionTitleLabelItem(cLabelFr);
		pos.addPositionTitleLabelItem(cLabelEN);
		pos.addPositionTitleLabelItem(cLabelENCA);
		CommandResult resu = vr.addPositionOfPerson(pos);
		System.err.println(resu.getResult());
	}
}
