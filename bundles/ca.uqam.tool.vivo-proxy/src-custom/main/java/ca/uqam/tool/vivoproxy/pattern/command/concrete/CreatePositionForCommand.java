package ca.uqam.tool.vivoproxy.pattern.command.concrete;

import java.io.IOException;

import ca.uqam.tool.vivoproxy.pattern.command.Command;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.pattern.command.receiver.Receiver;
import ca.uqam.tool.vivoproxy.pattern.command.receiver.VivoReceiver;
import ca.uqam.tool.vivoproxy.swagger.model.PositionOfPerson;

public class CreatePositionForCommand implements Command {

    private String name;
    private PositionOfPerson positionOfPerson;
    private CommandResult result;

    public CreatePositionForCommand(PositionOfPerson body) {
        super();
        setPositionOfPerson(body);
    }

    public CommandResult execute(Receiver vivo) {
        try {
            result = ((VivoReceiver)vivo).setPositionOfPerson(positionOfPerson);
            return  result;
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

    /**
     * @return the positionOfPerson
     */
    public PositionOfPerson getPositionOfPerson() {
        return positionOfPerson;
    }

    /**
     * @param positionOfPerson the positionOfPerson to set
     */
    public void setPositionOfPerson(PositionOfPerson positionOfPerson) {
        this.positionOfPerson = positionOfPerson;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        try {
            return positionOfPerson.toString();
        } catch (Exception e) {
            return null;
        }
    }
}
