package ca.uqam.tool.vivoproxy.pattern.command.concrete;

import java.io.IOException;

import ca.uqam.tool.vivoproxy.pattern.command.Command;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.pattern.command.Receiver;
import ca.uqam.tool.vivoproxy.pattern.command.receiver.VivoReceiver;
import ca.uqam.vocab.proxy.model.ResourceToResource;

public class AddResearchAreaOfCommand extends Command {

    private ResourceToResource resourceToLink;
	private CommandResult result;
	private String login;
	private String passwd;
	private String MINE_TYPE;

    public AddResearchAreaOfCommand(String login, String passwd, ResourceToResource resourceToLink, String MINE_TYPE) {
        super();
        setResourceToLink(resourceToLink);
        setLogin(login);
        setPasswd(passwd);
        setMINE_TYPE(MINE_TYPE);
        setName(toString());
    }

    @Override
	public CommandResult execute(Receiver vivo) {
        try {
            result = ((VivoReceiver)vivo).addResearchAreaOfafPerson(login, passwd, resourceToLink, MINE_TYPE);
            this.setCommandResult(result);
            return  result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

	public ResourceToResource getResourceToLink() {
		return resourceToLink;
	}

	public void setResourceToLink(ResourceToResource resourceToLink) {
		this.resourceToLink = resourceToLink;
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

	public void setMINE_TYPE(String mINE_TYPE) {
		MINE_TYPE = mINE_TYPE;
	}

	@Override
	public String toString() {
		return "AddResearchAreaOfCommand [resourceToLink=" + resourceToLink + "]";
	}
}
