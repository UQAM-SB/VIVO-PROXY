package ca.uqam.tool.vivoproxy.pattern.command.concrete;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import ca.uqam.tool.vivoproxy.pattern.command.Command;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.pattern.command.Receiver;
import ca.uqam.tool.vivoproxy.pattern.command.receiver.VivoReceiver;
import ca.uqam.tool.vivoproxy.swagger.model.Concept;
import ca.uqam.tool.vivoproxy.swagger.model.Person;

public class AddConceptCommand extends Command {

    private Concept anObject;
    private String login;
    private String passwd;

	public void setMINE_TYPE(String mINE_TYPE) {
		MINE_TYPE = mINE_TYPE;
	}

	private String MINE_TYPE;

    public AddConceptCommand(String login, String passwd, Concept concept, String MINE_TYPE) {
    	super();
    	setAnObject(concept);
        this.setLogin(login);
        this.setPasswd(passwd);
        this.setMINE_TYPE(MINE_TYPE);
        setName(toString());
    	
    }

	@Override
    public CommandResult execute(Receiver vivo) {
        CommandResult result = null;
        try {
           result = ((VivoReceiver)vivo).addConcept(login, passwd, anObject, MINE_TYPE);
            setCommandResult(result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


	@Override
	public String toString() {
		return "AddConceptCommand [anObject=" + anObject + ", login=" + login + ", MINE_TYPE=" + MINE_TYPE + "]";
	}

	public Concept getAnObject() {
		return anObject;
	}

	public void setAnObject(Concept anObject) {
		this.anObject = anObject;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getMINE_TYPE() {
		return MINE_TYPE;
	}

}
