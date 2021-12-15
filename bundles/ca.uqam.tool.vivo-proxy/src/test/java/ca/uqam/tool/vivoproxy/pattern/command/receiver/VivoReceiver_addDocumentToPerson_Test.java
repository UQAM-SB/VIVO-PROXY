package ca.uqam.tool.vivoproxy.pattern.command.receiver;

import java.io.IOException;

import ca.uqam.tool.util.credential.LOGIN;
import ca.uqam.tool.vivoproxy.pattern.command.AbstractReceiver;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.swagger.model.AuthorOfADocument;

/**
 * @author Michel Héon
 *
 */
public class VivoReceiver_addDocumentToPerson_Test extends AbstractReceiver {
	public static void main (String[] argv) throws Exception
	{
		String username=LOGIN.getUserName();
		String password=LOGIN.getPasswd();
		VivoReceiver vr = new VivoReceiver();
		vr.login(username, password);
		AuthorOfADocument author = new AuthorOfADocument();
		author.setDocumentIRI("http://localhost:8080/vivo/individual/n3789");
		author.setPersonIRI("http://localhost:8080/vivo/individual/n682");
		CommandResult resu = vr.addDocumentToPerson(author);
		System.err.println(resu.getOkhttpResult().body().string());
	}
}
