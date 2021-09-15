package ca.uqam.tool.vivoproxy.pattern.command.concrete;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import ca.uqam.tool.vivoproxy.pattern.command.Command;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.pattern.command.Receiver;
import ca.uqam.tool.vivoproxy.pattern.command.receiver.VivoReceiver;
import ca.uqam.tool.vivoproxy.swagger.model.AuthorOfADocument;
import ca.uqam.tool.vivoproxy.swagger.model.Concept;
import ca.uqam.tool.vivoproxy.swagger.model.Document;
import ca.uqam.tool.vivoproxy.swagger.model.Person;

public class AddAuthorOfDocumentCommand extends Command {

    private AuthorOfADocument authorOfADocument;

    public AddAuthorOfDocumentCommand(AuthorOfADocument author) {
    	super();
    	setAuthorOfADocument(author);
        setName(toString());
    }

	@Override
    public CommandResult execute(Receiver vivo) {
        CommandResult result = null;
        try {
           result = ((VivoReceiver)vivo).addDocumentToPerson(authorOfADocument);
            setCommandResult(result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

	public AuthorOfADocument getAuthorOfADocument() {
		return authorOfADocument;
	}

	public void setAuthorOfADocument(AuthorOfADocument authorOfADocument) {
		this.authorOfADocument = authorOfADocument;
	}

	@Override
	public String toString() {
		return "AddAuthorOfDocumentCommand [authorOfADocument=" + authorOfADocument + "]";
	}
}


