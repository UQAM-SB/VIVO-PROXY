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
import ca.uqam.tool.vivoproxy.swagger.model.Statement;

/**
 * @author Michel Héon
 *
 */
public class VivoReceiver_addStatement_Test extends AbstractReceiver {

	public static void main (String[] argv) throws IOException
	{
		String username=LOGIN.getUserName();
		String password=LOGIN.getPasswd();
		VivoReceiver vr = new VivoReceiver();
		vr.login(username, password);
		Statement stmt = new Statement();
		stmt.setSubject("http://localhost:8080/vivo/individual/n4253");
		stmt.setPredicate("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
		stmt.setObject("http://vivoweb.org/ontology/core#FacultyMember");
		CommandResult resu = vr.addaStatement(stmt)	;
		System.err.println(resu.getOkhttpResult().body().string());
	}

}
