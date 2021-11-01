package ca.uqam.tool.vivoproxy.pattern.command.concrete;

import java.io.IOException;

import ca.uqam.tool.vivoproxy.pattern.command.Command;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.pattern.command.Receiver;
import ca.uqam.tool.vivoproxy.pattern.command.receiver.VivoReceiver;
import ca.uqam.tool.vivoproxy.swagger.model.Organization;


public class AddOrganizationCommand extends Command {

    private Organization organization;

    public AddOrganizationCommand(Organization organization) {
        super();
        this.setOrganization(organization);
        setName(toString());
    }

    @Override
    public CommandResult execute(Receiver vivo) {
        CommandResult result = null;
        try {
            result = ((VivoReceiver)vivo).addOrganization(organization);
            setCommandResult(result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@Override
	public String toString() {
		return "AddOrganizationCommand [organization=" + organization + "]";
	}

}
