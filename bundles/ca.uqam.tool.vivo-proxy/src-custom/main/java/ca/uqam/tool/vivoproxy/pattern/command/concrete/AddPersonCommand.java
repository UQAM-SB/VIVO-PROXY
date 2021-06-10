package ca.uqam.tool.vivoproxy.pattern.command.concrete;

import java.io.IOException;
import java.util.UUID;

import ca.uqam.tool.vivoproxy.pattern.command.Command;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.pattern.command.VivoReceiver;

public class AddPersonCommand implements Command {

    private String firstName;
    private String middleName;
    private String lastName;
    private String vivoPersonType;
    private String name;

    public AddPersonCommand(String firstName, String middleName, String lastName, String vivoPersonType) {
        super();
        this.setFirstName(firstName);
        this.setMiddleName(middleName);
        this.setLastName(lastName);
        this.setVivoPersonType(vivoPersonType);
        setName(toString());
    }

    public CommandResult execute(VivoReceiver vivo) {
        CommandResult result = null;
        try {
            result = vivo.addPerson(getFirstName(), getMiddleName(), getLastName(), getVivoPersonType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getVivoPersonType() {
        return vivoPersonType;
    }

    public void setVivoPersonType(String vivoPersonType) {
        this.vivoPersonType = vivoPersonType;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "AddPersonCommand [" + (firstName != null ? "firstName=" + firstName + ", " : "")
                + (middleName != null ? "middleName=" + middleName + ", " : "")
                + (lastName != null ? "lastName=" + lastName + ", " : "")
                + (vivoPersonType != null ? "vivoPersonType=" + vivoPersonType : "") + "]";
    }

}
