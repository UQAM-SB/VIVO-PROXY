package ca.uqam.tool.vivoproxy.pattern.command.receiver;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

import ca.uqam.tool.util.credential.LOGIN;
import ca.uqam.tool.vivoproxy.pattern.command.AbstractReceiver;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.swagger.model.AuthorOfADocument;
import ca.uqam.tool.vivoproxy.swagger.model.IndividualType;
import ca.uqam.tool.vivoproxy.swagger.model.LinguisticLabel;

/**
 * @author Michel Héon
 *
 */
public class VivoReceiver_addLabelsToIndividual_Test extends AbstractReceiver {

			
	public static void main (String[] argv) throws IOException
	{
		String username=LOGIN.getUserName();
		String password=LOGIN.getPasswd();
		VivoReceiver vr = new VivoReceiver();
		vr.login(username, password);
		LinguisticLabel l1 = new LinguisticLabel();
		LinguisticLabel l2 = new LinguisticLabel();
		l1.setLabel("Presses de l'Université de Californie");
		l1.setLanguage("fr-CA");
		l2.setLabel("University of California Press");
		l2.setLanguage("en-US");
		LinguisticLabel[] labels = {l1, l2};
		IndividualType indvType = new IndividualType();
		indvType.setIndividualIRI("http://localhost:8080/vivo/individual/n3324");
		indvType.setVivoTypeIRI("http://vivoweb.org/ontology/core#FacultyMember");
		CommandResult resu = vr.addLabels("http://localhost:8080/vivo/individual/n4669", Arrays.asList(labels));
		System.err.println(resu.getOkhttpResult().body().string());
	}

}
