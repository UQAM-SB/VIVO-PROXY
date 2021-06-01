package ca.uqam.tool.vivoproxy.pattern.command.concrete;

import java.io.IOException;

import ca.uqam.tool.vivoproxy.pattern.command.Command;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.pattern.command.VivoReceiver;

public class AddMemberOfCommand implements Command {

    private String personUri;
    private String organizationUri;
    private String roleLabel;
    private String startField_year;
    private String endField_year;
    private String vivoOrganisationType;
    private String organizationLabel;
    private String name;

    public AddMemberOfCommand(String personUri, String organizationUri, String roleLabel, String startField_year,
            String endField_year, String vivoOrganisationType) {
        super();
        this.setPersonUri(personUri);
        this.setOrganizationUri(organizationUri);
        this.setRoleLabel(roleLabel);
        this.setStartField_year(startField_year);
        this.setEndField_year(endField_year);
        this.setVivoOrganisationType(vivoOrganisationType);
        setName(toString());

    }

    public CommandResult execute(VivoReceiver vivo) {
        CommandResult result = null;
        try {
            result = vivo.addMemberOf(getPersonUri(), getOrganizationUri(), getOrganizationLabel(), getRoleLabel(), getStartField_year(),
                    getEndField_year(), getVivoOrganisationType());
         
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

    public String getPersonUri() {
        return personUri;
    }

    public void setPersonUri(String personUri) {
        this.personUri = personUri;
    }

    public String getOrganizationUri() {
        return organizationUri;
    }

    public void setOrganizationUri(String organizationUri) {
        this.organizationUri = organizationUri;
    }

    public String getRoleLabel() {
        return roleLabel;
    }

    public void setRoleLabel(String roleLabel) {
        this.roleLabel = roleLabel;
    }

    public String getStartField_year() {
        return startField_year;
    }

    public void setStartField_year(String startField_year) {
        this.startField_year = startField_year;
    }

    /**
     * @return the endField_year
     */
    public String getEndField_year() {
        return endField_year;
    }

    /**
     * @param endField_year the endField_year to set
     */
    public void setEndField_year(String endField_year) {
        this.endField_year = endField_year;
    }

    /**
     * @return the vivoOrganisationType
     */
    public String getVivoOrganisationType() {
        return vivoOrganisationType;
    }

    /**
     * @param vivoOrganisationType the vivoOrganisationType to set
     */
    public void setVivoOrganisationType(String vivoOrganisationType) {
        this.vivoOrganisationType = vivoOrganisationType;
    }

    /**
     * @return the organizationLabel
     */
    public String getOrganizationLabel() {
        return organizationLabel;
    }

    /**
     * @param organizationLabel the organizationLabel to set
     */
    public void setOrganizationLabel(String organizationLabel) {
        this.organizationLabel = organizationLabel;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "AddMemberOfCommand [" + (personUri != null ? "personUri=" + personUri + ", " : "")
                + (organizationUri != null ? "organizationUri=" + organizationUri + ", " : "")
                + (roleLabel != null ? "roleLabel=" + roleLabel + ", " : "")
                + (startField_year != null ? "startField_year=" + startField_year + ", " : "")
                + (endField_year != null ? "endField_year=" + endField_year + ", " : "")
                + (vivoOrganisationType != null ? "vivoOrganisationType=" + vivoOrganisationType + ", " : "")
                + (organizationLabel != null ? "organizationLabel=" + organizationLabel + ", " : "")
                + (name != null ? "name=" + name : "") + "]";
    }

}
