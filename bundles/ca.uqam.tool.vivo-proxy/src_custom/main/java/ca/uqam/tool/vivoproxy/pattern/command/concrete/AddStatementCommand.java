package ca.uqam.tool.vivoproxy.pattern.command.concrete;

import java.io.IOException;

import ca.uqam.tool.vivoproxy.pattern.command.Command;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.pattern.command.Receiver;
import ca.uqam.tool.vivoproxy.pattern.command.receiver.VivoReceiver;
import ca.uqam.vocab.proxy.model.Statement;

/**
 * @author Michel Héon; Université du Québec à Montréal
 * @filename AddStatementCommand.java
 * @date 23 sept. 2021
 */
public class AddStatementCommand extends Command {
    private Statement statement;
    /**
     * @param indvType
     */
    public AddStatementCommand(Statement statement) {
    	super();
    	this.setStatement(statement);
        setName(toString());
    }

	@Override
    public CommandResult execute(Receiver vivo) {
        CommandResult result = null;
        try {
           result = ((VivoReceiver)vivo).addaStatement(statement);
            setCommandResult(result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

	public Statement getStatement() {
		return statement;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
	}

	@Override
	public String toString() {
		return "AddStatementCommand [statement=" + statement + "]";
	}
}
