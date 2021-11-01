package ca.uqam.tool.vivoproxy.pattern.command.concrete;

import java.io.IOException;

import ca.uqam.tool.vivoproxy.pattern.command.Command;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.pattern.command.Receiver;
import ca.uqam.tool.vivoproxy.pattern.command.receiver.VivoReceiver;
import ca.uqam.tool.vivoproxy.swagger.model.PositionOfPerson;

public class CreatePositionForCommand extends Command {

    private String name;
    private PositionOfPerson positionOfPerson;
	private CommandResult result;

    public CreatePositionForCommand(PositionOfPerson body) {
        super();
        setPositionOfPerson(body);
        setName(toString());
    }

    public CommandResult execute(Receiver vivo) {
        try {
            result = ((VivoReceiver)vivo).addPositionOfPerson(positionOfPerson);
            this.setCommandResult(result);
            return  result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
