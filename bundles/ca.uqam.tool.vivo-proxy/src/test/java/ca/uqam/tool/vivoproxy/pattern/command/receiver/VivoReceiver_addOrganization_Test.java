package ca.uqam.tool.vivoproxy.pattern.command.receiver;

import java.util.Locale;

import com.squareup.okhttp.Response;

import ca.uqam.tool.vivoproxy.pattern.command.AbstractReceiver;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.vocab.proxy.model.LinguisticLabel;
import ca.uqam.vocab.proxy.model.Organization;
import ca.uqam.vocab.vivo.VIVO;

/**
 * @author Michel Héon
 *
 */
public class VivoReceiver_addOrganization_Test extends AbstractReceiver {
	public static void main (String[] argv) throws Exception
	{
		VivoReceiver vr = new VivoReceiver();
		Organization org = new Organization();
		LinguisticLabel cLabelFr = new LinguisticLabel();
		LinguisticLabel cLabelEn = new LinguisticLabel();
		LinguisticLabel cLabelEnCA = new LinguisticLabel();
		LinguisticLabel overviewFr = new LinguisticLabel();
		LinguisticLabel overviewEnUs = new LinguisticLabel();
		LinguisticLabel overviewEnCA = new LinguisticLabel();
		cLabelFr.setLabel("Université du Québec à Montréal");
		cLabelFr.language(Locale.CANADA_FRENCH.toLanguageTag());
		cLabelEn.setLabel("University of Quebec at Montreal");
		cLabelEn.language(Locale.US.toLanguageTag());
		cLabelEnCA.setLabel("University of Quebec at Montreal");
		cLabelEnCA.language(Locale.CANADA.toLanguageTag());
		overviewFr.language(Locale.CANADA_FRENCH.toLanguageTag());
		overviewEnUs.language(Locale.US.toLanguageTag());
		overviewEnCA.language(Locale.CANADA.toLanguageTag());
		overviewFr.label("L’Université du Québec à Montréal (UQAM) est une université publique de langue française dont le rayonnement est international. L’originalité et les caractéristiques propres de ses programmes, sa recherche de pointe souvent axée sur les préoccupations sociales ainsi que ses innovations en création ont contribué à bâtir sa renommée.\nL’université offre de la formation sur le campus montréalais et dans ses campus régionaux situés dans la grande région métropolitaine de Montréal.");
		overviewEnUs.label("The Université du Québec à Montréal (UQAM) is a French-language public university with an international reputation. The originality and specific characteristics of its programs, its cutting-edge research often focused on social concerns, and its creative innovations have helped build its reputation.\nThe university offers training on the Montreal campus and on its regional campuses located in the greater Montreal area.");
		overviewEnCA.label("The Université du Québec à Montréal (UQAM) is a French-language public university with an international reputation. The originality and specific characteristics of its programs, its cutting-edge research often focused on social concerns, and its creative innovations have helped build its reputation.\nThe university offers training on the Montreal campus and on its regional campuses located in the greater Montreal area.");
		org.addNamesItem(cLabelEn);
		org.addNamesItem(cLabelFr);
		org.addNamesItem(cLabelEnCA);
		org.addOverviewItem(overviewFr);
		org.addOverviewItem(overviewEnUs);
		org.addOverviewItem(overviewEnCA);
		org.setOrganizationType(VIVO.University.getURI());
		org.setId("UQAM2");
		
		CommandResult resu = vr.addOrganization(org);
		Response response = resu.getOkhttpResult();
		String model = response.body().string();
		response.body().close();
		System.out.println(response.toString());
	}
}
