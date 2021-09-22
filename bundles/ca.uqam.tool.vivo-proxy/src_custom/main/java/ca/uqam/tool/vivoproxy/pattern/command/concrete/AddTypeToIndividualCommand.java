package ca.uqam.tool.vivoproxy.pattern.command.concrete;

import java.io.IOException;

import ca.uqam.tool.vivoproxy.pattern.command.Command;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.pattern.command.Receiver;
import ca.uqam.tool.vivoproxy.pattern.command.receiver.VivoReceiver;
import ca.uqam.tool.vivoproxy.swagger.model.IndividualType;

/**
 * @author Michel Héon; Université du Québec à Montréal
 * @filename AddTypeToIndividualCommand.java
 * @date 22 sept. 2021
 *
 */
public class AddTypeToIndividualCommand extends Command {

    private IndividualType indvType;

	private String MINE_TYPE;

    /**
     * @param indvType
     */
    public AddTypeToIndividualCommand(IndividualType indvType) {
    	super();
    	this.setIndvType(indvType);
    }

	@Override
    public CommandResult execute(Receiver vivo) {
        CommandResult result = null;
        try {
           result = ((VivoReceiver)vivo).addType(getIndvType());
            setCommandResult(result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

	public IndividualType getIndvType() {
		return indvType;
	}

	public void setIndvType(IndividualType indvType) {
		this.indvType = indvType;
	}

}
