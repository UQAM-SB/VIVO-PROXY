package ca.uqam.tool.vivoproxy.pattern.command.receiver;

import java.io.IOException;
import java.util.Arrays;

import ca.uqam.tool.util.credential.VIVO_PROXY_Properties;
import ca.uqam.tool.vivoproxy.pattern.command.AbstractReceiver;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.vocab.proxy.model.IndividualType;
import ca.uqam.vocab.proxy.model.LinguisticLabel;

/**
 * @author Michel Héon
 *
 */
public class VivoReceiver_addLabelsToIndividual_Test extends AbstractReceiver {

			
	public static void main (String[] argv) throws Exception
	{
		String username=VIVO_PROXY_Properties.getUserName();
		String password=VIVO_PROXY_Properties.getPasswd();
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
