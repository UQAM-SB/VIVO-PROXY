package ca.uqam.tool.vivoproxy.pattern.command.receiver;

import java.io.IOException;

import ca.uqam.tool.util.credential.VIVO_PROXY_Properties;
import ca.uqam.tool.vivoproxy.pattern.command.AbstractReceiver;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.vocab.proxy.model.Concept;
import ca.uqam.vocab.proxy.model.LinguisticLabel;

/**
 * @author Michel Héon
 *
 */
public class VivoReceiver_addConcept_Test extends AbstractReceiver {
	public static void main (String[] argv) throws Exception
	{
		String username=VIVO_PROXY_Properties.getUserName();
		String password=VIVO_PROXY_Properties.getPasswd();
		VivoReceiver vr = new VivoReceiver();
		vr.login(username, password);
		Concept concept = new Concept();
		LinguisticLabel cLabelFr = new LinguisticLabel();
		LinguisticLabel cLabelEn = new LinguisticLabel();
		cLabelFr.setLabel("Web Sémantique");
		cLabelFr.language("fr-CA");
		cLabelEn.setLabel("Semantic Web");
		cLabelEn.language("en-US");
		concept.addLabelsItem(cLabelEn);
		concept.addLabelsItem(cLabelFr);
		concept.setIRI("http://purl.org/uqam.ca/vocabulary/expertise#semanticweb");
		CommandResult resu = vr.addConcept(concept);
		System.err.println(resu.getOkhttpResult().body().string());
	}
}
