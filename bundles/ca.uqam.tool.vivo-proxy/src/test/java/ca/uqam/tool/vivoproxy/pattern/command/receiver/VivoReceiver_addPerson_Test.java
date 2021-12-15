package ca.uqam.tool.vivoproxy.pattern.command.receiver;

import java.io.IOException;

import com.squareup.okhttp.Response;

import ca.uqam.tool.util.credential.LOGIN;
import ca.uqam.tool.vivoproxy.pattern.command.AbstractReceiver;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.swagger.model.LinguisticLabel;
import ca.uqam.tool.vivoproxy.swagger.model.Person;

/**
 * @author Michel Héon; Université du Québec à Montréal
 * @filename VivoReceiver_addPerson_Test.java
 * @date 6 oct. 2021
 */
public class VivoReceiver_addPerson_Test extends AbstractReceiver {
	public static void main(String[] argv) throws Exception {
		String username = LOGIN.getUserName();
		String password = LOGIN.getPasswd();
		VivoReceiver vr = new VivoReceiver();
		vr.login(username, password);
		Person pers = new Person();
		LinguisticLabel fn = new LinguisticLabel();
		fn.setLabel("First Name");
		LinguisticLabel ln = new LinguisticLabel();
		ln.setLabel("Last Name");
		pers.addFirstNameItem(fn);
		pers.addLastNameItem(ln);
		pers.setPersonType("http://vivoweb.org/ontology/core#FacultyMember");
		CommandResult resu = vr.addPerson(pers);
		Response response = resu.getOkhttpResult();
		String model = response.body().string();
		System.out.println(response.toString());
		System.out.println(model);
		
//		Organization org = new Organization();
//		LinguisticLabel cLabelFr = new LinguisticLabel();
//		LinguisticLabel cLabelEn = new LinguisticLabel();
//		LinguisticLabel cLabelEnCA = new LinguisticLabel();
//		cLabelFr.setLabel("Presses de l'Université de Californie");
//		cLabelFr.language("fr-CA");
//		cLabelEn.setLabel("University of California Press");
//		cLabelEn.language("en-US");
//		cLabelEnCA.setLabel("University of California Press (en_CA)");
//		cLabelEnCA.language("en-CA");
//		org.addNamesItem(cLabelEn);
//		org.addNamesItem(cLabelFr);
//		org.addNamesItem(cLabelEnCA);
//		org.setOrganizationType("http://vivoweb.org/ontology/core#Publisher");
//		CommandResult resu = vr.addOrganization(org);
//		Response response = resu.getOkhttpResult();
//		String model = response.body().string();
//		System.out.println(VivoReceiverHelper.getUriResponseFromModel(model));
//		System.out.println(response.toString());
	}
}
