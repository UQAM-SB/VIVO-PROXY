package ca.uqam.tool.vivoproxy.pattern.command.concrete;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import ca.uqam.tool.vivoproxy.pattern.command.Command;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.pattern.command.Receiver;
import ca.uqam.tool.vivoproxy.pattern.command.receiver.VivoReceiver;
import ca.uqam.tool.vivoproxy.swagger.model.Concept;
import ca.uqam.tool.vivoproxy.swagger.model.Document;
import ca.uqam.tool.vivoproxy.swagger.model.Person;

public class AddDocumentCommand extends Command {

    private Document document;


	public Document getdocument() {
		return document;
	}

	public void setdocument(Document document) {
		this.document = document;
	}


    public AddDocumentCommand(Document document) {
    	super();
    	setdocument(document);
        setName(toString());
    }

	@Override
    public CommandResult execute(Receiver vivo) {
        CommandResult result = null;
        try {
           result = ((VivoReceiver)vivo).addDocument(document);
            setCommandResult(result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

	@Override
	public String toString() {
		return "AddDocumentCommand [document=" + document + "]";
	}
}


