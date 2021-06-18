package ca.uqam.tool.vivoproxy.pattern.command.concrete;

import java.io.IOException;

import ca.uqam.tool.vivoproxy.pattern.command.Command;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.pattern.command.receiver.Receiver;
import ca.uqam.tool.vivoproxy.pattern.command.receiver.VivoReceiver;


public class AddOrganizationCommand implements Command {

    private String organisationName;
    private String vivoOrganisationType;
    private String name;
    private VivoReceiver vivo;

    public AddOrganizationCommand(String organisationName, String vivoOrganisationType) {
        super();
        this.setOrganisationName(organisationName);
        this.setVivoOrganisationType(vivoOrganisationType);
        setName(toString());
    }

    @Override
    public CommandResult execute(Receiver vivo) {
        CommandResult result = null;
        try {
            result = ((VivoReceiver)vivo).addOrganization(getOrganisationName(), getVivoOrganisationType());
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "AddOrganizationCommand ["
                + (getOrganisationName() != null ? "organisationName=" + getOrganisationName() + ", " : "")
                + (getVivoOrganisationType() != null ? "vivoOrganisationType=" + getVivoOrganisationType() : "") + "]";
    }

    public String getOrganisationName() {
        return organisationName;
    }

    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }

    public String getVivoOrganisationType() {
        return vivoOrganisationType;
    }

    public void setVivoOrganisationType(String vivoOrganisationType) {
        this.vivoOrganisationType = vivoOrganisationType;
    }

}
