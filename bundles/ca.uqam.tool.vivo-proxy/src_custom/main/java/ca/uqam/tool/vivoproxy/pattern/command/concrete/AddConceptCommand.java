package ca.uqam.tool.vivoproxy.pattern.command.concrete;

import java.io.IOException;

import ca.uqam.tool.vivoproxy.pattern.command.Command;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.pattern.command.Receiver;
import ca.uqam.tool.vivoproxy.pattern.command.receiver.VivoReceiver;
import ca.uqam.vocab.proxy.model.Concept;

public class AddConceptCommand extends Command {

    private Concept anObject;

    public AddConceptCommand(Concept concept) {
    	super();
    	setAnObject(concept);
        setName(toString());
    }

	@Override
    public CommandResult execute(Receiver vivo) {
        CommandResult result = null;
        try {
           result = ((VivoReceiver)vivo).addConcept(getAnObject());
            setCommandResult(result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

	public Concept getAnObject() {
		return anObject;
	}

	public void setAnObject(Concept anObject) {
		this.anObject = anObject;
	}

	@Override
	public String toString() {
		return "AddConceptCommand [anObject=" + anObject + "]";
	}
}