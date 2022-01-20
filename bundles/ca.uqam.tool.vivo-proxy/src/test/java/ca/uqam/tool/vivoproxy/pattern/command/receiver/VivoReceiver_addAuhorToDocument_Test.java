package ca.uqam.tool.vivoproxy.pattern.command.receiver;

import java.io.IOException;

import ca.uqam.tool.util.credential.VIVO_PROXY_Properties;
import ca.uqam.tool.vivoproxy.pattern.command.AbstractReceiver;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.swagger.model.AuthorOfADocument;

/**
 * @author Michel Héon
 *
 */
public class VivoReceiver_addAuhorToDocument_Test extends AbstractReceiver {

	public static void main (String[] argv) throws Exception
	{
		String username=VIVO_PROXY_Properties.getUserName();
		String password=VIVO_PROXY_Properties.getPasswd();
		VivoReceiver vr = new VivoReceiver();
		vr.login(username, password);
		AuthorOfADocument author = new AuthorOfADocument();
		author.setDocumentIRI("http://localhost:8080/vivo/individual/n691");
//		author.setFirstName("Adriana");
//		author.setLastName(" Oliveira");
		author.setPersonIRI("http://localhost:8080/vivo/individual/n7696");
//		author.setPersonType("http://vivoweb.org/ontology/core#FacultyMember");
		CommandResult resu = vr.addAuhorToDocument(author);
		System.err.println(resu.getOkhttpResult().body().string());
	}

}
