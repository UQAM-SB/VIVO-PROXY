package ca.uqam.tool.vivoproxy.pattern.command.concrete;

import java.io.IOException;
import java.util.UUID;

import ca.uqam.tool.vivoproxy.pattern.command.Command;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.pattern.command.Receiver;
import ca.uqam.tool.vivoproxy.pattern.command.receiver.VivoReceiver;
import ca.uqam.tool.vivoproxy.swagger.model.PersonWithOfficeInfo;

public class AddPersonWithEmailCommand extends Command {

    private PersonWithOfficeInfo person;


    public AddPersonWithEmailCommand(PersonWithOfficeInfo person) {
    	super();
    	this.setPerson(person);
        setName(toString());
    }

	@Override
    public CommandResult execute(Receiver vivo) {
        CommandResult result = null;
        try {
            result = ((VivoReceiver)vivo).addPerson(getPerson());
            setCommandResult(result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

	@Override
	public String toString() {
		return "AddPersonCommand [name=" + name + ", person=" + getPerson() + "]";
	}

	public PersonWithOfficeInfo getPerson() {
		return person;
	}

	public void setPerson(PersonWithOfficeInfo person) {
		this.person = person;
	}

}
